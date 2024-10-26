package vn.com.carrentalsystem.enums;

import java.io.Serializable;

public enum CarStatus implements Serializable {
    AVAILABLE("Available"),
    STOPPED("Stopped"),
    BOOKED("Booked");

    private final String status;

    CarStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
