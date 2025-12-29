package com.brick.openapi.elements.security.scheme;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;

public class BearerFormatTest {
	@Test
	public void success() throws InvalidValue {
		assertEquals(BearerFormat.JWT, BearerFormat.fromString("JWT"));
		assertThrows(InvalidValue.class, ()->{
			BearerFormat.fromString("invalid");
		});
	}
}
