# QR-code Generator and Reader

###### Spring boot application exposing REST API endpoint to genrate QR-code representing custom message and another endpoint to read the decoded message, built using Java Spring boot and google's zxing library.

<center>
	<a target='_blank' href='https://iris-qr-code-api-spring-boot.herokuapp.com/swagger-ui.html'>Running Application</a>
</center>

## Local Setup

* Install Java 15
* Install Maven

Recommended way is to use [sdkman](https://sdkman.io/) for installing both maven and java

Run the below commands in the core

```
mvn clean install
```

```
mvn spring-boot:run

```

server port is configured to 9090 which can be changed in application.properties file

Go to the below url to view swagger-ui (API docs)

```
http://localhost:9090/swagger-ui.html
```