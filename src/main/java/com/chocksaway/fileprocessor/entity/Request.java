package com.chocksaway.fileprocessor.entity;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.UUID;

@Entity
public class Request {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String uri;
    private String timeStamp;
    private String responseCode;
    private String ipAddress;
    private String countryCode;
    private String ipProvider;
    private String timeLapsed;

    public Request(String name, String uri, String timeStamp, String responseCode,
                   String ipAddress, String countryCode, String ipProvider, String timeLapsed) {
        this.uri = uri;
        this.name = name;
        this.timeStamp = timeStamp;
        this.responseCode = responseCode;
        this.ipAddress = ipAddress;
        this.countryCode = countryCode;
        this.ipProvider = ipProvider;
        this.timeLapsed = timeLapsed;
    }

    public Request() {
    }

    public void buildName() {
        this.name = this.timeStamp + "_" + this.uri + "_" + this.ipAddress + "_" + this.responseCode;
    }

    @Override
    public String toString() {
        final String style = """
                Request[id=%s, name='%s', timeStamp='%s', responseCode='%s', ipAddress='%s', countryCode='%s', ipProvider='%s', timeLapsed='%s'
                """;
        return String.format(
                style,
                id, name, timeStamp, responseCode, ipAddress, countryCode, ipProvider, timeLapsed);
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

    /**
     * @param start - milliseconds when upload started
     */
    public void setTimeLapsed(long start) {
        long elapsed = new Date().getTime() - start;
        this.timeLapsed = String.valueOf(elapsed);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Request build(HttpServletRequest request, long timeNow) {
        Request buildRequest = new Request();
        buildRequest.ipAddress = request.getRemoteAddr();
        buildRequest.uri = request.getRequestURI();
        buildRequest.timeStamp = String.valueOf(timeNow);
        return buildRequest;
    }

    public void addValidation(IpApiResponse ipApiResponse) {
        this.countryCode = ipApiResponse.getCountry();
        this.ipProvider = ipApiResponse.getIsp();
    }

    public URI asUri() throws URISyntaxException {
        URI uri;
        uri = new URI(this.uri);
        return uri;
    }


}
