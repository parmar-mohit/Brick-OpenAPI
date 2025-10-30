package com.brick.openapi.elements.path;

import com.brick.utilities.exception.KeyNotFound;import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.schema.Schema;
import com.brick.openapi.elements.schema.SchemaFactory;
import com.brick.openapi.exception.*;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

public class Parameter {
    private final String name;
    private final ParameterType type;
    private final Optional<String> description;
    private final Optional<Boolean> required;// this property is required if type = PATH
    private final Schema schema;

    private Parameter(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        Logger.trace("Trying to Create Parameter Object");

        this.name = brickMap.getString(OpenAPIKeyConstants.NAME);
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        this.type = ParameterType.fromString( brickMap.getString(OpenAPIKeyConstants.PARAMETER_TYPE) );
        this.required = brickMap.getOptionalBoolean(OpenAPIKeyConstants.REQUIRED);


        // checks If ParameterType is path then required property should be true
        if( type == ParameterType.PATH ){
            if( ! required.isPresent() ){ //Checks if "required" property is present or not
                PropertyNotFound propertyNotFound = new PropertyNotFound(OpenAPIKeyConstants.REQUIRED);
                Logger.logException(propertyNotFound);
                throw propertyNotFound;
            }else if( ! required.get() ){ // Checks if "required" property is false
                InvalidValue invlaidValue = new InvalidValue(OpenAPIKeyConstants.REQUIRED);
                Logger.logException(invlaidValue);
                throw invlaidValue;
            }
        }

        this.schema = SchemaFactory.getSchema( brickMap.getBrickMap(OpenAPIKeyConstants.SCHEMA), components );
        Logger.trace("Parameter Object Created");
    }

    /*
        Description : Check if Reference present in components then return reference else create Object
     */
    public static Parameter getParameter(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        if(brickMap.contains(OpenAPIKeyConstants.REFERENCE)){
            Logger.trace("Getting Parameter Object from Reference");
            String refValue = brickMap.getString(OpenAPIKeyConstants.REFERENCE);
            if( !refValue.startsWith(OpenAPIKeyConstants.REFERENCE_PARAMETER) ){
                InvalidValue invalidValue = new InvalidValue(refValue);
                Logger.logException(invalidValue);
                throw invalidValue;
            }

            String parameterName = refValue.substring(OpenAPIKeyConstants.REFERENCE_PARAMETER.length());
            return components.getParameter(parameterName);
        }else{
            Logger.trace("Trying to Create new Parameter Object");
            return new Parameter(brickMap, components);
        }
    }

    public String getName() {
        return name;
    }

    public ParameterType getType() {
        return type;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<Boolean> getRequired() {
        return required;
    }

    public Schema getSchema() {
        return schema;
    }
}
