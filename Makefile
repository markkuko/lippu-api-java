# Generate necessary server and client keys.

ENVIRONMENT?=TEST
LOGLEVEL?=WARNING
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

stamps/docker-dependencies:
	@mkdir -p stamps
	sudo docker build --rm -f Dockerfile-builddeps -t lippu/lippu-builddeps .
	touch $@
# Creates uberjar from the sources. Build process uses docker image
# to have reproducible builds.
artifacts/lippu-service.jar: stamps/docker-dependencies
	@mkdir -p artifacts
	sudo docker build --rm -f Dockerfile-build -t lippu/lippu-build .
	sudo docker create --name lippu-build lippu/lippu-build
	sudo docker cp lippu-build:/home/lippu/build/libs/lippu-service.jar artifacts/
	sudo chown -R $(id -u):$(id -u) artifacts/
	sudo docker rm lippu-build
	sudo docker rmi -f  lippu/lippu-build

# Starts container from the lippu-docker image on port 8080.
run: artifacts/lippu-service.jar keys
	sudo docker-compose up --build

# Starts container from the lippu-docker image on port 8080.
test: artifacts/lippu-service.jar keys
	sudo docker-compose up -d --build
	sleep 45
	(\
		source `which virtualenvwrapper.sh`; \
		workon lippu-test; \
		python3 runtests.py; \
	)
	sudo docker-compose down

# Stops the lippu-docker container.
stop:
	sudo docker stop lippu-service

# Starts lippu service using spring boot. For development
# use.
dev:
	./gradlew clean bootRun

run_local: build
	sh run.sh

# Cleans previously build jars
clean:
	rm -f artifacts/*jar
	rm stamps/*

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
runtests:
	(\
		source `which virtualenvwrapper.sh`; \
		workon lippu-test; \
		python3 runtests.py --log=$(LOGLEVEL) --environment=$(ENVIRONMENT); \
	)
