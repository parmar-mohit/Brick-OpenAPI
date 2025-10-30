package com.brick.openapi.elements.path.http.methods;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.path.Parameter;
import com.brick.openapi.elements.path.Response;
import com.brick.openapi.elements.path.http.HttpStatusCode;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.*;

public abstract class HttpMethod {
    private final Optional<String> summary;
    private final Optional<String> description;
    private final List<Parameter> parameters;
    private final Map<HttpStatusCode,Response> responses;
    private final Optional<String> operationId;
    private final Optional<Boolean> deprecated;
    private final Optional<Security> security;

    public HttpMethod(BrickMap brickMap, Components components, Optional<Security> rootSecurity) throws KeyNotFound, InvalidValue, PropertyNotFound {
        Logger.trace("Trying to Create HttpMethod Object");
        this.summary = brickMap.getOptionalString(OpenAPIKeyConstants.SUMMARY);
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);

        Optional<List<Map<String,Object>>> optionalParameterMap = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.PARAMETERS);
        this.parameters = new ArrayList<>();
        if( optionalParameterMap.isPresent() ){
            List<Map<String,Object>> parameterMapList = optionalParameterMap.get();
            for (Map<String, Object> stringObjectMap : parameterMapList) {
                this.parameters.add(Parameter.getParameter(new BrickMap(stringObjectMap), components));
            }
        }

        this.responses = new HashMap<>();
        BrickMap responseMap = brickMap.getBrickMap(OpenAPIKeyConstants.RESPONSES);
        for( Map.Entry<String,Object> entry: responseMap ){
            HttpStatusCode statusCode = HttpStatusCode.fromString(entry.getKey());
            Response response = Response.getResponse(new BrickMap( entry.getValue() ), components);
            this.responses.put(statusCode,response);
        }

        this.operationId = brickMap.getOptionalString(OpenAPIKeyConstants.OPERATION_ID);
        this.deprecated = brickMap.getOptionalBoolean(OpenAPIKeyConstants.DEPRECATED);

        if( brickMap.contains(OpenAPIKeyConstants.SECURITY) ){
            Logger.trace("MethodLevel Security Details Present");
            this.security = Optional.of( new Security( brickMap.getListOfMap(OpenAPIKeyConstants.SECURITY),components) );
        }else if( rootSecurity.isPresent() ){
            Logger.trace("Getting Security Detail from Root Security Object");
            this.security = Optional.of(rootSecurity.get());
        }else{
            Logger.trace("No Security Object Found");
            this.security = Optional.empty();
        }

        Logger.trace("HttpMethodObject Created");
    }

    /*
        Description: Checks is given method has the parameter defined or not
     */
    public boolean hasParameter(String parameterName){
        for( Parameter parameter : this.parameters ){
            if( parameter.getName().equals(parameterName) ){
                return true;
            }
        }

        return false;
    }


    public Optional<String> getSummary() {
        return summary;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public Map<HttpStatusCode, Response> getResponses() {
        return responses;
    }
}
