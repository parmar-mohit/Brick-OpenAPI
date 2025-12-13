package com.brick.openapi.elements.server;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Server {
    private final String url;
    private final Optional<String> description;
    private final List<ServerVariable> variables;

    public Server(BrickMap brickMap) throws KeyNotFound {
        
        this.url = brickMap.getString(OpenAPIKeyConstants.URL);
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        this.variables = new ArrayList<>();

        //Getting Variables
        Optional<BrickMap>  optionalVariableBrickMap= brickMap.getOptionalBrickMap(OpenAPIKeyConstants.SERVER_VARIABLES);
        if( optionalVariableBrickMap.isPresent() ){
            for( Map.Entry<String,Object> entry: optionalVariableBrickMap.get() ){
                this.variables.add( new ServerVariable(entry.getKey(), new BrickMap( entry.getValue() ) ) );
            }
        }
        
    }

    public String getUrl() {
        return url;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public List<ServerVariable> getVariables() {
        return variables;
    }
}
