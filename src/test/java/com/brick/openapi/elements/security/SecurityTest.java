package com.brick.openapi.elements.security;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.security.scheme.BasicAuthScheme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SecurityTest {

    @Test
    public void testSecurity() {
        Security security = new Security();
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        security.addSecuritySchemes("basicAuth", basicAuthScheme);
        assertNotNull(security.getSecuritySchemes());
        assertEquals(1, security.getSecuritySchemes().size());
        assertEquals(basicAuthScheme, security.getSecuritySchemes().get("basicAuth"));
    }
}
