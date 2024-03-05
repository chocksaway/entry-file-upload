package com.chocksaway.fileprocessor.controller;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.get;

import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@EnableWireMock({
        @ConfigureWireMock(name = "validation-service", property = "ipApiBaseUrl")
})

public class EntityFileControllerWithWiremockTest {
    private static final String VALID_ENTRY_FILE_INPUT = """
                18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
                3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
                1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
                """;
    @InjectWireMock("validation-service")
    private WireMockServer wiremock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private Environment env;

    @Test
    public void when_mocked_ip_address_is_invalid_and_returns_status_if_forbidden() throws Exception {
        // returns a URL to WireMockServer instance
        env.getProperty("ipApiBaseUrl");
        wiremock.stubFor(get("/json/127.0.0.1").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                       {"status":"fail","message":"invalid query","query":"tester2"}
                        """)
        ));

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "invalid".getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/entry/file/upload2").file(file))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }


    @Test
    public void when_valid_entry_file_uploaded_then_wiremock_verify_201_status_and_three_sorted_transport_ascending_top_speed() throws Exception {
        // returns a URL to WireMockServer instance
        env.getProperty("ipApiBaseUrl");
        wiremock.stubFor(get("/json/127.0.0.1").willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                        {
                           "query": "217.155.19.80",
                           "status": "success",
                           "country": "United Kingdom",
                           "countryCode": "GB",
                           "region": "ENG",
                           "regionName": "England",
                           "timezone": "Europe/London",
                           "isp": "Zen Internet Ltd",
                           "org": "Central Networks & Technologies Ltd",
                           "as": "AS13037 Zen Internet Ltd"
                                                                                     }
                        """)
        ));

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                VALID_ENTRY_FILE_INPUT.getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/entry/file/upload2").file(file))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();
    }














}