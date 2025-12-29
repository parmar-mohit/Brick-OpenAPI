package com.brick.openapi.elements.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.CyclicReferenceFound;
import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class SchemaTest {
	@Test
	public void emptyMap() {
		BrickMap brickMap = new BrickMap(new HashMap<String, Object>());
		assertThrows(InvalidValue.class, ()->{
			Schema.getReferences(brickMap);
		});
	}
	
	@Test
	public void invalidReference() {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("$ref","invalid");
		BrickMap brickMap = new BrickMap(map);
		assertThrows(InvalidValue.class, ()->{
			Schema.getReferences(brickMap);
		});
	}
	 
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue, CyclicReferenceFound {
		String filePath = "/dummy_yaml/elements/schema/schema.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap testMap = fileReader.getMap();
		
		BrickMap schemaMap = testMap.getBrickMap("components").getBrickMap("schemas");
				
		List<String> multiValueList = Arrays.asList("testBaseSchema1","testBaseSchema2");
		assertEquals(multiValueList, Schema.getReferences(schemaMap.getBrickMap("testBaseSchema3")));
		assertEquals(multiValueList, Schema.getReferences(schemaMap.getBrickMap("testBaseSchema4")));
		assertEquals(multiValueList, Schema.getReferences(schemaMap.getBrickMap("testBaseSchema5")));
		
		List<String> singleValueList = Arrays.asList("testBaseSchema1");
		assertEquals(singleValueList, Schema.getReferences(schemaMap.getBrickMap("testBaseSchema6")));
		assertEquals(singleValueList, Schema.getReferences(schemaMap.getBrickMap("testBaseSchema7")));
		
	}
}
