package com.demo.config;

import io.micronaut.context.annotation.Value;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.endpoint.annotation.Endpoint;
import io.micronaut.management.endpoint.annotation.Read;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.micronaut.management.health.indicator.annotation.Readiness;
import jakarta.inject.Singleton;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;


@Endpoint(id = "startup",
        prefix = "custom",
        defaultEnabled = true,
        defaultSensitive = false)
@Readiness
@Singleton
public class StartupIndicator implements HealthIndicator {

    @Value("${app.up}")
    String appUp;

    private boolean isServiceReady;
    private String serviceName = "gcn-service-one";

    @Read
    @Override
    public Publisher<HealthResult> getResult() {
        isServiceReady = Boolean.parseBoolean(appUp);
        HealthResult.Builder builder = HealthResult.builder(serviceName);
        if(isServiceReady){
            builder.status(HealthStatus.UP);
        }else{
            builder.status(HealthStatus.DOWN);
        }
        return Flux.just(builder.build());
    }
}
