package com.spart.kafka.producer.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.TracerProvider;
import io.opentelemetry.extension.annotations.WithSpan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerResource {

    private final Environment env;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    public ProducerResource(Environment env) {
        this.env = env;
    }

    @PostMapping("/send")
    public ResponseEntity<Void> sendTransaction(@RequestBody String notification) throws JsonProcessingException {
        kafkaTemplate.send(env.getProperty("producer.kafka.topic-name"), notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
