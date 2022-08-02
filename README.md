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

##### post a message via swagger ui
![capture 1](https://github.com/anicetkeric/spring-kafka-integration/blob/master/screens/swagger.png)


##### produce message
![capture 2](https://github.com/anicetkeric/spring-kafka-integration/blob/master/screens/producer.png)


##### consume message
![capture 3](https://github.com/anicetkeric/spring-kafka-integration/blob/master/screens/consumer.png)
