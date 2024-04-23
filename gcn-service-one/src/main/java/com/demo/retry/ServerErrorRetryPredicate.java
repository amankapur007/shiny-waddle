package com.demo.retry;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.retry.annotation.RetryPredicate;

public class ServerErrorRetryPredicate implements RetryPredicate{

    @Override
    public boolean test(Throwable arg0) {
        if(arg0 instanceof HttpClientResponseException ex){
            return ex.getStatus()!=HttpStatus.OK;
        }
        return false;
    }
    
}
