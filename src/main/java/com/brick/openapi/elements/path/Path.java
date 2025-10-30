package com.brick.openapi.elements.path;

import com.brick.utilities.exception.KeyNotFound;import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.path.http.methods.HttpMethod;
import com.brick.openapi.elements.path.http.methods.HttpMethodFactory;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.utilities.BrickMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Path {
    private final String endPoint;
    private final List<HttpMethod> httpMethod;
    private final List<String> pathParameters;

    public Path(String endPoint, BrickMap brickMap, Components components, Optional<Security> rootSecurity) throws PropertyNotFound, InvalidValue, KeyNotFound {
        Logger.trace("Trying to Create Path Object");
        this.endPoint = endPoint;

        //Getting Parameters from endPoint
        this.pathParameters = new ArrayList<>();
        int i = 0;
        while( i < endPoint.length() ){
            if( endPoint.charAt(i) == '{' ){
                StringBuilder builder = new StringBuilder();
                i += 1;
                while( i < endPoint.length() && endPoint.charAt(i) != '}' ){
                    builder.append(endPoint.charAt(i));
                    i += 1;
                }

                if( i >= endPoint.length()  ){ // Path Ends without closing brace
                    InvalidValue invalidValue = new InvalidValue(endPoint);
                    Logger.logException(invalidValue);
                    throw invalidValue;
                }

                pathParameters.add(builder.toString());
                Logger.trace("Path Parameter \""+builder.toString()+"\" found in endpoint \""+endPoint+"\"");
            }
            i += 1;
        }

        this.httpMethod = new ArrayList<>();
        for(Map.Entry<String,Object> entry: brickMap ){
            HttpMethod newMethod = HttpMethodFactory.getHttpMethod(entry.getKey(), (Map<String,Object>)entry.getValue(), components, rootSecurity);


            // Checks if all parameters are present in every http method
            for( String parameterName : pathParameters ){
                if( !newMethod.hasParameter(parameterName) ){
                    InvalidValue invalidValue = new InvalidValue(parameterName);
                    Logger.logException(invalidValue);
                    throw invalidValue;
                }
            }

            this.httpMethod.add( newMethod );
        }

        Logger.trace("Path Object Created");
    }

    public String getEndPoint() {
        return endPoint;
    }

    public List<HttpMethod> getHttpMethod() {
        return httpMethod;
    }
}
