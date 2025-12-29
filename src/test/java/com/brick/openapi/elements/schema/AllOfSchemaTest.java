package com.brick.openapi.elements.schema;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.CyclicReferenceFound;
import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import tools.jackson.databind.node.JsonNodeFactory;
import tools.jackson.databind.node.ObjectNode;

public class AllOfSchemaTest {
	
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue, CyclicReferenceFound {
		String filePath = "/dummy_yaml/elements/schema/allOfSchema.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap testMap = fileReader.getMap();
		
		Components components = new Components(testMap.getBrickMap("components"));
		AllOfSchema schema = new AllOfSchema(testMap.getBrickMap("testSchema"), components);
		
		ObjectNode contentNode = JsonNodeFactory.instance.objectNode();
		assertFalse( schema.validateData(contentNode) );
		
		contentNode.put("property1", 3);
		contentNode.put("property2", "abc");
		assertFalse( schema.validateData(contentNode));
		
		contentNode.put("property3", 4);
		contentNode.put("property4", "def");
		assertTrue( schema.validateData(contentNode) );
	}

}
