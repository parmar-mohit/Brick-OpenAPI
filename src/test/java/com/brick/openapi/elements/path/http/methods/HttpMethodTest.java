package com.brick.openapi.elements.path.http.methods;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.brick.openapi.elements.path.Response;

public class HttpMethodTest {

    @Test
    public void testGetHttpMethod() {
        GetHttpMethod method = new GetHttpMethod();
        method.addResponse("200", new Response());
        assertEquals(1, method.getResponses().size());
        assertNotNull(method.getResponses().get("200"));
    }

    @Test
    public void testPostHttpMethod() {
        PostHttpMethod method = new PostHttpMethod();
        method.addResponse("201", new Response());
        assertEquals(1, method.getResponses().size());
        assertNotNull(method.getResponses().get("201"));
    }

    @Test
    public void testPutHttpMethod() {
        PutHttpMethod method = new PutHttpMethod();
        method.addResponse("200", new Response());
        assertEquals(1, method.getResponses().size());
        assertNotNull(method.getResponses().get("200"));
    }

    @Test
    public void testDeleteHttpMethod() {
        DeleteHttpMethod method = new DeleteHttpMethod();
        method.addResponse("204", new Response());
        assertEquals(1, method.getResponses().size());
        assertNotNull(method.getResponses().get("204"));
    }

    @Test
    public void testHttpMethodFactory() {
        HttpMethod get = HttpMethodFactory.create("get");
        assertTrue(get instanceof GetHttpMethod);

        HttpMethod post = HttpMethodFactory.create("post");
        assertTrue(post instanceof PostHttpMethod);

        HttpMethod put = HttpMethodFactory.create("put");
        assertTrue(put instanceof PutHttpMethod);

        HttpMethod delete = HttpMethodFactory.create("delete");
        assertTrue(delete instanceof DeleteHttpMethod);
    }
}
