# Lippu-projekti API:n Java-implementaatio


## Miten käynnistyy?

Projekti käyttää gradle build-työkalua, jolla voi luoda
ajettavan jar-tiedoston. Tämän lisäksi tarvitaan Java 8 SDK.

Aja komento:

```
gradlew clean build
```
joka luo lippu-api-service-0.1.0-SNAPSHOT.jar tiedoston build/libs/
hakemistoon.

Tämän jälkeen rajapinnan voi käynnistää komennolla:

```
java -jar build/libs/lippu-api-service-0.1.0-SNAPSHOT.jar
```

Rajapinta löytyy osoitteesta http://localhost:8080/ , tällä
hetkellä löytyy vain placeholder osoitteesta:

* http://localhost:8080/products