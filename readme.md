# Backend Spring Boot Rest Api
A simple Spring Boot application with REST API service provider.

# Technology
Following libraries were used during the development of this:
- Spring Boot and Spring Security - Server side framework
- JWT - Authentication mechanism for REST APIs
- Docker - Containerizing framework
- H2 - in memory database
- Swagger - API documentation
- MapStruct, Lombok, etc - code generator.

# API
- API for authentication, check User existing and return generated token
- API for retrieving all employee.
- API for retrieving one employee by ID.
- API for saving one employee.
- API for modifying one employee.
- API for deleting one employee by ID.

### Running the server locally

Requires Maven and Java8+ to run.

Install the dependencies and devDependencies and start the server.

```sh
$ cd backend
$ mvn clean install
$ maven package
$ java -jar target/backend-0.0.1-SNAPSHOT.jar
```

You can also run Spring Boot app with Maven plugin :

```sh
$ mvn spring-boot:run
```
Once the server is setup you should be able to access following URL :
- http://localhost:8080

And the REST APIs can be accessed over the following base-path :
http://localhost:8080/api/

Some of the important api endpoints are as follows :
Get Token (***Token will return inside HTTP Header response Field:"Authorization")
- http://localhost:8080/api/auth (HTTP:POST)

Access to resource endpoints
- http://localhost:8080/api/v1/employees (HTTP:GET)
- http://localhost:8080/api/v1/employees (HTTP:POST)
- http://localhost:8080/api/v1/employees/{id} (HTTP:GET)
- http://localhost:8080/api/v1/employees/{id} (HTTP:PUT)
- http://localhost:8080/api/v1/employees/{id} (HTTP:DELETE)

### Testing
```sh
$ mvn test
```

### Running in Docker Container
```sh
$ cd backend
$ docker build -t "your tag name"
$ docker run -p 8080:8080 "your tag name"
```

### API Documentation
Swagger2
http://localhost:8080/swagger-ui.html

Contributors
Yongyuth Jantrachat
