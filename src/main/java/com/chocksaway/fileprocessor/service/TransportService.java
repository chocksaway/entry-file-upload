package com.chocksaway.fileprocessor.service;

import com.chocksaway.fileprocessor.entity.Transport;
import com.chocksaway.fileprocessor.entity.comparator.TransportComparator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransportService {
    List<Transport> transportOutcome = new ArrayList<>();
    public void parseUploadFile(MultipartFile file) throws IOException {
        this.transportOutcome = Arrays.stream(new String(file.getBytes()).split("\n")).map(each -> {

            String[] eachSplit = each.split("\\|");
            if (eachSplit.length < 6) {
                throw new IllegalArgumentException("Input should be positive");
            }
            float topSpeed = Float.parseFloat(eachSplit[6]);
            return new Transport(eachSplit[2], eachSplit[4], topSpeed);
        }).collect(Collectors.toList());
    }

    public String getSortedTransportOutcomeAsJson() throws IOException {
        TransportComparator transportComparator = new TransportComparator();
        transportOutcome.sort(transportComparator);

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, transportOutcome);
        return out.toString();
    }
}
