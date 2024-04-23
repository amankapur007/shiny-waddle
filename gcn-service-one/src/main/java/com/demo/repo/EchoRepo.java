package com.demo.repo;

import com.demo.model.EchoMessageModel;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

@Repository
public interface EchoRepo extends CrudRepository<EchoMessageModel, Long>{
    
}
