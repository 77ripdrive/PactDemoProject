# Consumer-Driven-Contract Test 

## Consumer and Provider Java modules

All Java modules require **Java 14** to compile and run.

### Building with Gradle 6.4.1

Each module should be an independent build and can be built by calling `./gradlew clean build` in the module directory. 

All modules are listed in [build-all.sh](build-all.sh) to run in the CI pipeline.

#### Run 'docker-compose up' before 'publish' task if you'll use PactBroker with local Docker and you'll have a Broker running at http://localhost:8181/
