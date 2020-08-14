# spring-kafka-integration

### Prerequisites
- JDK 1.8
- Maven 3.x
- Docker
##
### Run kafka container
In a terminal, run
```
docker-compose up -d
```

##
### Build
```
git clone https://github.com/anicetkeric/spring-kafka-integration.git
cd spring-kafka-integration
```
#### Run Producer
```
cd producer

mvn clean package
```
#### Run Consumer
```
cd consumer

mvn clean package
```
