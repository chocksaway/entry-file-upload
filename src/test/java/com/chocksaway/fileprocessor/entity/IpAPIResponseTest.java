package com.chocksaway.fileprocessor.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class IpAPIResponseTest {
    @Test
    public void validate_country_which_is_not_usa_china_spain_is_true() {
        IpApiResponse response = new IpApiResponse("ok", "UK", "xyz");

        Assertions.assertTrue(response.validateCountry());
    }

    @Test
    public void validate_country_which_is_usa_china_spain_is_false() {
        IpApiResponse response = new IpApiResponse("ok", "Spain", "xyz");
        Assertions.assertFalse(response.validateCountry());

        response = new IpApiResponse("ok", "China", "xyz");
        Assertions.assertFalse(response.validateCountry());

        response = new IpApiResponse("ok", "USA", "xyz");
        Assertions.assertFalse(response.validateCountry());
    }

    @Test
    public void validate_isp_which_is_not_aws_azure_gcp_is_true() {
        IpApiResponse response = new IpApiResponse("ok", "UK", "Zen");

        Assertions.assertTrue(response.validateIsp());
    }

    @Test
    public void validate_isp_which_is_aws_azure_gcp_is_false() {
        IpApiResponse response = new IpApiResponse("ok", "UK", "microsoft");

        Assertions.assertFalse(response.validateIsp());
    }

    @Test
    public void validate_state_which_is_fail_is_false() {
        IpApiResponse response = new IpApiResponse("fail", "UK", "Zen");

        Assertions.assertFalse(response.validState());
    }

    @Test
    public void validate_state_which_is_not_fail_is_true() {
        IpApiResponse response = new IpApiResponse("success", "UK", "Zen");

        Assertions.assertTrue(response.validState());
    }
}
