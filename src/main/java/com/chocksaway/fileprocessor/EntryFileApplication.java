package com.chocksaway.fileprocessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EntryFileApplication {
    private static final Logger log = LoggerFactory.getLogger(EntryFileApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EntryFileApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner demo(RequestRepository repository) {
//        return (args) -> {
//            repository.save(new Request("test-name", "one", "two", "three", "four",
//                    "five", "six", "seven"));
//
//            repository.findAll().forEach(request -> log.info(request.toString()));
//        };
//    }
}
