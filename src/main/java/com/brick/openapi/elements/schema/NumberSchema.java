package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.List;
import java.util.Optional;

public class NumberSchema extends Schema {
    private Optional<Double> minimum;
    private Optional<Double> maximum;
    private final Optional<List<Double>> possibleValues;
    private final boolean nullable;

    public NumberSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        super(brickMap,components);

        Logger.trace("Trying to Create Number Schema Object");
        this.minimum = brickMap.getOptionalDouble(OpenAPIKeyConstants.MINIMUM);
        this.maximum = brickMap.getOptionalDouble(OpenAPIKeyConstants.MAXIMUM);
        this.possibleValues = brickMap.getOptionalListOfDouble(OpenAPIKeyConstants.ENUM);
        if( brickMap.contains(OpenAPIKeyConstants.NULLABLE) ){
            this.nullable = brickMap.getBoolean(OpenAPIKeyConstants.NULLABLE);
        }else{
            this.nullable = false;
        }
        Logger.trace("Number Schema Object Created");
    }
}
