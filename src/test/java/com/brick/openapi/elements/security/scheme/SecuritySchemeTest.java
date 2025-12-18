package com.brick.openapi.elements.security.scheme;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecuritySchemeTest {

    @Test
    public void testBasicAuthScheme() {
        BasicAuthScheme basicAuthScheme = new BasicAuthScheme();
        basicAuthScheme.setScheme("basic");
        assertEquals("basic", basicAuthScheme.getScheme());
        assertEquals("http", basicAuthScheme.getType());
    }

    @Test
    public void testBearerAuthScheme() {
        BearerAuthScheme bearerAuthScheme = new BearerAuthScheme();
        bearerAuthScheme.setScheme("bearer");
        bearerAuthScheme.setBearerFormat(BearerFormat.JWT);
        assertEquals("bearer", bearerAuthScheme.getScheme());
        assertEquals(BearerFormat.JWT, bearerAuthScheme.getBearerFormat());
        assertEquals("http", bearerAuthScheme.getType());
    }
}
