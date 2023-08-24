# hello
Hello World service for testing purposes

## Build
```shell
./mvnw clean package
```
```shell
docker build -t hurban/hello .
```

## Run
### Via command line
```shell
java -jar target/hello-1.0.0.jar 
```
### using Docker
```shell
docker run -d -p 8080:8080 hurban/hello
```

## Test
```shell
curl --location 'http://localhost:8080/hello'
```

