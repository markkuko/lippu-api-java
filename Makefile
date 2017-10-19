# Generate necessary server and client keys.

keys/operator.pem:
	@mkdir -p keys
	openssl genrsa -out keys/operator.pem 2048

keys/operator.key: keys/operator.pem
	openssl pkcs8 -topk8 -inform PEM -outform DER -in operator.pem  -nocrypt > keys/operator.key

keys/operator.pub: keys/operator.pem
	openssl rsa -in keys/operator.pem -pubout > keys/operator.pub

keys/client/client1.pem:
	mkdir -p keys/client
	openssl genrsa -out keys/client/client1.pem 2048

keys/client/client1.pub: keys/client/client1.pem
	openssl rsa -in keys/client/client1.pem -pubout > keys/client/client1.pub

keys: keys/client/client1.pub keys/operator.pub keys/operator.key

# Creates uberjar from the sources. Build process uses docker image
# to have reproducible builds.
artifacts/lippu-service.jar:
	@mkdir -p artifacts
	sudo docker run --rm -i -v ${PWD}/artifacts:/opt/artifacts:Z  openjdk:8-jdk-alpine sh < build.sh
	sudo chown -R $(id -u):$(id -u) artifacts/

# Builds docker iamge.
build_image: artifacts/lippu-service.jar
	sudo docker build -f Dockerfile --build-arg jar=lippu-service.jar -t lippu/lippu .

# Starts container from the lippu-docker image on port 8080.
run: build_image keys
	sudo docker run --rm -d -v ${PWD}/keys:/opt/lippu/keys:Z -e JAR_NAME=lippu-service.jar --name lippu-service -p 8080:8080 lippu/lippu

# Stops the lippu-docker container.
stop:
	sudo docker stop lippu-service

# Starts lippu service using spring boot. For development
# use.
dev:
	./gradlew clean bootRun

run_local: build
	sh run.sh

# Creates python3 based virtualenv environment
# for running integration tests.
test_setup:
	(\
		source `which virtualenvwrapper.sh`; \
		mkvirtualenv --python=`which python3` lippu-test; \
		workon lippu-test; \
		pip install -r test-requirements.txt; \
	)

# Runs integration test using virtualenv. See @test_setup
# for setting up the virtualenv environment.
test:
	(\
		source `which virtualenvwrapper.sh`; \
		workon lippu-test; \
		python3 runtests.py --log=$$LOG; \
	)
