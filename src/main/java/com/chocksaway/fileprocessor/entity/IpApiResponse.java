package com.chocksaway.fileprocessor.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpApiResponse {
    private final String status;
    private final String country;

    @JsonCreator
    public IpApiResponse(@JsonProperty("status") String status,
                         @JsonProperty("country") String country) {
        this.status = status;
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }
}
