package loadtest

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

/**
 * This is example exuction is originally simply recorded agains the web app.
 * After that there are some refactoring done to improve the readability a bit.
 **/
class BasicVaadinHelloSimulation extends Simulation {

	val httpProtocol = http
		.baseURL("http://localhost:8080")
		.inferHtmlResources(BlackList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.(t|o)tf""", """.*\.png"""), WhiteList())
		.acceptHeader("""*/*""")
		.acceptEncodingHeader("""gzip,deflate,sdch""")
		.acceptLanguageHeader("""en-US,en;q=0.8,fi;q=0.6""")
		.contentTypeHeader("""application/json; charset=UTF-8""")
		.userAgentHeader("""Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.143 Safari/537.36""")

	val headers_for_hostpage = Map(
		"""Accept""" -> """text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8""",
		"""Cache-Control""" -> """max-age=0""")

	val headers_initial_state_request = Map(
		"""Cache-Control""" -> """max-age=0""",
		"""Content-type""" -> """application/x-www-form-urlencoded""",
		"""Origin""" -> """http://localhost:8080""")

	val headers_3 = Map("""Origin""" -> """http://localhost:8080""")

    val uri1 = """http://localhost:8080"""

        // static resoures not fetched, can/should be moved to CDN
	val scn = scenario("BasicVaadinHelloSimulation4")
		.group("Fetch host page") {
		exec(http("request_0")
			.get("""/""")
			.headers(headers_for_hostpage))
		.pause(1 seconds)
                // ajax communication request must be in subsequent exec,
                // otherwise (if used e.g. resource request like in recorded case)
                // session is not yet correctly set up
		.exec(http("request_2")
			.post(uri1 + """/?v-1408798653302""")
			.headers(headers_initial_state_request)
			.body(RawFileBody("BasicVaadinHelloSimulation4_request_2.txt")))
		.pause(2 seconds)
		.exec(http("request_3")
			.post(uri1 + """/UIDL/?v-wsver=7.3.2&v-uiId=0""")
			.headers(headers_3)
			.body(RawFileBody("BasicVaadinHelloSimulation4_request_3.txt"))
                        // Make a small check that the scenario was successful
			.check(regex("Thank you for clicking").find(0).exists))
		};

        // Next line will execute the test just once
	//setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)

        // This uses more load, simulates 100 users who arrive with-in 10 seconds
	setUp(scn.inject(rampUsers(100) over (10 seconds))).protocols(httpProtocol)
	
}