package com.brick.openapi.elements.path.http;

import com.brick.logger.Logger;
import com.brick.openapi.exception.InvalidValue;

public enum HttpStatusCode {
    SUCCESS("200"),
    RESOURCE_CREATED("201"),
    NO_CONTENT("204");

    private final String statusCode;


    HttpStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public static HttpStatusCode fromString(String value) throws InvalidValue {
        
        for (HttpStatusCode pt : values()) {
            if (pt.statusCode.equalsIgnoreCase(value)) {
                return pt;
            }
        }

        InvalidValue invalidValue = new InvalidValue(value);
        Logger.logException(invalidValue);
        throw invalidValue;
    }
}
