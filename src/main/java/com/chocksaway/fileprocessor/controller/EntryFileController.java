package com.chocksaway.fileprocessor.controller;

import com.chocksaway.fileprocessor.entity.Request;
import com.chocksaway.fileprocessor.exception.TransportServiceException;
import com.chocksaway.fileprocessor.service.RequestService;
import com.chocksaway.fileprocessor.service.TransportService;
import com.chocksaway.fileprocessor.service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;

@RestController
@RequestMapping("/entry/file/")
public class EntryFileController {
    private final TransportService transportService;
    private final ValidationService validationService;
    private final RequestService  requestService;

    public EntryFileController(TransportService transportService, ValidationService validationService, RequestService  requestService) {
        this.transportService = transportService;
        this.validationService = validationService;
        this.requestService = requestService;
    }

    private static final Logger log = LoggerFactory.getLogger(EntryFileController.class);

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "ip-address") String ipAddress,
            HttpServletRequest httpServletRequest) throws TransportServiceException {

        Date start = new Date();
        Request request = requestService.build(httpServletRequest, start.getTime());

        if (ipAddress == null) {
            ipAddress = request.getIpAddress();
        }

        if (!validationService.validate(ipAddress)) {
            request.setResponseCode(String.valueOf(HttpStatus.FORBIDDEN));
            log.error("Validation for IP address: {} false", request.getIpAddress());
            requestService.save(start.getTime(), request);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "IP address not valid: " + ipAddress);
        }

        request.addValidation(validationService.getIpApiResponse());

        try {
            transportService.parseUploadFile(file);
        } catch (IOException | IllegalArgumentException e) {
            request.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST));
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid file input", e);
        }

        try {
            request.setResponseCode(String.valueOf(HttpStatus.CREATED));
            return ResponseEntity.created(request.asUri())
                    .body(transportService.getSortedTransportOutcomeAsJson());
        } catch (IOException e) {
            request.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST));
            throw new TransportServiceException(e.getMessage());
        } catch (URISyntaxException e) {
            request.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST));
            throw new RuntimeException(e);
        } finally {
            log.info("Saving request:");
            requestService.save(start.getTime(), request);
        }
    }


    @PostMapping("/upload2")
    public ResponseEntity<String> uploadWithNoIpAddress(
            @RequestParam("file") MultipartFile file,
            HttpServletRequest httpServletRequest) throws TransportServiceException {

        Date start = new Date();
        Request request = requestService.build(httpServletRequest, start.getTime());

        if (!validationService.validate(request.getIpAddress())) {
            request.setResponseCode(String.valueOf(HttpStatus.FORBIDDEN));
            log.error("Validation for IP address: {} false", request.getIpAddress());
            requestService.save(start.getTime(), request);
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "IP address not valid: " + request.getIpAddress());
        }

        request.addValidation(validationService.getIpApiResponse());

        try {
            transportService.parseUploadFile(file);
        } catch (IOException | IllegalArgumentException e) {
            request.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST));
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid file input", e);
        }

        try {
            request.setResponseCode(String.valueOf(HttpStatus.CREATED));
            return ResponseEntity.created(request.asUri())
                    .body(transportService.getSortedTransportOutcomeAsJson());
        } catch (IOException e) {
            request.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST));
            throw new TransportServiceException(e.getMessage());
        } catch (URISyntaxException e) {
            request.setResponseCode(String.valueOf(HttpStatus.BAD_REQUEST));
            throw new RuntimeException(e);
        } finally {
            log.info("Saving request:");
            requestService.save(start.getTime(), request);
        }
    }

    @GetMapping("/ping") public String ping() {
        return "pong";
    }
}
