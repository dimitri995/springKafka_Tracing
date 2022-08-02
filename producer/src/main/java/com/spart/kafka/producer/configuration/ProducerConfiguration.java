package com.spart.kafka.producer.configuration;


import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.instrumentation.kafkaclients.KafkaTelemetry;
import io.opentelemetry.instrumentation.kafkaclients.TracingProducerInterceptor;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaProducerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ProducerConfiguration {


    private final Environment env;

    public ProducerConfiguration(Environment env) {
        this.env = env;
    }

    // @Autowired
    // OpenTelemetry openTelemetry;
    
    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, env.getProperty("spring.kafka.bootstrap-servers"));
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 101680096);

        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        // value to block, after which it will throw a TimeoutException
        props.put(ProducerConfig.MAX_BLOCK_MS_CONFIG, 5000);

        List<Class> interceptors = new ArrayList<>();
        interceptors.add(TracingProducerInterceptor.class);
        props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        DefaultKafkaProducerFactory defaultKafkaProducerFactory = new DefaultKafkaProducerFactory<>(producerConfigs());
//        producerFactoryCustomizer().customize(defaultKafkaProducerFactory);
        return  defaultKafkaProducerFactory;
    }
//
//    @Bean
//    public DefaultKafkaProducerFactoryCustomizer producerFactoryCustomizer() {
//        KafkaTelemetry kafkaTelemetry = KafkaTelemetry.create(openTelemetry);
//        kafkaTelemetry.metricConfigProperties();
//        return producerFactory -> producerFactory.addPostProcessor(kafkaTelemetry::wrap);
//    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

}
