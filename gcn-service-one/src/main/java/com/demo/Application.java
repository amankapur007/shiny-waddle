/*
 * Copyright 2024 Oracle and/or its affiliates
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     https://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.runtime.Micronaut;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

@Controller("/v1") // Same as spring rest controller
class EchoController{

    private final Logger log = LoggerFactory.getLogger(EchoController.class);

    @Inject // This is same as @Autowired but as per document we should use contruction injection
    EchoService echoService;

    // Get / Put / Post / Patch /Delete
    // @QueryValue is same as @RequestParam of spring
    // @Nullable annotation before @QueryValue makes the param as not required
    // HttpResponse is equivalent to ResponseEntity
    @Get(value="/echo", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_PROBLEM})
    public Mono<HttpResponse<String>> echoMessage(@QueryValue("message") String message) throws InterruptedException{
        log.info("EchoController: Echo the message");
        Mono<HttpResponse<String>> m = Mono.create(arg0 -> {
            try {
                echoService.echoMessage(message, arg0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        log.info("EchoController: End of the controller");
        return m;
    }

    // @Body is equivalent to ResponseBody
    @Post(value = "/echo")
    public HttpResponse<String> postMessage(@Body String message) {
        log.info("EchoController: Post the message");
        return HttpResponse.created(message);
    }
}

@Singleton // Equivalent to @Service
class EchoService{
    private final Logger log = LoggerFactory.getLogger(EchoService.class);

    public void echoMessage(String message, MonoSink<HttpResponse<String>> result) throws InterruptedException{
        log.info("EchoService: Echoing the message");
        Thread.sleep(2000);
        log.info("EchoService: I am awake");
        result.success(HttpResponse.ok(message));
    }
}