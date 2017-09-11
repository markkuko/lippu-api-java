# Java implementation for Lippu-project API


## Howto run
Project uses gradle-build tool to build software artifacts. It can also be
used to build standalone runnable jar-file. You also need Java 8 SDK
and openssl to create encpyption keys.

Start the service by running, which will create server keys
to the directory `keys`. Edit the `src/main/resources/test-data.json`
to populate the database with your own test data.
```
sh run.sh
```

To run the server without generating keys, use command:
```
./gradlew clean bootRun
```

Standalone service is listening on port 8080:

* http://localhost:8080/stubs/

## License
This work is licended under EUPL (European Union Public Licence) version 1.2. See [LICENSE.txt](LICENSE.txt)
for more information.