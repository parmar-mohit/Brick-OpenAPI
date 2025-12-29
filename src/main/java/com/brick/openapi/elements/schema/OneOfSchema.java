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

public class OneOfSchema extends Schema {
	private final List<Schema> oneOfSchemas;
	
	public OneOfSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
		
		this.oneOfSchemas = new ArrayList<>();
        List<Map<String,Object>> oneOflist  = brickMap.getListOfMap(OpenAPIKeyConstants.ONE_OF);
        
        for (Map<String,Object> m : oneOflist ) {
            this.oneOfSchemas.add(SchemaFactory.getSchema(new BrickMap(m),components));
        }
        
        
	}

	@Override
	public boolean validateData(JsonNode data) {
		//Checking for oneOfSchemas
    	int matchCount = 0;
    	for( Schema s : oneOfSchemas ) {
    		if( s.validateData(data) ) {
    			matchCount += 1;
    		}
    		
    		if( matchCount > 1 ) {
        		return false;
        	}
    	}
    	
    	return matchCount == 1;	
	}
}
