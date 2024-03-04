package com.chocksaway.fileprocessor;

import com.chocksaway.fileprocessor.entity.Request;
import com.chocksaway.fileprocessor.repository.RequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EntryFileApplication {
    private static final Logger log = LoggerFactory.getLogger(EntryFileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EntryFileApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(RequestRepository repository) {
        return (args) -> {
            repository.save(new Request("one", "two", "three", "four",
                    "five", "six", "seven"));

            repository.findAll().forEach(request -> log.info(request.toString()));
        };
    }
}
