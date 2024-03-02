package com.chocksaway.fileprocessor.controller;

import com.chocksaway.fileprocessor.exception.TransportServiceException;
import com.chocksaway.fileprocessor.service.TransportService;
import com.chocksaway.fileprocessor.service.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@RestController
@RequestMapping("/entry/file/")
public class EntryFileController {

    @Autowired
    TransportService transportService;

    @Autowired
    ValidationService validationService;
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file,
            @RequestParam(value = "ip-address") String ipAddress) throws TransportServiceException {
        if (!validationService.validate(ipAddress)) {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "IP address not valid: " + ipAddress);
        }

        try {
            transportService.parseUploadFile(file);
        } catch (IOException | IllegalArgumentException e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid file input", e);
        }

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/upload")
                .buildAndExpand("tester")
                .toUri();

        try {
            return ResponseEntity.created(uri)
                    .body(transportService.getSortedTransportOutcomeAsJson());
        } catch (IOException e) {
            throw new TransportServiceException(e.getMessage());
        }
    }

    @GetMapping("/ping") public String ping() {
        return "pong";
    }
}
