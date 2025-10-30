package com.brick.openapi.elements.info;

import com.brick.logger.Logger;
import com.brick.utilities.exception.KeyNotFound;import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.Optional;

/*
    Description: Contains Info About the Open Api Specification
 */
public class Info {

    private final String title; // Api Title
    private final String apiVersion; // Api Version
    private final Optional<String> summary; // Optional - Provides Summary of Api
    private final Optional<String> description; // Optional - Provides Description of Api
    private final Optional<String> termsOfService; // Optional - A Url to Terms and Service of Api
    private final Optional<Contact> contact;
    private final Optional<License> license;

    public Info(BrickMap brickMap) throws KeyNotFound {
        Logger.trace("Creating Info Object");
        this.title = brickMap.getString(OpenAPIKeyConstants.INFO_TITLE);
        this.apiVersion = brickMap.getString(OpenAPIKeyConstants.INFO_API_VERSION);

        this.summary = brickMap.getOptionalString(OpenAPIKeyConstants.SUMMARY);
        this.description = brickMap.getOptionalString(OpenAPIKeyConstants.DESCRIPTION);
        this.termsOfService = brickMap.getOptionalString(OpenAPIKeyConstants.INFO_TERMS_OF_SERVICE);
        this.contact = Contact.getContact( brickMap.getOptionalBrickMap(OpenAPIKeyConstants.INFO_CONTACT) );
        this.license = License.getLicense( brickMap.getOptionalBrickMap(OpenAPIKeyConstants.INFO_LICENSE) );
        Logger.trace("Info Object Created");
    }

    public String getTitle() {
        return title;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public Optional<String> getSummary() {
        return summary;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Optional<String> getTermsOfService() {
        return termsOfService;
    }

    public Optional<Contact> getContact() {
        return contact;
    }

    public Optional<License> getLicense() {
        return license;
    }
}
