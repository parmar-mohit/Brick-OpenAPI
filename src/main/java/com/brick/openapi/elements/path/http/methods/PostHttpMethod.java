package com.brick.openapi.elements.path.http.methods;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;

import jakarta.servlet.http.HttpServletRequest;
import tools.jackson.databind.JsonNode;

import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.path.RequestBody;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.BrickRequestData;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class PostHttpMethod extends  HttpMethod {

    private final Optional<RequestBody> requestBody;

    public PostHttpMethod(BrickMap brickMap, Components components, Optional<Security> rootSecurity) throws KeyNotFound, InvalidValue {
        super(brickMap,components,  rootSecurity);

        
        Optional<BrickMap> optionalBrickMap = brickMap.getOptionalBrickMap(OpenAPIKeyConstants.REQUEST_BODY);
        if(optionalBrickMap.isPresent()){
            this.requestBody = Optional.of( new RequestBody(optionalBrickMap.get(), components));
        }else{
            this.requestBody = Optional.empty();
        }
        
    }

	@Override
	public boolean validateRequest(BrickRequestData brickRequestData) throws IOException {
		if( ! super.validateRequest(brickRequestData) ) {
			return false;
		}
				
		if( this.requestBody.isEmpty() ) { // If request body definition is not found, then request body should be empty
			if( requestBody.isEmpty() ) {
				return true;
			}
			
			return false;
		}
		
		return this.requestBody.get().validateRequest(brickRequestData.getRequestBody());
	}
    
}
