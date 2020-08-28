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

Example
```
curl -v --location --request POST 'http://localhost:8080/api/auth?username=user&password=password'
*   Trying ::1...
* TCP_NODELAY set
* Connected to localhost (::1) port 8080 (#0)
> POST /api/auth?username=user&password=password HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.64.1
> Accept: */*
> 
< HTTP/1.1 200 
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< Authorization: Bearer eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFM1MTIifQ.eyJzdWIiOiJ1c2VyIiwiZXhwIjoxNTk5NDY3NDIxLCJyb2xlcyI6WyJST0xFX1VTRVIiXX0.z3x-6PzwUvkXuibY16k7iwAmgqReFjF2WnaXvF8MpILDUBsj0gkDaNdJ5LC6xQ7HSAl73yK-YlOHfauwykxUSg
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Content-Length: 0
< Date: Fri, 28 Aug 2020 08:30:21 GMT
< 
* Connection #0 to host localhost left intact
* Closing connection 0
```

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
