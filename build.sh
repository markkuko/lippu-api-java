# Shell commands to build jar in alpine JDK8 docker container
apk update
apk add git
git clone https://github.com/markkuko/lippu-api-java
cd lippu-api-java
git checkout master
./gradlew clean findbugsMain build
cp build/libs/lippu-service.jar /opt/artifacts/lippu-service.jar
