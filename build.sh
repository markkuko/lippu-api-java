# Shell commands to build the lippu-service.jar in alpine JDK8 docker container
# After build is done, copy the jar to artifacts directory to make it available
# outside the container.
apk update
apk add git
git clone https://github.com/markkuko/lippu-api-java
cd lippu-api-java
git checkout master
./gradlew clean findbugsMain build
cp build/libs/lippu-service.jar /opt/artifacts/lippu-service.jar
