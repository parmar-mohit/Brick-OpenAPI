package com.brick.openapi.elements.server;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Map;

public class ServerTest {

    @Test
    public void testServer() {
        Server server = new Server("https://example.com", "description");
        assertEquals("https://example.com", server.getUrl());
        assertEquals("description", server.getDescription());

        ServerVariable serverVariable = new ServerVariable();
        serverVariable.setDefault("default");
        serverVariable.setDescription("description");
        serverVariable.setEnum(new String[]{"enum1", "enum2"});

        server.addVariable("variable", serverVariable);
        Map<String, ServerVariable> variables = server.getVariables();
        assertNotNull(variables);
        assertEquals(1, variables.size());
        assertEquals(serverVariable, variables.get("variable"));
    }

    @Test
    public void testServerVariable() {
        ServerVariable serverVariable = new ServerVariable();
        serverVariable.setDefault("default");
        serverVariable.setDescription("description");
        String[] enums = new String[]{"enum1", "enum2"};
        serverVariable.setEnum(enums);
        assertEquals("default", serverVariable.getDefault());
        assertEquals("description", serverVariable.getDescription());
        assertEquals(enums, serverVariable.getEnum());
    }
}
