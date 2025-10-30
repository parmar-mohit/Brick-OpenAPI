package com.brick.openapi.elements.path;

import com.brick.utilities.exception.KeyNotFound;import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

public class Response {
    private final Optional<String> description;
    private final Optional<Content> content;

    private Response(BrickMap brickMap, Components components) throws InvalidValue, PropertyNotFound, KeyNotFound {
        Logger.trace("Trying to Create Response Object");
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        Optional<BrickMap> optionalBrickMap = brickMap.getOptionalBrickMap(OpenAPIKeyConstants.CONTENT);
        if( optionalBrickMap.isPresent() ){
            this.content = Optional.of( new Content(optionalBrickMap.get(), components) );
        }else{
            this.content = Optional.empty();
        }
        Logger.trace("Response Object Created");
    }

    /*
        Description : Check if Response present in component then return reference else create Object
     */
    public static Response getResponse(BrickMap brickMap, Components components) throws InvalidValue, KeyNotFound, PropertyNotFound {
        if( brickMap.contains(OpenAPIKeyConstants.REFERENCE) ){
            Logger.trace("Reference found for Response");
            String refValue = brickMap.getString(OpenAPIKeyConstants.REFERENCE);
            if( !refValue.startsWith(OpenAPIKeyConstants.REFERENCE_RESPONSE)){
                InvalidValue invalidValue = new InvalidValue(refValue);
                Logger.logException(invalidValue);
                throw invalidValue;
            }

            String responseName = refValue.substring(OpenAPIKeyConstants.REFERENCE_RESPONSE.length());
            Logger.trace("Returning Response from Reference");
            return components.getResponse(responseName);
        }else{
            Logger.trace("Creating New Object for Response");
            return new Response(brickMap,components);
        }
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<Content> getContent() {
        return content;
    }
}
