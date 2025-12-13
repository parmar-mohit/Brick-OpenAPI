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

public class AllOfSchema extends Schema {
	private final List<Schema> allOfSchemas;
	
	public AllOfSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
		

        this.allOfSchemas = new ArrayList<>();
        List<Map<String,Object>> allOfList  = brickMap.getListOfMap(OpenAPIKeyConstants.ALL_OF);
        for (Map<String,Object> m : allOfList ) {
            this.allOfSchemas.add(SchemaFactory.getSchema(new BrickMap(m),components));
        }
        
        
	}

	@Override
	public boolean validateData(JsonNode data) {
		// Checking for All of Schema
    	for( Schema s :  allOfSchemas ) {
    		if( !s.validateData(data) ) {
    			return false;
    		}
    	}
    	
    	return true;
	}

}
