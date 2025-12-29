package com.brick.openapi.elements.path.http.methods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.path.http.HttpStatusCode;
import com.brick.openapi.elements.security.Security;
import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.BrickRequestData;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;


public class PostHttpMethodTest {
	@Test
	public void success_withRequestBody() throws InvalidData, KeyNotFound, InvalidValue, IOException {
		String filePath = "/dummy_yaml/path/http/methods/post_success_withRequestBody.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		
		assertDoesNotThrow(()->{
			new PostHttpMethod(methodMap.getBrickMap("post"), null, Optional.empty());
		});
		
		PostHttpMethod method = new PostHttpMethod(methodMap.getBrickMap("post"), null, Optional.empty());
		
		assertEquals("Create Order", method.getSummary().get());
		assertEquals("Post order details to server for processing", method.getDescription().get());
		
		assertEquals(1, method.getParameters().size());
		
		assertTrue(method.getResponses().containsKey(HttpStatusCode.SUCCESS));
		
		assertTrue(method.hasParameter("categoryId"));
		assertFalse(method.hasParameter("invalid"));
		
		
		//Request Body Validation
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "name");
		contentNode.put("age",15);
		Map<String,String> header = new HashMap<String, String>();
		header.put("categoryId", "abc");
		BrickRequestData brickRequestData = new BrickRequestData(contentNode, null, header, null, null);
		assertTrue( method.validateRequest(brickRequestData) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		contentNodeInvalid.put("height", "height");
		contentNodeInvalid.put("weight","weight");
		brickRequestData = new BrickRequestData(contentNodeInvalid, null, header, null, null);
		assertFalse( method.validateRequest(brickRequestData) );
		
		header.clear();
		brickRequestData = new BrickRequestData(contentNode, null, header, null, null);
		assertFalse( method.validateRequest(brickRequestData) );
		
		// Response Body Validation
		assertFalse(method.validateResponse(567, null));
		assertFalse(method.validateResponse(201, null));
		ObjectNode responseNode = JsonNodeFactory.instance.objectNode();
		responseNode.put("orderId", 122);
		assertTrue( method.validateResponse(200, responseNode));
		responseNode = JsonNodeFactory.instance.objectNode();
		responseNode.put("orderId","123");
		assertFalse(method.validateResponse(200, responseNode));
	}
	
	@Test
	public void success_withoutRequestBody() throws InvalidData, KeyNotFound, InvalidValue, IOException {
		String filePath = "/dummy_yaml/path/http/methods/post_success_withoutRequestBody.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		
		assertDoesNotThrow(()->{
			new PostHttpMethod(methodMap.getBrickMap("post"), null, Optional.empty());
		});
		
		PostHttpMethod method = new PostHttpMethod(methodMap.getBrickMap("post"), null, Optional.empty());
		
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "name");
		contentNode.put("age",15);
		BrickRequestData brickRequestData = new BrickRequestData(contentNode, null, null, null, null);
		assertFalse( method.validateRequest(brickRequestData) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		brickRequestData = new BrickRequestData(contentNodeInvalid, null, null, null, null);
		assertTrue( method.validateRequest(brickRequestData) );
	}
	
	@Test
	public void securityFromParent() throws FileNotFoundException, InvalidData {
		String filePath = "/dummy_yaml/path/http/methods/post_success_withRequestBody.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		Security security = mock(Security.class);
		
		
		assertDoesNotThrow(()->{
			new PostHttpMethod(methodMap.getBrickMap("post"), null, Optional.of(security));
		});
	}
}
