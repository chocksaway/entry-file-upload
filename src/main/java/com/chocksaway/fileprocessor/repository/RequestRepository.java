package com.chocksaway.fileprocessor.repository;

import com.chocksaway.fileprocessor.entity.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RequestRepository extends CrudRepository<Request, Long> {
    Optional<Request> findByName(String name);

}
