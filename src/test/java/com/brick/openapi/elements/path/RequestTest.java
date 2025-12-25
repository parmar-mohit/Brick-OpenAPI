package com.brick.openapi.elements.path;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

public class RequestTest {
	
	@Test
	public void requestBody__required_success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/path/requestBody_required_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap requestBodyMap = fileReader.getMap();
		
		RequestBody requestBody = new RequestBody(requestBodyMap, null);
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "name");
		contentNode.put("age",15);
		assertTrue( requestBody.validateRequest(contentNode) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		contentNode.put("height", "height");
		contentNode.put("weight","weight");
		assertFalse( requestBody.validateRequest(contentNodeInvalid) );
	}
	
	@Test
	public void requestBody_notRequired_success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/path/requestBody_notRequired_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap requestBodyMap = fileReader.getMap();
		
		RequestBody requestBody = new RequestBody(requestBodyMap, null);
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		assertTrue( requestBody.validateRequest(contentNode) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		contentNodeInvalid.put("height", "height");
		contentNodeInvalid.put("weight","weight");
		assertFalse( requestBody.validateRequest(contentNodeInvalid) );
	}
	
	@Test
	public void requestBody_Exception() throws KeyNotFound {
		BrickMap requestBodyMap = mock(BrickMap.class);
		
		when( requestBodyMap.getBrickMap(OpenAPIKeyConstants.CONTENT) ).thenThrow( KeyNotFound.class);
		
		assertThrows(KeyNotFound.class,()->{
			new RequestBody(requestBodyMap,null);
		});
	}

}
