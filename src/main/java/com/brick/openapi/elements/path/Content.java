package com.brick.openapi.elements.path;

import com.brick.utilities.exception.KeyNotFound;import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.schema.Schema;
import com.brick.openapi.elements.schema.SchemaFactory;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.*;

public class Content {
    private final Map<String,Schema> content;

    /*
        Description: List of Valid Content Type Supported by Current Version of OpenApi Parse
     */
    private final List<String> validContentType = new ArrayList<>(
            Arrays.asList(
                    "application/json"
            )
    );

    public Content(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        Logger.trace("Trying to Create Content Object");
        this.content = new HashMap<>();
        for(Map.Entry<String,Object> entry : brickMap ){
            if( !validContentType.contains(entry.getKey()) ){
                InvalidValue invalidValue = new InvalidValue(entry.getKey());
                Logger.logException(invalidValue);
                throw invalidValue;
            }
            this.content.put(entry.getKey(), SchemaFactory.getSchema(new BrickMap( entry.getValue() ) .getBrickMap(OpenAPIKeyConstants.SCHEMA), components ) );
        }

        Logger.trace("Content Object Created");
    }

    public Map<String, Schema> getContent() {
        return content;
    }
}
