# Building

Project is setup using maven therefore you can use standard build commands to create a executable jar

`mvn package`

# Test

As the build system is maven you can executing testing using:

`mvn test`

# Running

The application is utilizing Spring Boot as a wrapper there for you can stand a service up using:

`mvn spring-boot:run`

After running the service you can access swagger-ui via:

http://localhost:8080/swagger-ui.html

# Data

By default the application is loading the data.sql file to an H2 database.  This would need to be extended to support additional mechanisms for load.
