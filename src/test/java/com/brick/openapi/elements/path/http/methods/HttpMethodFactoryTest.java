package com.brick.openapi.elements.path.http.methods;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.brick.openapi.exception.InvalidValue;
import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class HttpMethodFactoryTest {
	
	@Test
	public void success() throws FileNotFoundException, InvalidData, InvalidValue, KeyNotFound {
		String filePath = "/dummy_yaml/path/http/methods/post_success_withRequestBody.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap methodMap = fileReader.getMap();
		
		assertTrue( HttpMethodFactory.getHttpMethod("get", (Map<String,Object>)methodMap.getObject("post"), null, Optional.empty())  instanceof GetHttpMethod );
		assertTrue( HttpMethodFactory.getHttpMethod("put", (Map<String,Object>)methodMap.getObject("post"), null, Optional.empty())  instanceof PutHttpMethod );
		assertTrue( HttpMethodFactory.getHttpMethod("post", (Map<String,Object>)methodMap.getObject("post"), null, Optional.empty())  instanceof PostHttpMethod );
		assertTrue( HttpMethodFactory.getHttpMethod("delete", (Map<String,Object>)methodMap.getObject("post"), null, Optional.empty())  instanceof DeleteHttpMethod );
		assertThrows(InvalidValue.class,()->{
			HttpMethodFactory.getHttpMethod("invalid", (Map<String,Object>)methodMap.getObject("post"), null, Optional.empty());
		});
	}

}
