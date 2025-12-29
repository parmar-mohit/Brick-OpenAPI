package com.brick.openapi.elements.security.scheme;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class SecuritySchemeFactoryTest {
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue {
		String filePath = "/dummy_yaml/elements/security/securitySchemeFactory.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap contentMap = fileReader.getMap();
		
		assertThrows(InvalidValue.class,()->{
			SecuritySchemeFactory.getSecurityScheme(contentMap.getBrickMap("InvalidAuth"));
		});
		
		assertThrows(InvalidValue.class,()->{
			SecuritySchemeFactory.getSecurityScheme(contentMap.getBrickMap("InvalidScheme"));
		});
		
		assertInstanceOf(BasicAuthScheme.class, SecuritySchemeFactory.getSecurityScheme(contentMap.getBrickMap("BasicAuth")));
		assertInstanceOf(BearerAuthScheme.class, SecuritySchemeFactory.getSecurityScheme(contentMap.getBrickMap("BearerAuth")));
	}
}
