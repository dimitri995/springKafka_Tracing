package com.aek.kafka.producer.configuration;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.ContextPropagators;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporterBuilder;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.resources.Resource;
import io.opentelemetry.sdk.trace.IdGenerator;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.BatchSpanProcessor;
import io.opentelemetry.sdk.trace.export.SpanExporter;
import io.opentelemetry.semconv.resource.attributes.ResourceAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


@Configuration
public class OtelConfig  {

    @Bean
    public OpenTelemetry openTelemetry() throws IOException {

//        IdGenerator id=IdGenerator.random();
//        System.out.println(("id generator"+id.generateTraceId()));
        File file = new File("../../../../security/ca.pem");
        byte[] bytes_cert = Files.readAllBytes(file.toPath());

        SpanExporter spanExporter = OtlpGrpcSpanExporter.builder()
                .setEndpoint("http://127.0.0.1:4320") //TODO Replace <URL> to your SaaS/Managed-URL as mentioned in the next step
                .setTrustedCertificates(bytes_cert)
//                .addHeader("Authorization", "Api-Token <TOKEN>") //TODO Replace <TOKEN> with your API Token as mentioned in the next step
                .build();

        SdkTracerProvider sdkTracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(BatchSpanProcessor.builder(spanExporter).build())
//                .setIdGenerator(id)
                .setResource(Resource.create(Attributes.of(ResourceAttributes.SERVICE_NAME,"producer")))
                .build();

        OpenTelemetry openTelemetry = OpenTelemetrySdk.builder()
                .setTracerProvider(sdkTracerProvider)
                .setPropagators(ContextPropagators.create(W3CTraceContextPropagator.getInstance()))
                .buildAndRegisterGlobal();

        return openTelemetry;
    }

}
