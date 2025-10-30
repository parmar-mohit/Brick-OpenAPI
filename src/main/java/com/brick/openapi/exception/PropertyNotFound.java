package com.brick.openapi.exception;

public class PropertyNotFound extends Exception {
    private String propertyNotFound;

    public PropertyNotFound(String propertyNotFound){
        super(
                "Cannot Find : " + propertyNotFound
        );
        this.propertyNotFound = propertyNotFound;
    }

    public String getPropertyNotFound() {
        return propertyNotFound;
    }
}
