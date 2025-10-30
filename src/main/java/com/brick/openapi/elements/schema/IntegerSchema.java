package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.List;
import java.util.Optional;

public class IntegerSchema extends Schema {
    private final Optional<Integer> minimum;
    private final Optional<Integer> maximum;
    private final Optional<List<Integer>> possibleValues;
    private final boolean nullable;

    public IntegerSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        super(brickMap,components);

        Logger.trace("Trying to Create IntegerSchema Object");
        this.minimum = brickMap.getOptionalInteger(OpenAPIKeyConstants.MINIMUM);
        this.maximum = brickMap.getOptionalInteger(OpenAPIKeyConstants.MAXIMUM);
        this.possibleValues = brickMap.getOptionalListOfInteger(OpenAPIKeyConstants.ENUM);
        if( brickMap.contains(OpenAPIKeyConstants.NULLABLE) ){
            this.nullable = brickMap.getBoolean(OpenAPIKeyConstants.NULLABLE);
        }else{
            this.nullable = false;
        }
        Logger.trace("IntegerSchema Object Created");
    }
}
