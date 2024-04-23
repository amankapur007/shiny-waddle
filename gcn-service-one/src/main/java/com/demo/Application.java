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

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }
}

@Controller("/v1") //(1) Same as spring rest controller
class EchoController{

    private final Logger log = LoggerFactory.getLogger(EchoController.class);

    //(2) Get / Put / Post / Patch /Delete
    //(3) @QueryValue is same as @RequestParam of spring
    //(4) @Nullable annotation before @QueryValue makes the param as not required
    //(5) HttpResponse is equivalent to ResponseEntity
    @Get(value="/echo", produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_PROBLEM})
    public HttpResponse<String> echoMessage(@QueryValue("message") String message){
        log.info("EchoController: Echo the message");
        return HttpResponse.ok(message);
    }

    //(6) @Body is equivalent to ResponseBody
    @Post(value = "/echo")
    public HttpResponse<String> postMessage(@Body String message){
        log.info("EchoController: Post the message");
        return HttpResponse.created(message);
    }
}