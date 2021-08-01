# Manage Reports

Generate a report using Spring Boot, Thymleaf, bootstrap and css

# Getting Started

This project is a web application based on [Spring] boot(https://spring.io/) and Thymeleaf (https://www.thymeleaf.org/).

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.3/maven-plugin/reference/html/#build-image)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-spring-mvc-template-engines)
* [Spring Web](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#boot-features-developing-web-applications)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.5.3/reference/htmlsingle/#using-boot-devtools)

### Guides
The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)
* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/bookmarks/)


# Used technologies
    Spring Boot 2.5.3
    Spring 5.3.9
    Thymeleaf 3.0.12.RELEASE
    bootstrap 4.2.1
    Tomcat embed 9.0.14
    JUnit 4.12
    Maven 3
    Java 11

# Run the application

To run the Application, you need to have [JDK](http://www.oracle.com/technetwork/java/javase/downloads/index.html), [Maven](https://maven.apache.org/) 

1- Using Command line

```
Clone the project or download and unzip it, go to managereports diractory and run the app

cd springboot-thymleaf-junit-manage-reports
mvn clean install
mvn spring-boot:run
```
Browse to the app at (http://localhost:8088)
The port can be changed in application.properties

2- Using Docker

```
Clone the project or download and unzip it, go to managereports diractory and run the app

cd springboot-thymleaf-junit-manage-reports
mvn clean install

docker build -f Dockerfile -t manage-reports .
docker run --rm --name manage-reports-api -p 8088:8088 manage-reports
```
Browse to the app at http://localhost:8088



# Test the application

If You want to test the application with an other paragraph, you just need to put the paragraph you want in the file "src/main/resources/paragraph.txt" and restart the application.
