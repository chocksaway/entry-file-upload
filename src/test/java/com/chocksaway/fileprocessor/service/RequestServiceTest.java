package com.chocksaway.fileprocessor.service;

import com.chocksaway.fileprocessor.entity.Request;
import com.chocksaway.fileprocessor.repository.RequestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
public class RequestServiceTest {
    @Autowired
    RequestRepository repository;

    @Test
    public void when_find_by_uuid_then_return_request_entity() {
        repository.deleteAll();
        Iterable<Request> iter = repository.findAll();

        assertEquals(0, size(iter));

        Request request = new Request("unique-name", "one", "two", "three", "four",
                "five", "six", "seven");

        repository.save(request);
        iter = repository.findAll();

        assertEquals(1, size(iter));

        Optional<Request> found = repository.findByName("unique-name");

        assertTrue(found.isPresent());
        assertEquals("unique-name", found.get().getName());
    }

    private static int size(Iterable<Request> data) {
        if (data instanceof Collection) {
            return ((Collection<?>) data).size();
        }
        int counter = 0;
        for (Object ignored : data) {
            counter++;
        }
        return counter;
    }
}
