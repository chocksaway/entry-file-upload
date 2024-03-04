package com.chocksaway.fileprocessor.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Request {
    @Id
    @GeneratedValue
    private UUID id;
    private String uri;
    private String timeStamp;
    private String responseCode;
    private String ipAddress;
    private String countryCode;
    private String ipProvider;
    private String timeLapsed;

    public Request(String uri, String timeStamp, String responseCode,
                   String ipAddress, String countryCode, String ipProvider, String timeLapsed) {
        this.uri = uri;
        this.timeStamp = timeStamp;
        this.responseCode = responseCode;
        this.ipAddress = ipAddress;
        this.countryCode = countryCode;
        this.ipProvider = ipProvider;
        this.timeLapsed = timeLapsed;
    }

    public Request() {
    }

    @Override
    public String toString() {
        return String.format(
                "Request[id=%s, uri='%s', timestamp='%s']",
                id, uri, timeStamp);
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
        // uri, timestamp, response_code, ip_Address, country_code, ip_provider, time_lapsed
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getIpProvider() {
        return ipProvider;
    }

    public void setIpProvider(String ipProvider) {
        this.ipProvider = ipProvider;
    }

    public String getTimeLapsed() {
        return timeLapsed;
    }

    public void setTimeLapsed(String timeLapsed) {
        this.timeLapsed = timeLapsed;
    }
}
