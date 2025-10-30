package com.brick.openapi.exception;

public class InvalidValue extends Exception {
    private String invalidValue;

    public InvalidValue(String invalidValue) {
        super("Invalid Value : "+invalidValue);
        this.invalidValue = invalidValue;
    }

    public String getInvalidValue() {
        return invalidValue;
    }
}
