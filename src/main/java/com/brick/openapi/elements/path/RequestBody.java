package com.brick.openapi.elements.path;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

public class RequestBody {
    private final Optional<String> description;
    private final Content content;
    private final boolean required;

    public RequestBody(BrickMap brickMap, Components components) throws KeyNotFound, InvalidValue, PropertyNotFound {
        Logger.trace("Trying to Create RequestBody Object");

        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        this.content = new Content(brickMap.getBrickMap(OpenAPIKeyConstants.CONTENT), components);

        Optional<Boolean> optionalRequired = brickMap.getOptionalBoolean(OpenAPIKeyConstants.REQUIRED);
        if( optionalRequired.isPresent() ) {
            this.required = optionalRequired.get();
        }else{
            this.required = false;
        }

        Logger.trace("RequestBody Object Created");
    }
}
