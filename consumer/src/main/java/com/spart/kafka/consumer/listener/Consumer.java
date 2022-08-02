package com.spart.kafka.consumer.listener;

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

    @KafkaListener(topics = "tracing", groupId = "tracing-group-id", containerFactory = "kakfaListenerContainerFactory")
    public void listenSenderEmail(String data) {
        log.info("Consumed message: " + data);
    }

}
