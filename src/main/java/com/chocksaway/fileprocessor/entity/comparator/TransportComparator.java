package com.chocksaway.fileprocessor.entity.comparator;

import com.chocksaway.fileprocessor.entity.Transport;

import java.util.Comparator;

public class TransportComparator implements Comparator<Transport> {
    @Override
    public int compare(Transport t1, Transport t2) {
        return Float.compare(t1.getTopSpeed(), t2.getTopSpeed());
    }
}
