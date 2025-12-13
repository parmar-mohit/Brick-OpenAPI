package com.brick.openapi.elements.path.http.methods;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;

import jakarta.servlet.http.HttpServletRequest;
import tools.jackson.databind.JsonNode;

import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;

import java.util.Map;
import java.util.Optional;

public class DeleteHttpMethod extends HttpMethod {
    public DeleteHttpMethod(BrickMap brickMap, Components components, Optional<Security> rootSecurity) throws KeyNotFound, InvalidValue {
        super(brickMap, components, rootSecurity);

        
    }   
}
