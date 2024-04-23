package com.demo.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micronaut.context.annotation.Factory;
import io.micronaut.health.HealthStatus;
import io.micronaut.management.endpoint.health.HealthLevelOfDetail;
import io.micronaut.management.health.aggregator.HealthAggregator;
import io.micronaut.management.health.indicator.HealthIndicator;
import io.micronaut.management.health.indicator.HealthResult;
import io.micronaut.scheduling.annotation.Scheduled;
import reactor.core.publisher.Mono;

@Factory
public class HealthIndicatorMetrics {
    private final HealthIndicator[] healthIndicators;
    private final HealthAggregator<HealthResult> healthAggregator;
    private final MeterRegistry meterRegistry;

    // This si constructor dependency
    public HealthIndicatorMetrics(HealthIndicator[] healthIndicators, HealthAggregator<HealthResult> healthAggregator,
            MeterRegistry meterRegistry) {
        this.healthIndicators = healthIndicators;
        this.healthAggregator = healthAggregator;
        this.meterRegistry = meterRegistry;
    }

    @Scheduled(fixedDelay = "${micronaut.health.monitor.interval:10s}", initialDelay = "${micronaut.health.monitor.initial-delay:10s}")
    void monitor(){
        HealthLevelOfDetail detail = HealthLevelOfDetail.STATUS_DESCRIPTION_DETAILS;

        HealthResult r = Mono.from(healthAggregator.aggregate(healthIndicators, detail)).block();
        if(r.getStatus() == HealthStatus.UP){
            meterRegistry.gauge("gcn_service_one_health", 1);
        }else{
            meterRegistry.gauge("gcn_service_one_health", 0);
        }
    }

    
}
