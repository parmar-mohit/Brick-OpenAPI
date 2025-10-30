package com.brick.openapi.elements.info;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.KeyNotFound;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class InfoTest {
    @Test
    public void test_allDetailsPresent() throws KeyNotFound {
        Map<String,Object> map = new HashMap<>();
        map.put("title","infoTitle");
        map.put("version","v1.0");
        map.put("summary","infoSummary");
        map.put("description","infoDescription");
        map.put("termsOfService","infoTermsOfService");

        BrickMap brickMap = new BrickMap(map);

        Info info = new Info(brickMap);
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
    public void test_necessaryDetailsPresent() throws KeyNotFound {
        Map<String,Object> map = new HashMap<>();
        map.put("title","infoTitle");
        map.put("version","v1.0");

        BrickMap brickMap = new BrickMap(map);

        Info info = new Info(brickMap);
        assertEquals("infoTitle",info.getTitle());
        assertEquals("v1.0",info.getApiVersion());
        assertFalse(info.getSummary().isPresent());
        assertFalse(info.getDescription().isPresent());
        assertFalse(info.getTermsOfService().isPresent());
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
