package com.brick.openapi.elements.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

public class ServerTest {
	@Test
	public void success() throws FileNotFoundException, InvalidData, KeyNotFound {
		String filePath = "/dummy_yaml/elements/server/server.yaml";
		FileReader fileReader = new YamlFileReader(filePath);
		BrickMap contentMap = fileReader.getMap();
		
		Server server = new Server(contentMap);
		assertEquals("https://randomurl.com:{port}/production/{version}",server.getUrl());
		assertEquals("Production Server", server.getDescription().get());
		assertEquals(2, server.getVariables().size());
	}
}
