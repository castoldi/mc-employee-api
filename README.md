# mc-employee-api

## Overview
The API was built using Spring Boot 3.1.0 with Java 17.

Users of this application should be able to:

- Add a new employee
- Update an employee’s detail.
- Remove an employee
- List Department structure. (ie. All employees in a hierarchy)
- Calculate the cost allocation of a Department.
- Calculate the cost allocation of a Manager. 

## Pre-requisites

- Java 17
- [Optional] Zipkin server

## Open API and Swagger

Once application is started, use below links to learn about the API.

- http://localhost:8080/api-docs
- http://localhost:8080/swagger-ui/index.html


## Start with SSL and DEV profile

Default profile works fine in local environment.

However, dev and ssl profiles are available. 

Use ssl profile to start the application with https and self-signed certificate.

Add below VM arguments if needed.

-Dspring.profiles.active=dev,ssl

HTTPS URL example: https://localhost:8443/api/v1/employee

## Postman

Postman project is attached to the source code.

Check file [Employee.postman_collection.json] in the project docs folder in GitHub.

## Database

This application uses a H2 in-memory database.

Use below URL and login information to access the console.

> http://localhost:8080/h2-console

- JDBC URL: jdbc:h2:mem:employeedb
- Username: sa
- Password: password

## Zipkin

Use Zipkin to monitor application performance. This step is optional if want to enable observability.

### Download

https://repo1.maven.org/maven2/io/zipkin/zipkin-server/2.24.0/zipkin-server-2.24.0-slim.jar

### Running via Java

```shell
java -jar zipkin-server-2.24.0-slim.jar
```

### Web Access

Use below URL to access Zipkin traces.

> http://localhost:9411/zipkin/

## Generate HTTPS Certificates
Below commands were used to generate non-production self-signed certificates.

```shell
keytool -genkeypair -alias mc-employee-api -keyalg RSA -keysize 4096 -storetype JKS -keystore mc-employee-api.jks -validity 3650 -storepass changeit
keytool -genkeypair -alias mc-employee-api -keyalg RSA -keysize 4096 -storetype PKCS12 -keystore mc-employee-api.p12 -validity 3650 -storepass changeit
```

Should you have a signed JKS, use this command to convert to PKCS12:

```shell
keytool -import -alias mc-employee-api -file myCertificate.crt -keystore mc-employee-api.p12 -storepass changeit
```