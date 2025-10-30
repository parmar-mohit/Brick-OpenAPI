package com.brick.openapi.elements.info;

import com.brick.utilities.BrickMap;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    public void test_detailPresent(){
        Map<String,Object> map = new HashMap<>();
        map.put("name","contactName");
        map.put("email","contactEmail");
        map.put("url","contactUrl");

        BrickMap brickMap = new BrickMap(map);
        Optional<BrickMap> optionalBrickMap = Optional.of(brickMap);

        Optional<Contact> optionalContact = Contact.getContact(optionalBrickMap);

        assertTrue(optionalContact.isPresent());
        assertTrue(optionalContact.get().getName().isPresent());
        assertEquals("contactName",optionalContact.get().getName().get());
        assertTrue(optionalContact.get().getEmail().isPresent());
        assertEquals("contactEmail",optionalContact.get().getEmail().get());
        assertTrue(optionalContact.get().getUrl().isPresent());
        assertEquals("contactUrl",optionalContact.get().getUrl().get());
    }

    @Test
    public void test_detailsNotPresent(){
        Optional<BrickMap> optionalBrickMap = Optional.empty();

        Optional<Contact> optionalContact = Contact.getContact(optionalBrickMap);

        assertFalse(optionalContact.isPresent());
    }
}
