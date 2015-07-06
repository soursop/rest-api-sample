# REST-API-EXAMPLE

## USE LIBRARY

- Spring Data JPA
- Spring Rest
- Spring Boot
- Swagger (API Document library)

## RECOMMANDED ENVIRONMENT

Java 7, Java 8

Maven 3.1.0

## BEFORE STEP 

please update maven dependency

    cd {projectRoot}
    mvn package

## EXEC STEP 

    java -jar {projectRoot}/target/rest-api-sample-0.0.1-SNAPSHOT.war

or

    java -jar {projectRoot}/target/rest-api-sample-0.0.1-SNAPSHOT.war -Dspring.profiles.active=live

You would be able to see different BindingExceptionMessage depending on profile option (`live` or NONE)

## AFTER EXECUTE, PLEASE CHECK DOCUMENT

move to http://localhost:8080/docs/index.html
    
## MAIN CLASS

    RestApiSampleApplication.java
