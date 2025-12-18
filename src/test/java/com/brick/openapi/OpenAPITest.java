package com.brick.openapi;

import org.junit.jupiter.api.Test;

import com.brick.openapi.reader.OpenAPIFileYamlReader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

public class OpenAPITest {

    @Test
    public void testGetters() throws FileNotFoundException, Exception {
        OpenAPIFileYamlReader reader = new OpenAPIFileYamlReader();
        OpenAPI openAPI = reader.read("src/test/resources/dummy_yaml/dummy_valid_withComponents.yaml");

        assertEquals("3.0.0", openAPI.getOpenapi());
    }
}
