# ServerProg-EmergencyRoom

Final project for my Server Programming class based on a system for patient administration in an emergency room.

## Basic functionality

The app implements some basic functionality which we have gone through during the course.

### RESTful service

The app has a working RESTful service, serving both the Spring defaults and my own implementation. These have been secured, as well.

### External DB

The project stores data in a database managed by PostgreSQL. This has been tested both locally and on the remote server.

### Security features

These include authentication, encryption of stored credentials and restricting user access and interaction with a role-based approach. 

### Testing

Tests have been written taking into consideration different aspects of the functionality, including the controllers, the REST services and the web layer.

### Deployment

The project is, as of the time this documentation is written, running on a remote server, on the Heroku platform.

## Extras

### REST access limiter

Implemented the Spring Boot Starter at [source](https://github.com/MarcGiffing/bucket4j-spring-boot-starter) to prevent brute force login attempts and attacks against the endpoints. It is based on [bucket4j](https://github.com/vladimir-bukhtoyarov/bucket4j) which is a rate-limiting library for Java.

### Extra security features

These were based on the Developer documentation from Heroku about preparing a Spring Boot app for deployment, which can be found [here](https://devcenter.heroku.com/articles/preparing-a-spring-boot-app-for-production-on-heroku).
The use of HTTPS is forced, the API calls are limited (as described above) and primary keys have been set as UUID instead of integers.
