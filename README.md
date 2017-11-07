# Java implementation for Lippu-project API

This is a simple work-in-progress implementation
of the [Lippu-API](https://github.com/finnishtransportagency/lippu-api).
This is meant as an example and should not be used in production.
It  for example lacks route planning and services with multiple
stops. Test data currently have two routes with departure
and arrival stop.

The data storage is implemented with 
[Spring Data Key-Value](https://docs.spring.io/spring-data/keyvalue/docs/current/reference/html/)
repositories and such lacks many features of
the SQL Databases. This shifts some of the query logic
to business logic side instead of a database query.
 
## How to run
Project uses gradle-build tool to build software artifacts. It can also be
used to build standalone runnable jar-file. You also need Java 8 SDK
to build and run the service and openssl to create encryption keys.  

### As a docker container
This requires docker and docker-compose to be found in the
$PATH. Start the service
by running the following command:
```
make run
```
This will generate server and client keys to the directory `keys`,
build a uberjar of the lippu-service using docker container, start
the lippu-service and nginx-service using *docker-compose*.

Nginx-proxy listening on port 80 and will proxy traffic to the
lippu-service:
* Lippu-service http://localhost:80/stubs/
* The swagger UI at http://localhost:80/stubs/swagger-ui.html 

### As a spring boot service
To run the server as a spring boot service without generating keys,
use command:
```
./gradlew clean bootRun
```
Standalone service is listening on port 8080:
* http://localhost:8080/stubs/
* The swagger UI at http://localhost:8080/stubs/swagger-ui.html 


## Integration tests
Integration tests expects [virtualenvwrapper](https://pypi.python.org/pypi/virtualenvwrapper)
to be found in path and uses lippu-test virtualenv. Tests
require python3. You can setup the virtualenv with the command:   
```
make test_setup
```
It will generate the lippu-test virtualenv and installs
required python packages for it. Then you can run
the tests (start the lippu service before running tests
and make user the *tests/env.json* has correct address
in the *base_url*):
```
make test
```

To get more logging you can run the tests with LOG-parameter:
```
make test LOG=INFO
make test LOG=DEBUG

```

## Own test data
Edit the `src/main/resources/test-data.json` if you want to
populate the database with your own test data.

On the client side, you can generate client side keys:
```
openssl genrsa -out client.pem 2048
openssl rsa -in client.pem -pubout > client.pub
```
Then configure the client.pub key to a client in the server
test data, update the relevant test-data in the
file *tests/testdata/testdata.json* and in the environment
definitions in the *tests/env.json*.

## License
This work is licenced under EUPL (European Union Public Licence) version 1.2. See [LICENSE.txt](LICENSE.txt)
for more information.