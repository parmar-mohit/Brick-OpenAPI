package com.brick.openapi.elements.info;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InfoTest {
    @Test
    public void test_allDetailsPresent() throws KeyNotFound, FileNotFoundException, InvalidData {
    	String filePath = "/dummy_yaml/info/info_success.yaml";
    	FileReader fileReader = new YamlFileReader(filePath);
    	BrickMap infoMap = fileReader.getMap();

        Info info = new Info(infoMap);
        assertEquals("infoTitle",info.getTitle());
        assertEquals("v1.0",info.getApiVersion());
        assertTrue(info.getSummary().isPresent());
        assertEquals("infoSummary",info.getSummary().get());
        assertTrue(info.getDescription().isPresent());
        assertEquals("infoDescription",info.getDescription().get());
        assertTrue(info.getTermsOfService().isPresent());
        assertEquals("infoTermsOfService",info.getTermsOfService().get());
        assertFalse(info.getContact().isPresent());
        assertFalse(info.getLicense().isPresent());
    }

    @Test
    public void test_noDetailsPresent(){
        Map<String,Object> map = new HashMap<>();

        BrickMap brickMap = new BrickMap(map);

        assertThrows(KeyNotFound.class,()->{
            new Info(brickMap);
        });
    }
}
