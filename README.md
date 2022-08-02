# spring-kafka-integration

##### Architecture
![capture 1](https://github.com/dimitri995/springKafka_Tracing/blob/dd3d87e12be78da348aa708748f04d2bcadee64d/img/archi.png)

##### tracing kafka message with Zipkin
![capture 2](https://github.com/dimitri995/springKafka_Tracing/blob/master/img/zipkin.png)
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

```
#### Run Producer
```
cd producer
java -jar target/producer-0.0.1-SNAPSHOT.jar
```
#### Run Consumer
```
cd consumer
java -jar target/consumer-0.0.1-SNAPSHOT.jar
```
#### Output

Go to http://localhost:8082/api and post a message via swagger ui.


