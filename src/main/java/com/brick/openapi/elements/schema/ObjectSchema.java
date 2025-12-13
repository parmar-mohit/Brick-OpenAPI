package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;

import tools.jackson.databind.JsonNode;

import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.*;

public class ObjectSchema extends Schema {
    private final Map<String,Schema> properties;
    private final List<String> requiredProperties;
    private final boolean nullable;

    public ObjectSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
        
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
        
    }

	@Override
	public boolean validateData(JsonNode data) {
		for( Map.Entry<String, Schema> entry: this.properties.entrySet() ) {
			//Checking if that property exist in data
			if( !data.has(entry.getKey())  ) {
				return false;
			}
			
			// Validating for That Schema
			if( !entry.getValue().validateData(data.get(entry.getKey())) ) {
				return false;
			}
		}
		
		return true;
	}
    
    
}
