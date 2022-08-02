package com.aek.kafka.producer.web;

import com.aek.kafka.producer.model.BankTransaction;
import com.aek.kafka.producer.service.ProducerService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/producer")
public class ProducerResource {

    private final ProducerService producerService;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final Environment env;

    public ProducerResource(ProducerService producerService, Environment env) {
        this.producerService = producerService;
        this.env = env;
    }


    @PostMapping("/send")
    public ResponseEntity<Void> sendTransaction(@RequestBody BankTransaction notification) throws JsonProcessingException {
        producerService.sendMessage(env.getProperty("producer.kafka.topic-name"), mapper.writeValueAsString(notification));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
