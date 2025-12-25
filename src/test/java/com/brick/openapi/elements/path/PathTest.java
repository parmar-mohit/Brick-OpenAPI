package com.brick.openapi.elements.path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.Components;
import com.brick.openapi.elements.path.http.methods.GetHttpMethod;
import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class PathTest {
	
	@Test
	public void matches_withoutPathVariable() throws InvalidValue, KeyNotFound {
		String endpoint = "/categories/categoryId";
		BrickMap brickMap = new BrickMap(new HashMap<String, Object>());
	
		Path path = new Path(endpoint,brickMap,null,null);
		
		String endpoint1 = "/categories/categoryId";
		assertTrue(path.matches(endpoint1));
		
		String endpoint2 = "/categories/ctgryId";
		assertFalse(path.matches(endpoint2));
	}
	
	@Test
	public void matches_withPathVarible() throws InvalidValue, KeyNotFound {
		String endpoint = "/categories/{categoryId}";
		BrickMap brickMap = new BrickMap(new HashMap<String, Object>());
	
		Path path = new Path(endpoint,brickMap,null,null);
		
		String endpoint1 = "/categories/123";
		assertTrue(path.matches(endpoint1));
		
		String endpoint2 = "/abc/123/xyz/xyz";
		assertFalse(path.matches(endpoint2));
	}
	
	@Test
	public void getPathVariables_matchFailure() throws InvalidValue, KeyNotFound {
		String endpoint = "/categories/{categoryId}";
		BrickMap brickMap = new BrickMap(new HashMap<String, Object>());
	
		Path path = new Path(endpoint,brickMap,null,null);
		
		String endpoint1 = "/abc/def";
		assertThrows(InvalidData.class, ()->{
			path.getPathVariables(endpoint1);
		});
	}
	
	@Test
	public void getPathVariables_matchSuccess() throws InvalidValue, KeyNotFound, InvalidData {
		String endpoint = "/categories/{categoryId}";
		BrickMap brickMap = new BrickMap(new HashMap<String, Object>());
	
		Path path = new Path(endpoint,brickMap,null,null);
		
		String endpoint1 = "/categories/123";
		
		Map<String,String> expected = new HashMap<String, String>();
		expected.put("categoryId", "123");
		
		Map<String,String> actual = path.getPathVariables(endpoint1);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void path_success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/path/path_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap pathMap = fileReader.getMap();
		
		String endpoint = "/categories/{categoryId}";
		
		Path path = new Path(endpoint,pathMap.getBrickMap(endpoint),null,Optional.empty());
		
		assertEquals("/categories/{categoryId}", path.getUri());
		assertTrue( path.getMethod("get") instanceof GetHttpMethod );
		assertNull( path.getMethod("post") );
	}
	
	@Test
	public void path_invalidEndpoint() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/path/path_invalidEndpoint.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap pathMap = fileReader.getMap();
		
		String endpoint = "/categories/{categoryId";
		
		assertThrows(InvalidValue.class, ()->{
			new Path(endpoint,pathMap.getBrickMap(endpoint),null,Optional.empty());
		});
	}
	
	@Test
	public void path_parameterNotPresent() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/path/path_parameterNotPresent.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap pathMap = fileReader.getMap();
		
		String endpoint = "/categories/{categoryId}";
		
		assertThrows(InvalidValue.class, ()->{
			new Path(endpoint,pathMap.getBrickMap(endpoint),null,Optional.empty());
		});
	}
}
