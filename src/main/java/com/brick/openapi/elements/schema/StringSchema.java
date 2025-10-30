package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.List;
import java.util.Optional;

enum StringSchemaFormat {
    DATE("date"),
    DATE_TIME("date-time"),
    PASSWORD("password"),
    UUID("uuid"),
    EMAIL("email");

    private final String type;

    StringSchemaFormat(String type) {
        this.type = type;
    }

    public static StringSchemaFormat fromString(String value) throws InvalidValue {
        Logger.trace("Trying to create StringSchemaFormat Enum");
        for (StringSchemaFormat pt : values()) {
            if (pt.type.equalsIgnoreCase(value)) {
                return pt;
            }
        }

        InvalidValue invalidValue = new InvalidValue(value);
        Logger.logException(invalidValue);
        throw invalidValue;
    }
}

public class StringSchema extends Schema{
    private final Optional<StringSchemaFormat> format;
    private final Optional<String> pattern;
    private final Optional<Integer> minLength;
    private final Optional<Integer> maxLength;
    private final Optional<List<String>> possibleValues;// enum in openapi
    private final boolean nullable;

    public StringSchema(BrickMap brickMap, Components components) throws InvalidValue, KeyNotFound, PropertyNotFound {
        super(brickMap,components);

        Logger.trace("Trying to Create StringSchemaObject");
        Optional<String> stringSchemaType = brickMap.getOptionalString(OpenAPIKeyConstants.FORMAT);
        if( stringSchemaType.isPresent() ){
            this.format = Optional.of(StringSchemaFormat.fromString(stringSchemaType.get()));
        }else{
            this.format = Optional.empty();
        }
        this.pattern = brickMap.getOptionalString(OpenAPIKeyConstants.PATTERN);
        this.minLength = brickMap.getOptionalInteger(OpenAPIKeyConstants.MINIMUM_LENGTH);
        this.maxLength = brickMap.getOptionalInteger(OpenAPIKeyConstants.MAXIMUM_LENGTH);
        this.possibleValues = brickMap.getOptionalListOfString(OpenAPIKeyConstants.ENUM);

        if( brickMap.contains(OpenAPIKeyConstants.NULLABLE) ){
            this.nullable = brickMap.getBoolean(OpenAPIKeyConstants.NULLABLE);
        }else{
            this.nullable = false;
        }

        Logger.trace("StringSchema Object Created");
    }

}
