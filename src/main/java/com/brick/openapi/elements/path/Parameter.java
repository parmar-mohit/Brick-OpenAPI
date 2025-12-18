package com.brick.openapi.elements.path;

import com.brick.utilities.exception.KeyNotFound;

import jakarta.servlet.http.Cookie;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ArrayNode;

import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.schema.Schema;
import com.brick.openapi.elements.schema.SchemaFactory;
import com.brick.openapi.exception.*;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.BrickRequestData;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Parameter {
    private final String name;
    private final ParameterType type;
    private final Optional<String> description;
    private final Optional<Boolean> required;// this property is required if type = PATH
    private final Schema schema;

    private Parameter(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
        

        this.name = brickMap.getString(OpenAPIKeyConstants.NAME);
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        this.type = ParameterType.fromString( brickMap.getString(OpenAPIKeyConstants.PARAMETER_TYPE) );
        this.required = brickMap.getOptionalBoolean(OpenAPIKeyConstants.REQUIRED);


        // checks If ParameterType is path then required property should be true
        if( type == ParameterType.PATH ){
            if( ! required.isPresent() ){ //Checks if "required" property is present or not
                KeyNotFound keyNotFound = new KeyNotFound(OpenAPIKeyConstants.REQUIRED);
                Logger.logException(keyNotFound);
                throw keyNotFound;
            }else if( ! required.get() ){ // Checks if "required" property is false
                InvalidValue invlaidValue = new InvalidValue(OpenAPIKeyConstants.REQUIRED);
                Logger.logException(invlaidValue);
                throw invlaidValue;
            }
        }

        this.schema = SchemaFactory.getSchema( brickMap.getBrickMap(OpenAPIKeyConstants.SCHEMA), components );
        
    }

    /*
        Description : Check if Reference present in components then return reference else create Object
     */
    public static Parameter getParameter(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue {
        if(brickMap.contains(OpenAPIKeyConstants.REFERENCE)){
            
            String refValue = brickMap.getString(OpenAPIKeyConstants.REFERENCE);
            if( !refValue.startsWith(OpenAPIKeyConstants.REFERENCE_PARAMETER) ){
                InvalidValue invalidValue = new InvalidValue(refValue);
                Logger.logException(invalidValue);
                throw invalidValue;
            }

            String parameterName = refValue.substring(OpenAPIKeyConstants.REFERENCE_PARAMETER.length());
            return components.getParameter(parameterName);
        }else{
            
            return new Parameter(brickMap, components);
        }
    }
    
    /*
     * Description: Check if Parameter is Present and Correct
     */
    public boolean validateParameter(BrickRequestData brickRequestData) {
    	
    	if( ParameterType.PATH == type ) {
    		Map<String,String> pathVariables = brickRequestData.getPathVariables();
    		
    		if( !pathVariables.containsKey(this.name) ) {
    			if( this.required.isPresent() ) {
    				return !this.required.get();
    			}
    			
    			return true;
    		}
    		
    		JsonNode pathVariableData = new ObjectMapper().valueToTree(pathVariables.get(this.name));
    		
    		if( !schema.validateData(pathVariableData) ) {
    			Logger.info("Parameter : "+this.name+" Could not be Validated");
    			return false;
    		}
    		
    		
    	}else if( ParameterType.HEADER == type ) {
    		Map<String,String> headers = brickRequestData.getHeaders();
    		
    		if( !headers.containsKey(this.name) ) {
    			if( this.required.isPresent() ) {
    				if( this.required.get() ) {
    					Logger.info("Parameter : "+this.name+" Could not be Validated");
    					return false;
    				}
    			}
    			
    			return true;
    		}
    		    		
    		JsonNode headerData = new ObjectMapper().valueToTree(headers.get(this.name));
    		if( !schema.validateData(headerData) ) {
    			Logger.info("Parameter : "+this.name+" Could not be Validated");
    			return false;
    		}
    	}else if( ParameterType.QUERY == type ) {
    		Map<String,String[]> parameterMap = brickRequestData.getQueryParams();
    		
    		if( !parameterMap.containsKey(this.name) ) {
    			if( this.required.isPresent() ) {
    				if( this.required.get() ) {
    					Logger.info("Parameter : "+this.name+" Could not be Validated");
    					return false;
    				}
    			}
    			
    			return true;
    		}
    		
    		if( parameterMap.get(this.name).length == 1 ) {
        		JsonNode parameterData = new ObjectMapper().readTree(parameterMap.get(this.name)[0] );
        		if( !schema.validateData(parameterData) ) {
        			Logger.info("Parameter : "+this.name+" Could not be Validated");
        			return false;
        		}
    		}else {
	    		ArrayNode jsonArray = new ObjectMapper().createArrayNode();
	    		for( String val : parameterMap.get(this.name) ) {
	    			jsonArray.add(val);
	    		}
	    		
	    		
	    		if( !schema.validateData(jsonArray) ) {
	    			Logger.info("Parameter : "+this.name+" Could not be Validated");
	    			return false;
	    		}
    		}
    		
    	}else if( ParameterType.COOKIE == type ) {
    		List<Cookie> listOfCookies = brickRequestData.getCookies();
    		
    		if( null == listOfCookies ) {
    			Logger.info("Parameter : "+this.name+" Could not be Validated");
    			return false;
    		}
    		
    		for( Cookie c : listOfCookies ) {
    			if( this.name.equals(c.getName()) ) {    				
    				JsonNode cookieData = new ObjectMapper().valueToTree(c.getValue() );
    				
    				if( !schema.validateData(cookieData) ) {
    					Logger.info("Parameter : "+this.name+" Could not be Validated");
    					return false;
    				}
    			}
    		}
    		
    		if( this.required.isPresent() ) {
    			if( !this.required.get() ) {
    				Logger.info("Parameter : "+this.name+" Could not be Validated");
    				return false;
    			}
			}
			
			return true;
    	}
    	
    	
    	return true;
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
