package com.chocksaway.fileprocessor.util;

import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UriTest {
    @Test
    public void request_uri_uri_without_exception() {
        final String requestUri = "/entry/file/upload";

        try {
            URI uri = new URI("/entry/file/upload");
            assertEquals(requestUri, uri.getPath());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
