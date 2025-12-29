package com.brick.openapi.elements.schema;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
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

public class SchemaFactoryTest {
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound, InvalidValue, CyclicReferenceFound {
		String filePath = "/dummy_yaml/elements/schema/schemaFactory.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap testMap = fileReader.getMap();
		
		assertInstanceOf(ArraySchema.class, SchemaFactory.getSchema(testMap.getBrickMap("arraySchema"), null));
		assertInstanceOf(IntegerSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("integerSchema"), null));
		assertInstanceOf(NumberSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("numberSchema"), null));
		assertInstanceOf(StringSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("stringSchema"), null));
		assertInstanceOf(ObjectSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("objectSchema"), null));
		
		Components components = new Components(testMap.getBrickMap("components"));
		assertInstanceOf(AllOfSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("allOfSchema"), components));
		assertInstanceOf(OneOfSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("oneOfSchema"), components));
		assertInstanceOf(AnyOfSchema.class, SchemaFactory.getSchema(testMap.getBrickMap("anyOfSchema"), components));
		
		assertThrows(InvalidValue.class,()->{
			SchemaFactory.getSchema(testMap.getBrickMap("invalidReference"), components);
		});
		assertThrows(InvalidValue.class,()->{
			SchemaFactory.getSchema(testMap.getBrickMap("invalidSchema"), components);
		});
	}
}
