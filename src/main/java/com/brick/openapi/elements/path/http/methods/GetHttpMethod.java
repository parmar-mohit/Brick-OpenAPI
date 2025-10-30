package com.brick.openapi.elements.path.http.methods;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.utilities.BrickMap;

import java.util.Optional;

public class GetHttpMethod extends HttpMethod {

    public GetHttpMethod(BrickMap brickMap, Components components, Optional<Security> rootSecurity) throws PropertyNotFound, InvalidValue, KeyNotFound {
        super(brickMap,components,rootSecurity);

        Logger.trace("GetHttpMethod Object Created");
    }

}
