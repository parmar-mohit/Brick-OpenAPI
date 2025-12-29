package com.brick.openapi.elements.path.http;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;

public class HttpStatusCodeTest {
	
	@Test
	public void statusCodeTest() throws InvalidValue {
		assertEquals(HttpStatusCode.SUCCESS, HttpStatusCode.fromString("200"));
		assertEquals(HttpStatusCode.RESOURCE_CREATED, HttpStatusCode.fromString("201"));
		assertEquals(HttpStatusCode.NO_CONTENT, HttpStatusCode.fromString("204"));
		
		assertThrows(InvalidValue.class,()->{
			HttpStatusCode.fromString("299");
		});
		
	}

}
