package vn.com.carrentalsystem.enums;

import java.io.Serializable;

public enum Role implements Serializable {

    ROLE_ADMIN("Admin"),
    ROLE_CUSTOMER("Customer"),
    ROLE_CAROWNER("Car Owner");

    private final String status;

    Role(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
