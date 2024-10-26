package vn.com.carrentalsystem.enums;

import java.io.Serializable;

public enum BookingStatus implements Serializable {

    CONFIRMED("Confirmed"),
    IN_PROGRESS("In Progress"),
    PENDING_DEPOSIT("Pending Deposit"),
    COMPLETED("Completed"),
    CANCEL("Cancel");

    private final String status;

    BookingStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
