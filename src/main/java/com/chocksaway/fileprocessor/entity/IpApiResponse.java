package com.chocksaway.fileprocessor.entity;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IpApiResponse {
    private final String status;
    private final String country;
    private final String isp;

    @JsonCreator
    public IpApiResponse(@JsonProperty("status") String status,
                         @JsonProperty("country") String country,
                         @JsonProperty("country") String isp) {
        this.status = status;
        this.country = country;
        this.isp = isp;

    }

    public String getCountry() {
        return country;
    }

    public String getStatus() {
        return status;
    }

    public String getIsp() {
        return isp;
    }

    public boolean validState() {
        return !this.status.equals("fail");
    }

    public boolean validateCountry() {
        enum invalidCountry {
            China,
            Spain,
            USA
        }

        return Arrays.stream(invalidCountry.values())
                .noneMatch(each -> each.name().equals(this.country));
    }

    public boolean validateIsp() {
        enum invalidIsp {
            Amazon,
            Google,
            Microsoft
        }

        return Arrays.stream(invalidIsp.values())
                .noneMatch(each -> each.name().toLowerCase().contains(this.isp.toLowerCase()));
    }
}
