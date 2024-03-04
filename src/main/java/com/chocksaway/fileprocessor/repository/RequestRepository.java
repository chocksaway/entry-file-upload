package com.chocksaway.fileprocessor.repository;

import com.chocksaway.fileprocessor.entity.Request;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RequestRepository extends CrudRepository<Request, Long> {
    Request findById(UUID id);

}
