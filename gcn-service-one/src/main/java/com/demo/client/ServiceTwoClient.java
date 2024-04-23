package com.demo.client;

import com.demo.retry.ServerErrorRetryPredicate;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import reactor.core.publisher.Mono;

@Client("${service.two}")
@Retryable(attempts = "4", predicate = ServerErrorRetryPredicate.class)
public interface ServiceTwoClient {
    
    @Get(value = "/v1/helloWorld")
    Mono<HttpResponse<String>> helloWorld();
}
