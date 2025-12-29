package com.brick.openapi.elements.security.scheme;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.KeyNotFound;

public class BearerAuthSchemeTest {
	@Test
	public void success() throws KeyNotFound {
		BrickMap brickMap = mock(BrickMap.class);
		when( brickMap.getString(OpenAPIKeyConstants.BEARER_FORMAT)).thenReturn("JWT");
		assertDoesNotThrow(()->{
			new BearerAuthScheme(brickMap);
		});
	}
}
