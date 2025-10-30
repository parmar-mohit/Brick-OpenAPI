package com.brick.openapi.elements.info;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

/*
    Description: Contains License Information about OpenApi Specification
 */
public class License {
    private final String name;
    private final Optional<String> url;

    private License(BrickMap brickMap) throws KeyNotFound {
        this.name = brickMap.getString(OpenAPIKeyConstants.NAME);
        this.url = brickMap.getOptionalString(OpenAPIKeyConstants.URL);
    }

    public static Optional<License> getLicense(Optional<BrickMap> optionalBrickMap) throws KeyNotFound{
        Logger.trace("Creating License Object");
        if(optionalBrickMap.isPresent() ){
            License license = new License(optionalBrickMap.get());
            Logger.trace("License Object Created");
            return Optional.of( license );
        }

        Logger.trace("License Details Not Present Returning Empty Optional");
        return Optional.empty();
    }

    public String getName() {
        return name;
    }

    public Optional<String> getUrl() {
        return url;
    }
}
