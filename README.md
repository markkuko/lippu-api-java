# Java implementation for Lippu-project API


## Howto run
Project uses gradle-build tool to build software artifacts. It can also be
used to build standalone runnable jar-file. You also need Java 8 SDK
and openssl to create encpyption keys.

Start the service by running, which will create server and client keys
to the directory `keys`.
```
sh run.sh
```

To run the server without generating keys, use command:
```
./gradlew clean bootRun
```
Standalone service is listening on port 8080:
* http://localhost:8080/stubs/
* The swagger UI at http://localhost:8080/stubs/swagger-ui.html 


## Integration tests
Install requirements for the python3, use new
[virtualenv](http://www.pythonforbeginners.com/basics/how-to-use-python-virtualenv/)
if you can:
```
pip install -r test-requirements.txt
```
*PyCrypto* might need some 
Start the server and run the integration tests:
```
python3 runtests.py
```
To get more logging you can the tests with:
```
python3 runtests.py --log=INFO
```
or
```
python3 runtests.py --log=DEBUG
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
This work is licended under EUPL (European Union Public Licence) version 1.2. See [LICENSE.txt](LICENSE.txt)
for more information.