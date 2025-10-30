package com.brick.openapi.elements.path.http.methods;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.path.RequestBody;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

public class PutHttpMethod extends HttpMethod {
    private final Optional<RequestBody> requestBody;

    public PutHttpMethod(BrickMap brickMap, Components components,Optional<Security> rootSecurity) throws KeyNotFound, InvalidValue, PropertyNotFound {
        super(brickMap,components, rootSecurity);

        Logger.trace("Trying to Create PutHttpMethod Object");
        Optional<BrickMap> optionalBrickMap = brickMap.getOptionalBrickMap(OpenAPIKeyConstants.REQUEST_BODY);
        if(optionalBrickMap.isPresent()){
            this.requestBody = Optional.of( new RequestBody(optionalBrickMap.get(),components));
        }else{
            this.requestBody = Optional.empty();
        }
        Logger.trace("PutHttpMethod Object Created");
    }
}
