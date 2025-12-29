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

import tools.jackson.databind.node.ArrayNode;
import tools.jackson.databind.node.JsonNodeFactory;

public class ArraySchemaTest {
	@Test
	public void arraySchema_nullable() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/elements/schema/arraySchema_nullable.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap arrayMap = fileReader.getMap();
		
		ArraySchema schema = new ArraySchema(arrayMap, null);
		assertTrue( schema.validateData(null));
		assertFalse( schema.validateData(JsonNodeFactory.instance.objectNode()));
		
		ArrayNode contentNode = JsonNodeFactory.instance.arrayNode();
		contentNode.add("abc");
		assertFalse( schema.validateData(contentNode));
		
		contentNode.add("def");
		assertTrue( schema.validateData(contentNode));
		
		contentNode.add("ghi");
		contentNode.add("jkl");
		assertFalse(schema.validateData(contentNode));
		
		
		contentNode = JsonNodeFactory.instance.arrayNode();
		contentNode.add("abc");
		contentNode.add("def");
		contentNode.add(123);
		assertFalse( schema.validateData(contentNode));
	}
	
	@Test
	public void arraySchema_uniqueItems() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/elements/schema/arraySchema_uniqueItems.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap arrayMap = fileReader.getMap();
		
		ArraySchema schema = new ArraySchema(arrayMap, null);
		assertFalse( schema.validateData(null));
		assertFalse( schema.validateData(JsonNodeFactory.instance.objectNode()));
		
		ArrayNode contentNode = JsonNodeFactory.instance.arrayNode();
		contentNode.add("abc");
		assertTrue( schema.validateData(contentNode) );
		
		contentNode.add("abc");
		assertFalse( schema.validateData(contentNode) );
		
		contentNode = JsonNodeFactory.instance.arrayNode();
		contentNode.add("abc");
		contentNode.add("def");
		assertTrue( schema.validateData(contentNode) );
	}

}
