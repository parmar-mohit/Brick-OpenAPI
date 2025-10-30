package com.brick.openapi.elements.schema;

import com.brick.utilities.exception.KeyNotFound;import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

public class EmptySchema extends Schema {

    public EmptySchema(BrickMap brickMap, Components components) throws InvalidValue, KeyNotFound, PropertyNotFound {
        super(brickMap,components);

        Logger.trace("Checking Validity of Empty Schema Object");
        if( ! checkIfValidEmptySchema(brickMap) ){
            PropertyNotFound propertyNotFound = new PropertyNotFound("Cannot find any valid key for Schema");
            Logger.logException(propertyNotFound);
            throw propertyNotFound;
        }
        Logger.trace("Valid Empty Schema Object");
    }

    /*
        Description: Checks if EmptySchema contains atLeast one from ( allOf, anyOf, oneOf )
     */
    private boolean checkIfValidEmptySchema(BrickMap brickMap){
        if( brickMap.isEmpty() ){
            return false;
        }

        if( brickMap.contains(OpenAPIKeyConstants.ANY_OF) ){
            return true;
        }

        if( brickMap.contains(OpenAPIKeyConstants.ALL_OF) ){
            return true;
        }

        if( brickMap.contains(OpenAPIKeyConstants.ONE_OF) ){
            return true;
        }

        return false;
    }
}
