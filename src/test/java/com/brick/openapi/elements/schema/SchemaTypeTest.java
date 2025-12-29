package com.brick.openapi.elements.schema;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;

public class SchemaTypeTest {
	
	@Test
	public void success() throws InvalidValue {
		assertEquals(SchemaType.ARRAY, SchemaType.fromString("array"));
		assertEquals(SchemaType.INTEGER, SchemaType.fromString("integer"));
		assertEquals(SchemaType.NUMBER, SchemaType.fromString("number"));
		assertEquals(SchemaType.STRING, SchemaType.fromString("string"));
		assertEquals(SchemaType.OBJECT, SchemaType.fromString("object"));
		assertThrows(InvalidValue.class,()->{
			SchemaType.fromString("invalid");
		});
	}

}
