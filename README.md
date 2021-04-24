# Factory pattern with spring boot

This repository contains a simple example of factory design pattern with spring boot

### Table of Contents  
[Technologies](#technologies)<br>
[Dependencies](#dependencies)<br>
[Pre requirements](#requirements)<br>
[Download](#download)<br>
[Run Tests](#runtests)<br>
[Installing](#installing)<br>
[Run Project](#run)<br>
[Using](#using)

<a name="technologies"/></a>
## Technologies
  * Java 11
  * Maven
  * Spring Boot
  * Lombook
  
<a name="dependencies"/></a>
## Dependencies
  * Lombok

<a name="requirements"/></a>
## Pre requirements
  * JDK 11. version
  * Maven 4.0.0 version
  * git (optional)

<a name="download"/></a>
## Download
  * First of all you need to clone this project. For this, if you have installed git then you can run above code snippet in bash, powershall or terminal.<br>
      `git clone https://github.com/ilyasziyaoglu/Car-Factory.git`<br>
    or you can download from github.com project page as a `.zip` file directly and extract.

<a name="runtests"/></a>
## Run Tests
  * To run tests run above code snippet.<br>
    `mvn clean compile test`<br>

<a name="installing"/></a>
## Installing
  * To build project, before go into project folder bash, powershall or terminal and run above code snippet.<br>
    `mvn clean install`<br>
    If everything goes well you will see the last `BUILD SUCCESS` post.

<a name="run"/></a>
## Run Project
  * To run project run above code snippet. After this process, it will start to accept requests over port 8080 with the embedded tomcat server.<br>
    `java -jar ./target/carfactory-0.0.1-SNAPSHOT.jar`<br>

<a name="using"/></a>
## Using
  * To make a request for this project, you can use the Postman collection in the project or run the following `curl` command from the terminal.<br>
    `curl --location --request POST 'http://localhost:8080/' --header 'Content-Type: text/plain' --data-raw 'cabrio'`
