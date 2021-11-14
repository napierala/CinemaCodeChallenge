# CinemaCodeChallenge

## Decisions:
- DataStore: A SQL relational DB for the sake of this challenge an H2 in memory but in a "real" production app a PostgresSQL DB would have been chosen. Why SQL instead of NoSQL like MongoDB etc.? The answer is simple the requirements of this app are quite simple and the query times are not that crusial, and in the case of horizontal scaling one could just partition the store based on groups of cinemas. An additional advantage is that if later on the client wants to add ticket selling/reservation of seats a SQL data store is more reliable and truly transactional.
- DataStore init: Flyway was chosen, sometimes in a production scenario flyway is turned off and a devops does the work, but compared to a simple hibernate.ddl update it is much more stable.
- GitFlow: In a many developer scenario and if the work extends to a longer period of time a real git flow would have been employed - develop, master, feature and hot-fixes if the app is deployed.
- Modules: Usually projects are split up into modules, each with a specific purpose like: rest external models, database dao, entities for the sake of this project only one SpringBoot module is used.
- Dates: Normally zoned dates are used, as the server could be in one country and the application logic in a different timezone, but for the sake of this challenge LocalDateTime were used.
- Logs: Normally a log system like graylog is used but for the sake of this challenge a default console log from spring was used.
- Security: Usually oauth tokens with https are used as authentication but for the sake of this challenge a simple basic authentication was used which of course needs https. Many times https is done via other mechanism like haproxy to keep things simple and avoid the self-signed certificate problems https was omitted.
- Currency: Only one currency is assumed in this app.
- Cinema: Even though the requirements only state a single cinema, supporting from the beginning many cinemas will mean a lot less changes when the client opens more cinemas.
- Tests: unit tests were made only for the truly non-spring code - builders, to test the full functionalities' integration tests that start the app were made. Tests with mocks(@WebMvcTest, @DataJpaTest, etc.) should also be made but in this challenge they were omitted.

## Prerequisites:
- maven installed.
- Java8 jdk installed.
- 8088 port not used.

## Run integration tests:

mvn clean test -P IT -DimdbKey=OMDb_KEY

## Build:

mvn clean package

## Launch:

java -jar target/cinemacodechallenge.jar -DimdbKey=OMDb_KEY

## REST documentation:

http://localhost:8088/swagger-ui/index.html?configUrl=/v3/api-docs/swagger-config

## PreLoaded data:

- Admin: user: admin pass: admin_password
- Regular: user: user pass: user_password
- Cinema: code: FIRST
- Showings: 2021-12-01 and 2021-12-02 of The Fast and the Furious