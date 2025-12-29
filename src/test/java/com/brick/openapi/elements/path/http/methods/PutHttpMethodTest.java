package com.brick.openapi.elements.path.http.methods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.BrickRequestData;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

public class PutHttpMethodTest {
	@Test
	public void success_withRequestBody() throws InvalidData, KeyNotFound, InvalidValue, IOException {
		String filePath = "/dummy_yaml/path/http/methods/put_success_withRequestBody.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		
		assertDoesNotThrow(()->{
			new PutHttpMethod(methodMap.getBrickMap("put"), null, Optional.empty());
		});
		
		PutHttpMethod method = new PutHttpMethod(methodMap.getBrickMap("put"), null, Optional.empty());
		 
		
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
	}
	
	@Test
	public void success_withoutRequestBody() throws InvalidData, KeyNotFound, InvalidValue, IOException {
		String filePath = "/dummy_yaml/path/http/methods/put_success_withoutRequestBody.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		assertDoesNotThrow(()->{
			new PutHttpMethod(methodMap.getBrickMap("put"), null, Optional.empty());
		});
		
		PutHttpMethod method = new PutHttpMethod(methodMap.getBrickMap("put"), null, Optional.empty());
		
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "name");
		contentNode.put("age",15);
		BrickRequestData brickRequestData = new BrickRequestData(contentNode, null, null, null, null);
		assertFalse( method.validateRequest(brickRequestData) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		brickRequestData = new BrickRequestData(contentNodeInvalid, null, null, null, null);
		assertTrue( method.validateRequest(brickRequestData) );
	}
	
}
