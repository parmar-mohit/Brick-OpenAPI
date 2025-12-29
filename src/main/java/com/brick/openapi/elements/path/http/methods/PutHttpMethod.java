package com.brick.openapi.elements.path.http.methods;

import com.brick.utilities.exception.KeyNotFound;


import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.path.RequestBody;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.BrickRequestData;

import java.io.IOException;
import java.util.Optional;

public class PutHttpMethod extends HttpMethod {
    private final Optional<RequestBody> requestBody;

    public PutHttpMethod(BrickMap brickMap, Components components,Optional<Security> rootSecurity) throws KeyNotFound, InvalidValue {
        super(brickMap,components, rootSecurity);

        
        Optional<BrickMap> optionalBrickMap = brickMap.getOptionalBrickMap(OpenAPIKeyConstants.REQUEST_BODY);
        if(optionalBrickMap.isPresent()){
            this.requestBody = Optional.of( new RequestBody(optionalBrickMap.get(),components));
        }else{
            this.requestBody = Optional.empty();
        }
        
    }

	@Override
	public boolean validateRequest(BrickRequestData brickRequestData) throws IOException {
		if( !super.validateRequest(brickRequestData) ) {
			return false;
		}
		
		
		if( this.requestBody.isEmpty() ) { // If request body definition is not found, then request body should be empty
			return brickRequestData.getRequestBody().isEmpty();
		}
		
		return this.requestBody.get().validateRequest(brickRequestData.getRequestBody());
		
		
	}
}
