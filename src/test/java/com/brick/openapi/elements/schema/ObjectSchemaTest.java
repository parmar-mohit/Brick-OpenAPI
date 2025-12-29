package com.brick.openapi.elements.schema;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

public class ObjectSchemaTest {
	
	@Test
	public void success_nullable() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/elements/schema/objectSchema_nullable.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap objectMap = fileReader.getMap();
		
		ObjectSchema schema = new ObjectSchema(objectMap, null);
		
		assertTrue( schema.validateData(null));
		assertTrue( schema.validateData(JsonNodeFactory.instance.objectNode()));
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "abc");
		assertTrue(schema.validateData(contentNode));
		
		contentNode.put("age",20);
		assertTrue( schema.validateData(contentNode));
	}
	
	@Test
	public void success_required() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/elements/schema/objectSchema_required.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap objectMap = fileReader.getMap();
		
		ObjectSchema schema = new ObjectSchema(objectMap, null);
		
		assertFalse( schema.validateData(null));
		assertFalse( schema.validateData(JsonNodeFactory.instance.objectNode()));
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		contentNode.put("name", "abc");
		assertTrue(schema.validateData(contentNode));
		
		contentNode.put("age",20);
		assertTrue( schema.validateData(contentNode));
		
		contentNode.put("name", 123);
		assertFalse( schema.validateData(contentNode));
	}

}
