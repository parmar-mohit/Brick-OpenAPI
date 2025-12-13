package com.brick.openapi.elements.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.KeyNotFound;

import tools.jackson.databind.JsonNode;

public class AnyOfSchema extends Schema {
	
	private final List<Schema> anyOfSchemas;
	
	
	public AnyOfSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
		
	
	    this.anyOfSchemas = new ArrayList<>();
	    List<Map<String,Object>> anyOfList  = brickMap.getListOfMap(OpenAPIKeyConstants.ANY_OF);
        for (Map<String,Object> m : anyOfList ) {
            this.anyOfSchemas.add(SchemaFactory.getSchema(new BrickMap(m),components));
        }

	
	    
	}


	@Override
	public boolean validateData(JsonNode data) {
		// Checking for AnyOfSchema
    	for( Schema s: anyOfSchemas ) {
    		if( s.validateData(data) ) {
    			return true;
    		}
    	}
    	
    	return false;
	}
}
