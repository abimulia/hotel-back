# Hotel Back
[![Spring Boot v3.3.5](https://img.shields.io/badge/Java-SpringBoot-green)](https://spring.io/)
[![License](http://img.shields.io/:license-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0.html)

This project created by `start.spring.io` contain [Spring Boot](https://spring.io/) version 3.3.5.


Code in this project based on the guide to build Airbnb clone on https://www.youtube.com/watch?v=XriUV06Hkow 

The front end application using Angular, and connect to the back end application created with Spring Boot 

[![FrontEnd](http://img.shields.io/:FrontEnd-Angular-red.svg)](https://github.com/abimulia/hotel-front)



## Development server

Run `mvn spring-boot:run` for a dev server. Spring Boot’s embedded Apache Tomcat server is acting as a webserver and is listening for requests on `localhost` port `8080`. Open your browser and in the address bar at the top enter http://localhost:8080

## Help

[![Static Badge](https://img.shields.io/badge/Help-green)](https://github.com/abimulia/hotel-back/HELP.md)

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.5/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.5/maven-plugin/build-image.html)
* [Spring Data JPA](https://docs.spring.io/spring-boot/3.3.5/reference/data/sql.html#data.sql.jpa-and-spring-data)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/3.3.5/reference/using/devtools.html)
* [Docker Compose Support](https://docs.spring.io/spring-boot/3.3.5/reference/features/dev-services.html#features.dev-services.docker-compose)
* [Liquibase Migration](https://docs.spring.io/spring-boot/3.3.5/how-to/data-initialization.html#howto.data-initialization.migration-tool.liquibase)
* [Spring Modulith](https://docs.spring.io/spring-modulith/reference/)
* [OAuth2 Client](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html#web.security.oauth2.client)
* [Okta Spring Boot documentation](https://github.com/okta/okta-spring-boot#readme)
* [Spring Security](https://docs.spring.io/spring-boot/3.3.5/reference/web/spring-security.html)
* [Validation](https://docs.spring.io/spring-boot/3.3.5/reference/io/validation.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.3.5/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Accessing Data with JPA](https://spring.io/guides/gs/accessing-data-jpa/)
* [Okta-Hosted Login Page Example](https://github.com/okta/samples-java-spring/tree/master/okta-hosted-login)
* [Custom Login Page Example](https://github.com/okta/samples-java-spring/tree/master/custom-login)
* [Okta Spring Security Resource Server Example](https://github.com/okta/samples-java-spring/tree/master/resource-server)
* [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
* [Spring Boot and OAuth2](https://spring.io/guides/tutorials/spring-boot-oauth2/)
* [Authenticating a User with LDAP](https://spring.io/guides/gs/authenticating-ldap/)
* [Validation](https://spring.io/guides/gs/validating-form-input/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

## OAuth 2.0 and OIDC with Okta

If you don't have a free Okta developer account, you can create one with [the Okta CLI](https://cli.okta.com):

```bash
$ okta register
```

Then, register your Spring Boot app on Okta using:

```bash
$ okta apps create
```

Select **Web** > **Okta Spring Boot Starter** and accept the default redirect URIs.

### Docker Compose support
This project contains a Docker Compose file named `compose.yaml`.
In this file, the following services have been defined:

* postgres: [`postgres:latest`](https://hub.docker.com/_/postgres)

Please review the tags of the used images and set them to the same as you're running in production.


