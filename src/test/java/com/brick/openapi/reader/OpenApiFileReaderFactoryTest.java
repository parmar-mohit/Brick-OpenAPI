package com.brick.openapi.reader;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.exception.InvalidData;

public class OpenApiFileReaderFactoryTest {
	@Test
	public void success() throws InvalidData {
		File file1 = new File("abc.yaml");
		assertInstanceOf(OpenAPIFileYamlReader.class, OpenApiFileReaderFactory.getReader(file1));
		
		File file2 = new File("abc.invalid");
		assertThrows(InvalidData.class,()->{
			OpenApiFileReaderFactory.getReader(file2);
		});
	}

}
