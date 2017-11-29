ENVIRONMENT?=TEST
LOGLEVEL?=WARNING
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

dirs:
	@mkdir -p stamps
	@mkdir -p artifacts
stamps/docker-dependencies: dirs
	sudo docker build --rm -f Dockerfile-builddeps -t lippu/lippu-builddeps .
	touch $@
# Creates uberjar from the sources. Build process uses docker image
# to have reproducible builds.
artifacts/lippu-service.jar: dirs stamps/docker-dependencies
	sudo docker build --rm -f Dockerfile-build -t lippu/lippu-build .
	sudo docker create --name lippu-build lippu/lippu-build
	sudo docker cp lippu-build:/home/lippu/build/libs/lippu-service.jar artifacts/
	sudo chown -R $(id -u):$(id -u) artifacts/
	sudo docker rm lippu-build
	sudo docker rmi -f  lippu/lippu-build

# Starts container from the lippu-docker image on port 8080.
run: artifacts/lippu-service.jar keys
	sudo docker-compose up --build

# Starts lippu-service docker containers, runs tests
# against the containers and then brings the containers down.
test: artifacts/lippu-service.jar keys stamps/test_setup
	sudo docker-compose up -d --build
	sleep 45
	(\
		source `which virtualenvwrapper.sh`; \
		workon lippu-test; \
		python3 runtests.py --log=WARNING --environment=TEST \
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
stamps/test_setup:
	(\
		source `which virtualenvwrapper.sh`; \
		mkvirtualenv --python=`which python3` lippu-test; \
		workon lippu-test; \
		pip install -r test-requirements.txt; \
	)
	touch $@

# Runs integration test using virtualenv. See @stamps/test_setup
# for setting up the virtualenv environment.
runtests:
	(\
		source `which virtualenvwrapper.sh`; \
		workon lippu-test; \
		python3 runtests.py --log=$(LOGLEVEL) --environment=$(ENVIRONMENT); \
	)
