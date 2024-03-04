package com.chocksaway.fileprocessor.controller;

import com.chocksaway.fileprocessor.entity.Request;
import com.chocksaway.fileprocessor.exception.TransportServiceException;
import com.chocksaway.fileprocessor.service.RequestService;
import com.chocksaway.fileprocessor.service.TransportService;
import com.chocksaway.fileprocessor.service.ValidationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    TransportService transportService;

    @Autowired
    ValidationService validationService;

    @Autowired
    RequestService  requestService;

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
        } finally {
            requestService.save(start.getTime(), request);
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
            requestService.save(start.getTime(), request);
        }
    }

    @GetMapping("/ping") public String ping() {
        return "pong";
    }
}
