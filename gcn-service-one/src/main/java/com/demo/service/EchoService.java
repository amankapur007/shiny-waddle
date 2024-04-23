package com.demo.service;

import java.util.Optional;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.demo.model.EchoMessageModel;
import com.demo.repo.EchoRepo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import jakarta.inject.Singleton;
import reactor.core.publisher.MonoSink;

@Singleton // Equivalent to @Service
public class EchoService{
    private final Logger log = LoggerFactory.getLogger(EchoService.class);

    private final EchoRepo repo;

    public EchoService(EchoRepo repo) {
        this.repo = repo;
    }

    public void echoMessage(String message, MonoSink result) throws InterruptedException{
        log.info("EchoService: Echoing the message");
        Thread.sleep(2000);
        log.info("EchoService: I am awake");
        result.success(HttpResponse.ok(message));
    }

    public void postMessage(String message, MonoSink arg0) {
        log.info("EchoService: Posting the message");
       EchoMessageModel r= repo.save(EchoMessageModel.builder().message(message).build());
        arg0.success(HttpResponse.status(HttpStatus.CREATED).body(r));
        log.info("EchoService: Done");        
    }

    public void getMessages(Long id, MonoSink<Object> arg0) {
        log.info("EchoService: Getting the messages");
        List<EchoMessageModel> l =  new ArrayList<>();
        if(id == null){
            arg0.success(repo.findAll());
            return;
        }
        Optional<EchoMessageModel> m = repo.findById(id);
        if(m.isPresent()){
            l.add(m.get());
            arg0.success(HttpResponse.ok().body(l));
        }else{
            arg0.success(HttpResponse.notFound("Not found"));
        }
    }
}