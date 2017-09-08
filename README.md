# Java implementation for Lippu-project API


## Howto run
Project uses gradle-build tool to build software
artifacts. It can be used to build standalone
runnable jar-file. You also need Java 8 SDK to
build and run the 

Run command:

```
gradlew clean build
```
which creates lippu-api-service-0.1.0-SNAPSHOT.jar file
to the build/libs/ directory.

You can start the service by running:

```
java -jar build/libs/lippu-api-service-0.1.0-SNAPSHOT.jar
```

Standalone service is listening on port 8080, currently
only placehoder implementation is available:

* http://localhost:8080/products