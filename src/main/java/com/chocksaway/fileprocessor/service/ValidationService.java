package com.chocksaway.fileprocessor.service;

import com.chocksaway.fileprocessor.entity.IpApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidationService {
    private IpApiResponse ipApiResponse;
    // http is in the spec

    @Value("${spring.ipApiBaseUrl}") String ipApiBaseUrl;

    public boolean validate(final String address) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint
                = ipApiBaseUrl + "/json/" + address;
        ResponseEntity<String> response
                = restTemplate.getForEntity(endpoint, String.class);

        Gson gson = new GsonBuilder().create();

        ipApiResponse = gson.fromJson(response.getBody(), IpApiResponse.class);

        if (!ipApiResponse.validState()) {
            return false;
        } else if (!ipApiResponse.validateCountry()) {
            return false;
        } else {
            return ipApiResponse.validateIsp();
        }
    }

    public IpApiResponse getIpApiResponse() {
        return ipApiResponse;
    }
}
