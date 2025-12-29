package com.brick.openapi.elements.path.http.methods;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.FileNotFoundException;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class GetHttpMethodTest {
	@Test
	public void success() throws FileNotFoundException, InvalidData {
		String filePath = "/dummy_yaml/path/http/methods/get_success.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		assertDoesNotThrow(()->{
			new GetHttpMethod(methodMap.getBrickMap("get"), null, Optional.empty());
		});
	}
}
