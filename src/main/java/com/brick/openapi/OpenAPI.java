package com.brick.openapi;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.info.Info;
import com.brick.openapi.elements.path.Path;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.elements.server.Server;
import com.brick.openapi.exception.CyclicReferenceFound;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OpenAPI {

    private final String openApiVersion; // OpenAPI Specification Version
    private final Info info; // Info About Api
    private final List<Path> paths;
    private final List<Server> servers;
    private final Optional<Components> components;
    private final Optional<Security> security;

    public OpenAPI( BrickMap brickMap ) throws KeyNotFound, InvalidValue, CyclicReferenceFound {
        

        this.openApiVersion = brickMap.getString(OpenAPIKeyConstants.OPEN_API_VERSION);
        this.info = new Info( brickMap.getBrickMap(OpenAPIKeyConstants.INFO) );

        //Server
        this.servers = new ArrayList<>();
        Optional<List<Map<String,Object>>> optionalServers = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.SERVERS);
        if( optionalServers.isPresent() ){
            for( Map<String,Object> map : optionalServers.get() ){
                this.servers.add( new Server( new BrickMap(map) ) );
            }
        }

        //Components
        Optional<BrickMap> optionalComponentMap = brickMap.getOptionalBrickMap(OpenAPIKeyConstants.COMPONENTS);
        if( optionalComponentMap.isPresent() ){
            this.components = Optional.of( new Components( optionalComponentMap.get() ) );
        }else{
            this.components = Optional.empty();
        }

        // Security
        if( brickMap.contains(OpenAPIKeyConstants.SECURITY) ){
            this.security = Optional.of( new Security( brickMap.getListOfMap(OpenAPIKeyConstants.SECURITY),this.components.orElse(null)) );
        }else{
            this.security = Optional.empty();
        }

        //Paths
        this.paths = new ArrayList<>();
        BrickMap pathMap = brickMap.getBrickMap(OpenAPIKeyConstants.PATHS);
        for( Map.Entry<String, Object> entry : pathMap ){
            paths.add( new Path(entry.getKey(), new BrickMap( entry.getValue()), this.components.orElse(null), this.security) );
        }

        
    }

    public String getOpenApiVersion() {
        return openApiVersion;
    }

    public Info getInfo() {
        return info;
    }

    public List<Server> getServers() {
        return servers;
    }

    public List<Path> getPaths() {
        return paths;
    }
}


