# minesweeper-api

#####The list of tasks done (prioritized from most important to least important):

- [x]  Design and implement  a documented RESTful API for the game (think of a mobile app for your API)
- [ ]  Implement an API client library for the API designed above. Ideally, in a different language, of your preference, to the one used for the API
- [x]  When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
- [x]  Ability to 'flag' a cell with a question mark or red flag
- [x]  Detect when game is over
- [x]  Persistence
- [ ]  Time tracking
- [x]  Ability to start a new game and preserve/resume the old ones
- [x]  Ability to select the game parameters: number of rows, columns, and mines
- [x]  Ability to support multiple users/accounts

## Getting Started

Spring Boot Application using Java 11 with Redis

###API Documentation
Swagger API documentation for the game

* [Local Link](http://localhost:8080/swagger-ui.html#/)
* [Prod Link ](https://*****/swagger-ui.html#/)

###Decisions taken and Important notes
* Users/accounts was implemented without a password or secure session owing to the given time. The implementation of this would be creating jwt tokens when the user logged in. So the user should send the JWT in each request and the backend should check if the token is valid and not expired


#### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/maven-plugin/reference/html/#build-image)
* [Spring Configuration Processor](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#configuration-metadata-annotation-processor)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#using-boot-devtools)
* [Spring Data Redis (Access+Driver)](https://docs.spring.io/spring-boot/docs/2.3.4.RELEASE/reference/htmlsingle/#boot-features-redis)

#### Guides
The following guides illustrate how to use some features concretely:

* [Messaging with Redis](https://spring.io/guides/gs/messaging-redis/)

