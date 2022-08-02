package com.aek.kafka.consumer.configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.instrumentation.spring.kafka.v2_7.SpringKafkaTelemetry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.ContainerCustomizer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

/**
 * This class concern broker configuration. In case Kafka
 */

@Configuration
@EnableKafka
public class ConsumerConfiguration {

    private final Environment env;

    public ConsumerConfiguration(Environment env) {
        this.env = env;
    }

    @Autowired
    OpenTelemetry openTelemetry;
    @Bean
    public ConsumerFactory<String, String> consumerFactory(){
        Map<String,Object> config=new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(config);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String,String> kakfaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, String> factory=new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setContainerCustomizer(listenerCustomizer()::configure);
        return factory;
    }

    @Bean
    public ContainerCustomizer<String, String, ConcurrentMessageListenerContainer<String, String>>
    listenerCustomizer() {
        SpringKafkaTelemetry springKafkaTelemetry = SpringKafkaTelemetry.create(openTelemetry);
        springKafkaTelemetry.createRecordInterceptor();

        return container -> {
            container.setRecordInterceptor(springKafkaTelemetry.createRecordInterceptor());
            container.setBatchInterceptor(springKafkaTelemetry.createBatchInterceptor());
        };
    }
}
