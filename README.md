# Example to use Gatling with Vaadin - Work in progress

This is simple example project to help to get started with load testing Vaadin applications using Gatling.

The the project is configured to run single scenario during integration test phase agains jetty server.

If you check out and execute "mvn install", either with command line or with ide
 
 1. The project is compiled, war file created
 2. in pre-integration-test phase it is started into jetty server
 3. Gatling acutomatically bombards the test, test reports are collected to target/gatling
 4. jetty is stopped

If you play around with the project in IDE, you can also start the web app in any way you want and then just execute the test scenario. Easies way to start gatling script is probably executing "mvn gatling:execute".

For an example that uses WebSocket communication (aka modern server push) check out [this project](https://github.com/mstahv/v-quiz/). TODO Make a very simple version of websocket testing into this example project as well. Contributions more than welcome.


