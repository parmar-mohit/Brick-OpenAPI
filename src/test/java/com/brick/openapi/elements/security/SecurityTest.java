package com.brick.openapi.elements.security;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

public class SecurityTest {
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue, CyclicReferenceFound {
		String filePath = "/dummy_yaml/elements/security/security_valid.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap contentMap = fileReader.getMap();
		
		Components components = new Components(contentMap.getBrickMap("components"));
		
		assertDoesNotThrow(()->{
			new Security(contentMap.getListOfMap("scheme"), components);
		});
	}
	
	@Test
	public void exception() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue, CyclicReferenceFound {
		String filePath = "/dummy_yaml/elements/security/security_invalid.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap contentMap = fileReader.getMap();
		
		Components components = new Components(contentMap.getBrickMap("components"));
		
		assertThrows(InvalidValue.class,()->{
			new Security(contentMap.getListOfMap("scheme"), components);
		});
	}
}
