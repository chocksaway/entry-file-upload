package com.chocksaway.fileprocessor.controller;

import com.chocksaway.fileprocessor.entity.Transport;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.text.DecimalFormat;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class EntityFileControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    private static final String VALID_ENTRY_FILE_INPUT = """
                18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1
                3ce2d17b-e66a-4c1e-bca3-40eb1c9222c7|2X2D24|Mike Smith|Likes Grape|Drives an SUV|35.0|95.5
                1afb6f5d-a7c2-4311-a92d-974f3180ff5e|3X3D35|Jenny Walters|Likes Avocados|Rides A Scooter|8.5|15.3
                """;

    @Test
    public void when_entry_file_is_null()
            throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "invalid".getBytes()
        );

        MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("/entry/file/upload").file(file))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    public void when_entry_file_uploaded_then_verify_201_status_and_three_sorted_transport_ascending_top_speed()
            throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                VALID_ENTRY_FILE_INPUT.getBytes()
        );

        MockMvc mockMvc
                = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        MvcResult result = mockMvc.perform(multipart("/entry/file/upload").file(file))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Transport> transportList = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>(){});

        DecimalFormat df = new DecimalFormat("##.#");
        Assertions.assertEquals("12.1", df.format(transportList.get(0).getTopSpeed()));
        Assertions.assertEquals("15.3", df.format(transportList.get(1).getTopSpeed()));
        Assertions.assertEquals("95.5", df.format(transportList.get(2).getTopSpeed()));
    }
}
