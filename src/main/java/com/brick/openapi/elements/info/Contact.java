package com.brick.openapi.elements.info;

import com.brick.logger.Logger;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

/*
    Description : Contains Contact Info for OpenApi Specification
 */
public class Contact {
    private final Optional<String> name;
    private final Optional<String> email;
    private final Optional<String> url;

    public static Optional<Contact> getContact(Optional<BrickMap> optionalBrickMap){
        
        if( optionalBrickMap.isPresent() ){
            Contact contact = new Contact(optionalBrickMap.get());
            
            return Optional.of(  contact);
        }

        
        return Optional.empty();
    }

    private Contact(BrickMap brickMap ){
        this.name = brickMap.getOptionalString(OpenAPIKeyConstants.NAME);
        this.email = brickMap.getOptionalString(OpenAPIKeyConstants.CONTACT_EMAIL);
        this.url = brickMap.getOptionalString(OpenAPIKeyConstants.URL);
    }

    public Optional<String> getName() {
        return name;
    }

    public Optional<String> getEmail() {
        return email;
    }

    public Optional<String> getUrl() {
        return url;
    }
}
