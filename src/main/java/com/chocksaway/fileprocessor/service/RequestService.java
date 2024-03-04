package com.chocksaway.fileprocessor.service;

import com.chocksaway.fileprocessor.entity.Request;
import com.chocksaway.fileprocessor.repository.RequestRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RequestService {
    private final RequestRepository requestRepository;

    private static final Logger log = LoggerFactory.getLogger(RequestService.class);

    public RequestService(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    private void save(Request request) {
        requestRepository.save(request);
    }

    public void save(long timeLapsed, Request request) {
        request.buildName();
        request.setTimeLapsed(timeLapsed);
        log.info("Saving request {}", request);
        save(request);
    }

    public Iterable<Request> findAll() {
        return requestRepository.findAll();
    }

    public Request build(HttpServletRequest httpServletRequest, long timeNow) {
        return Request.build(httpServletRequest, timeNow);
    }
}
