package com.chocksaway.fileprocessor.service;

import com.chocksaway.fileprocessor.entity.IpApiResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ValidationService {
    // http is in the spec
    final static String IP_API_DOT_COM_JSON_ENDPOINT = "http://ip-api.com/json/";
    public boolean validate(final String address) {
        RestTemplate restTemplate = new RestTemplate();
        String endpoint
                = IP_API_DOT_COM_JSON_ENDPOINT + address;
        ResponseEntity<String> response
                = restTemplate.getForEntity(endpoint, String.class);

        Gson gson = new GsonBuilder().create();

        IpApiResponse ipApiResponse = gson.fromJson(response.getBody(), IpApiResponse.class);

        if (!ipApiResponse.validState()) {
            return false;
        } else if (!ipApiResponse.validateCountry()) {
            return false;
        } else {
            return ipApiResponse.validateIsp();
        }
    }
}
