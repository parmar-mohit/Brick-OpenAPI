package com.brick.openapi.elements.info;

import com.brick.utilities.BrickMap;
import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ContactTest {

    @Test
    public void test_detailPresent() throws FileNotFoundException, InvalidData{
    	String filePath = "/dummy_yaml/info/info_contact_success.yaml";
    	FileReader fileReader = new YamlFileReader(filePath);
    	BrickMap contactMap = fileReader.getMap();

        Optional<BrickMap> optionalBrickMap = Optional.of(contactMap);

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
