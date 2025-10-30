package com.brick.openapi.elements.server;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.List;
import java.util.Optional;

public class ServerVariable {
    private final String variableName;
    private final String defaultValue;
    private final Optional<String> description;
    private final Optional<List<String>> possibleValue; // enum in yaml

    public ServerVariable(String name, BrickMap brickMap) throws KeyNotFound {
        Logger.trace("Trying to Create ServerVariable Object");
        this.variableName = name;
        this.defaultValue = brickMap.getString(OpenAPIKeyConstants.VARIABLE_DEFAULT);
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        this.possibleValue = brickMap.getOptionalListOfString(OpenAPIKeyConstants.ENUM);
        Logger.trace("ServerVariable Object Created");
    }

    public String getVariableName() {
        return variableName;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<List<String>> getPossibleValue() {
        return possibleValue;
    }
}
