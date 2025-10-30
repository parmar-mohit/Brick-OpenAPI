package com.brick.openapi.elements.info;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.KeyNotFound;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class LicenseTest {
    @Test
    public void test_nameNotPresent(){
        Map<String,Object> map = new HashMap<>();
        map.put("url","licenseUrl");

        BrickMap brickMap = new BrickMap(map);
        Optional<BrickMap> optionalBrickMap = Optional.of(brickMap);

        assertThrows(KeyNotFound.class,()->{
            License.getLicense(optionalBrickMap);
        });
    }
    @Test
    public void test_detailPresent() throws KeyNotFound {
        Map<String,Object> map = new HashMap<>();
        map.put("name","licenseName");
        map.put("url","licenseUrl");

        BrickMap brickMap = new BrickMap(map);
        Optional<BrickMap> optionalBrickMap = Optional.of(brickMap);

        Optional<License> optionalLicense = License.getLicense(optionalBrickMap);

        assertTrue(optionalLicense.isPresent());
        assertEquals("licenseName",optionalLicense.get().getName());
        assertTrue(optionalLicense.get().getUrl().isPresent());
        assertEquals("licenseUrl",optionalLicense.get().getUrl().get());
    }

    @Test
    public void test_detailsNotPresent() throws KeyNotFound {
        Optional<BrickMap> optionalBrickMap = Optional.empty();

        Optional<License> optionalLicense = License.getLicense(optionalBrickMap);

        assertFalse(optionalLicense.isPresent());
    }
}
