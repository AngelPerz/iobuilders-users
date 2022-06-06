# iobuilders-users
Users microservice of the simulated small bank for ioBuilders

compiled and tested with:
``` 
graalvm-ce-java17-21.3.1 
```

## Installation
To compile the application use the Maven wrapper launching the following command in the project's root directory:
```
./mvnw clean install
```

## Requirements
This microservice requires the dockerized Postgres database declared in the same project to be running:
``` 
/iobuilders-users/docker-compose/docker-compose.yml
```
Using the command:
```
docker-compose up -d
```

## Usage
Start the application and send REST requests to the API, by default in `http://localhost:8080/`

For API documentation and local testing the Swagger Page is available in
[http://localhost:8080/swagger-ui/#/](http://localhost:8080/swagger-ui/#/)