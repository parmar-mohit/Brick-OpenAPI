package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.openapi.exception.InvalidValue;

public enum SchemaType {
    ARRAY("array"),
    NUMBER("number"),
    INTEGER("integer"),
    STRING("string"),
    OBJECT("object");

    private final String type;

    SchemaType(String type) {
        this.type = type;
    }

    public static SchemaType fromString(String value) throws InvalidValue {
        
        for (SchemaType pt : values()) {
            if (pt.type.equalsIgnoreCase(value)) {
                return pt;
            }
        }

        InvalidValue invalidValue = new InvalidValue(value);
        Logger.logException(invalidValue);
        throw invalidValue;
    }
}