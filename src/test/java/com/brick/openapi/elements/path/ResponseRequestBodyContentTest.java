package com.brick.openapi.elements.path;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.schema.StringSchema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ResponseRequestBodyContentTest {

    @Test
    public void testContent() {
        Content content = new Content();
        content.addSchema("application/json", new StringSchema());
        assertNotNull(content.getSchema());
        assertEquals(1, content.getSchema().size());
    }

    @Test
    public void testRequestBody() {
        RequestBody requestBody = new RequestBody();
        requestBody.setDescription("description");
        requestBody.setRequired(true);
        Content content = new Content();
        content.addSchema("application/json", new StringSchema());
        requestBody.setContent(content);
        assertEquals("description", requestBody.getDescription());
        assertEquals(true, requestBody.getRequired());
        assertEquals(content, requestBody.getContent());
    }

    @Test
    public void testResponse() {
        Response response = new Response();
        response.setDescription("description");
        Content content = new Content();
        content.addSchema("application/json", new StringSchema());
        response.setContent(content);
        assertEquals("description", response.getDescription());
        assertEquals(content, response.getContent());
    }
}
