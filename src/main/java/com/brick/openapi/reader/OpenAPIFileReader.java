package com.brick.openapi.reader;

import com.brick.logger.Logger;
import com.brick.openapi.exception.InvalidOpenAPISpecification;
import com.brick.openapi.OpenAPI;
import com.brick.utilities.exception.InvalidData;

import java.io.FileNotFoundException;

public abstract class OpenAPIFileReader {

    protected String fileName;

    public OpenAPIFileReader(String fileName) {
        this.fileName = fileName;
    }

    public abstract OpenAPI getOpenAPI() throws FileNotFoundException, InvalidData, InvalidOpenAPISpecification;
}
