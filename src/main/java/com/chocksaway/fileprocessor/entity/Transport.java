package com.chocksaway.fileprocessor.entity;

import java.util.Objects;

public final class Transport {
    private String name;
    private String type;
    private float topSpeed;

    public Transport(String name, String type, float topSpeed) {
        this.name = name;
        this.type = type;
        this.topSpeed = topSpeed;
    }

    public Transport() {
    }

    @Override
    public String toString() {
        return "Transport[" +
                "name=" + name + ", " +
                "type=" + type + ", " +
                "topSpeed=" + topSpeed + ']';
    }

    public String getName() {
        return this.name;
    }

    public String getType() {
        return this.type;
    }

    public float getTopSpeed() {
        return this.topSpeed;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Transport) obj;
        return Objects.equals(this.name, that.name) &&
                Objects.equals(this.type, that.type) &&
                Float.floatToIntBits(this.topSpeed) == Float.floatToIntBits(that.topSpeed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, topSpeed);
    }
}
