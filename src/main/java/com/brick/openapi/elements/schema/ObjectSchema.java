package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.*;

public class ObjectSchema extends Schema {
    private final Map<String,Schema> properties;
    private final List<String> requiredProperties;
    private final boolean nullable;

    public ObjectSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        super(brickMap,components);

        Logger.trace("Trying to Create Object Schema Object");
        this.properties = new HashMap<>();
        BrickMap propertiesMap = brickMap.getBrickMap(OpenAPIKeyConstants.PROPERTIES);
        for( Map.Entry<String,Object> entry : propertiesMap ){
            this.properties.put(entry.getKey(), SchemaFactory.getSchema( new BrickMap( entry.getValue() ), components ) );
        }

        Optional<List<String>> optionalListOfString = brickMap.getOptionalListOfString(OpenAPIKeyConstants.REQUIRED);
        this.requiredProperties = optionalListOfString.orElseGet(ArrayList::new);

        if( brickMap.contains(OpenAPIKeyConstants.NULLABLE) ){
            this.nullable = brickMap.getBoolean(OpenAPIKeyConstants.NULLABLE);
        }else{
            this.nullable = false;
        }
        Logger.trace("ObjectSchema Object Created");
    }
}
