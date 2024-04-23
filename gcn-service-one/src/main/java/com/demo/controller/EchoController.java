package com.demo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.service.EchoService;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import reactor.core.publisher.Mono;

@Controller("/v1") // Same as spring rest controller
class EchoController{

    private final Logger log = LoggerFactory.getLogger(EchoController.class);

    private final EchoService echoService;

    public EchoController(EchoService echoService) {
        this.echoService = echoService;
    }

    @Get(value="/echo", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_PROBLEM})
    public Mono<?> echoMessage(@Nullable @QueryValue("message") String message) throws InterruptedException{
        log.info("EchoController: Echo the message");
        Mono<?> m = Mono.create(arg0 -> {
            try {
                if(message!=null){
                echoService.echoMessage(message, arg0);
                }else{
                    echoService.getMessages(null, arg0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("EchoController: End of the controller");
        return m;
    }

    @Post(value = "/echo")
    public Mono<HttpResponse<?>> postMessage(@Body String message) {
        log.info("EchoController: Post the message");
        return Mono.create(arg0 -> echoService.postMessage(message, arg0));
    }

    @Get(value = "/echo/{id}")
    public Mono<?> echoById(@PathVariable("id") Long id) {
        log.info("EchoController: Getting the message by id - {}", id);
        return Mono.create(arg0 -> echoService.getMessages(id, arg0));
    }

    @Get("/helloWorld")
    public Mono<?> helloWorld() {
        log.info("EchoController: Hello World");
        return Mono.create(arg0 -> echoService.helloWorld(arg0));

    }
}
