package com.brick.openapi.elements.security.scheme;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

public class BearerAuthScheme implements SecurityScheme {
    private final BearerFormat bearerFormat;

    public BearerAuthScheme(BrickMap brickMap) throws KeyNotFound, InvalidValue {
        
        this.bearerFormat = BearerFormat.fromString( brickMap.getString(OpenAPIKeyConstants.BEARER_FORMAT) );
        
    }
}
