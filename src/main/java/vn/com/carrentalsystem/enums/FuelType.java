package vn.com.carrentalsystem.enums;

import java.io.Serializable;

public enum FuelType implements Serializable {
    GASOLINE("Gasoline"),
    DIESEL("Diesel");

    private final String status;

    FuelType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
