package vn.com.carrentalsystem.enums;

import java.io.Serializable;


public enum PaymentMethod implements Serializable {

    MY_WALLET("My Wallet"),
    CASH("Cash"),
    BANK_TRANSFER("Bank Transfer");

    private final String status;

    PaymentMethod(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
