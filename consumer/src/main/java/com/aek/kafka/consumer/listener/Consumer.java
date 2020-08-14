package com.aek.kafka.consumer.listener;

import com.aek.kafka.consumer.model.BankTransaction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {

    private static final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "transaction", groupId = "transaction-group-id", containerFactory = "kakfaListenerContainerFactory")
    public void listenSenderEmail(String data) throws JsonProcessingException {

        BankTransaction dataConsumer = mapper.readValue(data, BankTransaction.class);
        log.info("Consumed message: " + data);

    }

}
