###### In this example I have used H2 as in memory database and rest web services are In spring Boot. Project can be executed as follows:

`mvn clean`

`mvn install`

`mvn spring-boot:run`

**After the project is up and running DB, Services and Swagger-UI can be accessed as follows:**

DB

Access localhost:8080/h2-console with schema jdbc:h2:mem:testdb connect and see tables data.

Services

_`NOTE:__` Players  and Team should be populated first to carry out Contract fee calculations.

###### Player
~~~~

POST localhost:8080/player/register

GET localhost:8080/player/name/{name}

GET localhost:8080/player/

GET localhost:8080/player/{id}

DELETE localhost:8080/player/{name}

GET localhost:8080/player/contract/{name}
~~~~
###### Team
~~~~
POST localhost:8080/team/register

GET localhost:8080/team/name/{name}

GET localhost:8080/team/{id}

DELETE localhost:8080/team/{name}

POST localhost:8080/team/build
~~~~
Postman Collections is part of project folder. You can find it by the name of PlayerMarket.postman_collection.json

Swagger-UI

localhost:8080/swagger-ui.html