package vn.com.carrentalsystem.enums;

import java.io.Serializable;

public enum TransmissionType implements Serializable {

    AUTOMATIC("Automatic"),
    MANUAL("Manual");

    private final String status;

    TransmissionType(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

}
