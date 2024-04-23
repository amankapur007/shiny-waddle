package com.demo.client;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.client.annotation.Client;
import reactor.core.publisher.Mono;

@Client("${service.two}")
public interface ServiceTwoClient {
    
    @Get(value = "/v1/helloWorld")
    Mono<HttpResponse<String>> helloWorld();
}
