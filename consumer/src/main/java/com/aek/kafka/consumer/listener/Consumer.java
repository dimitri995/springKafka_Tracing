package com.aek.kafka.consumer.listener;

import com.aek.kafka.consumer.model.BankTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import io.opentelemetry.api.trace.Span;
//import io.opentelemetry.api.trace.SpanKind;
//import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ContainerCustomizer listenerCustomizer;
    @KafkaListener(topics = "transaction", groupId = "transaction-group-id", containerFactory = "kakfaListenerContainerFactory")
    public void listenSenderEmail(String data) {
        log.info("Consumed message: " + data);
    }

}
