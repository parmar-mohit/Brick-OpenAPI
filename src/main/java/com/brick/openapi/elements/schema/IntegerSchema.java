package com.brick.openapi.elements.schema;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;

import tools.jackson.databind.JsonNode;

import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.List;
import java.util.Optional;

public class IntegerSchema extends Schema {
    private final Optional<Integer> minimum;
    private final Optional<Integer> maximum;
    private final Optional<List<Integer>> possibleValues;
    private final boolean nullable;

    public IntegerSchema(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
        
        this.minimum = brickMap.getOptionalInteger(OpenAPIKeyConstants.MINIMUM);
        this.maximum = brickMap.getOptionalInteger(OpenAPIKeyConstants.MAXIMUM);
        this.possibleValues = brickMap.getOptionalListOfInteger(OpenAPIKeyConstants.ENUM);
        if( brickMap.contains(OpenAPIKeyConstants.NULLABLE) ){
            this.nullable = brickMap.getBoolean(OpenAPIKeyConstants.NULLABLE);
        }else{
            this.nullable = false;
        }
        
    }

	@Override
	public boolean validateData(JsonNode data) {
		
		//Checking Nullable Condition
		if( data == null || data.isNull() ) {
			return this.nullable;
		}
		
		int value;
		

		//Checking if Data is Integer or and Object with Integer
		if( !data.isInt() ) { 
			return false;
		}
			
		//Checking Range of Values
		value = data.asInt();
		
		if( this.minimum.isPresent() && value < this.minimum.get() ) {
			return false;
		}
		if( this.maximum.isPresent() && value > this.maximum.get() ) {
			return false;
		}
		if( this.possibleValues.isPresent() && !this.possibleValues.get().contains(value) ) {
			return false;
		}
		
		return true;
	}
    
    
}
