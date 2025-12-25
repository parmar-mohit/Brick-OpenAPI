package com.brick.openapi.elements.path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

public class ResponseTest {
	@Test
	public void response_contentPresent_success() throws FileNotFoundException, InvalidData, InvalidValue, KeyNotFound {
		String filePath = "/dummy_yaml/path/response_contentPresent_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap responseMap = fileReader.getMap();
		
		Response response = Response.getResponse(responseMap, null);
		
		assertEquals("response body description", response.getDescription().get());
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "name");
		contentNode.put("age",15);
		assertTrue( response.validateResponse(contentNode) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		contentNode.put("height", "height");
		contentNode.put("weight","weight");
		assertFalse( response.validateResponse(contentNodeInvalid) );
	}
	
	@Test
	public void response_contentNotPresent_success() throws FileNotFoundException, InvalidData, InvalidValue, KeyNotFound {
		String filePath = "/dummy_yaml/path/response_contentNotPresent_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap responseMap = fileReader.getMap();
		
		Response response = Response.getResponse(responseMap, null);
		
		assertFalse(response.getContent().isPresent());
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		assertTrue( response.validateResponse(contentNode) );
		
		ObjectNode contentNodeInvalid = JsonNodeFactory.instance.objectNode();
		contentNodeInvalid.put("height", "height");
		contentNodeInvalid.put("weight","weight");
		assertFalse( response.validateResponse(contentNodeInvalid) );
	}
	
	@Test
	public void getResponseTest_success() throws KeyNotFound, InvalidValue, FileNotFoundException, InvalidData {
		BrickMap responseMap = mock(BrickMap.class);
		String refValue = OpenAPIKeyConstants.REFERENCE_RESPONSE+"responseName";
		when( responseMap.contains(OpenAPIKeyConstants.REFERENCE ) ).thenReturn(true);
		when( responseMap.getString(OpenAPIKeyConstants.REFERENCE ) ).thenReturn(refValue).thenReturn("invalid");
		
		String filePath = "/dummy_yaml/path/response_contentNotPresent_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap responseFileMap = fileReader.getMap();
		
		Response response = Response.getResponse(responseFileMap, null);
		Components components = mock(Components.class);
		when( components.getResponse("responseName") ).thenReturn(response);
		
		assertEquals(response,Response.getResponse(responseMap, components) );
		
		assertThrows(InvalidValue.class, ()->{
			Response.getResponse(responseMap, components);
		});
		
		
	}
}
