package com.demo.filters;

import java.time.Duration;
import java.time.Instant;

import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import reactor.core.publisher.Flux;

@Filter("/**")
public class CustomFilter implements HttpServerFilter{
    
    private static Logger log = LoggerFactory.getLogger(CustomFilter.class);

    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request, ServerFilterChain chain){
        Instant startTime = Instant.now();
        log.info("Filter: Start - {} ", startTime.toString());
        return Flux.from(chain.proceed(request))
        .doOnComplete(() -> {
            Instant end = Instant.now();
            log.info("Filter: End - {} ", end.toString());
            log.info("Filter: Total time - {}", Duration.between(startTime, end));
        }).doOnNext(arg0 -> arg0.getHeaders().add("x-total-time", Duration.between(startTime, Instant.now()).toString()));
    }
    
}
