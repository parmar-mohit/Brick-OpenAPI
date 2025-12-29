package com.brick.openapi.elements.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class ServerVariableTest {
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound {
		String filePath = "/dummy_yaml/elements/server/serverVariables.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap contentMap = fileReader.getMap();
		
		ServerVariable serverVariable = new ServerVariable("version",contentMap.getBrickMap("version"));
		assertEquals("version",serverVariable.getVariableName());
		assertEquals("Value of Version", serverVariable.getDescription().get());
		assertEquals("v1", serverVariable.getDefaultValue());
		assertEquals(Arrays.asList("v1","v2"), serverVariable.getPossibleValue().get());
	}
}
