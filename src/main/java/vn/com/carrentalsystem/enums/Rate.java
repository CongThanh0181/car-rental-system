package vn.com.carrentalsystem.enums;

import java.io.Serializable;

public enum Rate implements Serializable {

    ZERO(0),
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final Integer value;

    Rate(Integer value) {
        this.value = value;
    }

    public Integer getStatus() {
        return value;
    }
}
