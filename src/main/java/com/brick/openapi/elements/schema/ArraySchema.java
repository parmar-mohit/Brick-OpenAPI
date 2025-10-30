package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

public class ArraySchema extends Schema{
    private final Schema items;
    private final boolean nullable;
    private final Optional<Integer> minItems;
    private final Optional<Integer> maxItems;
    private final boolean uniqueItems;

    public ArraySchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        super(brickMap,components);

        Logger.trace("Trying to Create Array Schema Object");
        this.items = SchemaFactory.getSchema( brickMap.getBrickMap(OpenAPIKeyConstants.ARRAY_SCHEMA), components );
        this.minItems = brickMap.getOptionalInteger(OpenAPIKeyConstants.MINIMUM_ITEMS);
        this.maxItems = brickMap.getOptionalInteger(OpenAPIKeyConstants.MAXIMUM_ITEMS);

        if( brickMap.contains(OpenAPIKeyConstants.NULLABLE) ){
            this.nullable = brickMap.getBoolean(OpenAPIKeyConstants.NULLABLE);
        }else{
            this.nullable = false;
        }

        if( brickMap.contains(OpenAPIKeyConstants.UNIQUE_ITEMS) ){
            this.uniqueItems = brickMap.getBoolean(OpenAPIKeyConstants.UNIQUE_ITEMS);
        }else{
            this.uniqueItems = false;
        }

        Logger.trace("ArraySchema Object Created");
    }
}
