package com.brick.openapi.elements.schema;

import com.brick.utilities.exception.KeyNotFound;import com.brick.logger.Logger;
import com.brick.openapi.elements.Components;
import com.brick.openapi.exception.InvalidValue;
import com.brick.openapi.exception.PropertyNotFound;
import com.brick.openapi.reader.OpenAPIKeyConstants;
import com.brick.utilities.BrickMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class Schema {

    private final List<Schema> allOfSchemas;
    private final List<Schema> anyOfSchemas;
    private final List<Schema> oneOfSchemas;

    public Schema(BrickMap brickMap, Components components) throws InvalidValue, KeyNotFound, PropertyNotFound {
        Logger.trace("Trying to Create Schema Object");

        this.allOfSchemas = new ArrayList<>();
        Optional<List<Map<String,Object>>> allOfList  = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.ALL_OF);
        if( allOfList.isPresent() ) {
            Logger.trace("allOf Schema Found");
            for (Map<String,Object> m : allOfList.get()) {
                this.allOfSchemas.add(SchemaFactory.getSchema(new BrickMap(m),components));
            }
        }

        this.anyOfSchemas = new ArrayList<>();
        Optional<List<Map<String,Object>>> anyOfList  = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.ANY_OF);
        if( anyOfList.isPresent() ) {
            Logger.trace("anyOf Schema Found");
            for (Map<String,Object> m : anyOfList.get()) {
                this.anyOfSchemas.add(SchemaFactory.getSchema(new BrickMap(m),components));
            }
        }

        this.oneOfSchemas = new ArrayList<>();
        Optional<List<Map<String,Object>>> oneOflist  = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.ONE_OF);
        if( oneOflist.isPresent() ) {
            Logger.trace("oneOf Schema Found");
            for (Map<String,Object> m : oneOflist.get()) {
                this.oneOfSchemas.add(SchemaFactory.getSchema(new BrickMap(m),components));
            }
        }

        Logger.trace("Schema Object Created");
    }

    /*
        Description: Returns a List of All the references that current schema uses
     */
    public static List<String> getReferences(BrickMap brickMap) throws KeyNotFound, InvalidValue {
        if( brickMap.isEmpty() ){
            InvalidValue invalidValue = new InvalidValue("Empty Object");
            Logger.logException(invalidValue);
            throw invalidValue;
        }

        List<String> references = new ArrayList<>();
        if( brickMap.contains(OpenAPIKeyConstants.REFERENCE) ){
            String refValue = brickMap.getString(OpenAPIKeyConstants.REFERENCE);
            if( !refValue.startsWith(OpenAPIKeyConstants.REFERENCE_SCHEMA) ){
                InvalidValue invalidValue = new InvalidValue(refValue);
                Logger.logException(invalidValue);
                throw  invalidValue;
            }

            String referenceSchema = refValue.substring(OpenAPIKeyConstants.REFERENCE_SCHEMA.length());
            references.add(referenceSchema);
        }else {
            Optional<List<Map<String,Object>>> allOfList = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.ALL_OF);
            if( allOfList.isPresent() ){ // Checking for References in All Of List
                for( Map<String,Object> m: allOfList.get() ) {
                    references.addAll( getReferences(new BrickMap(m)) );
                }
            }

            Optional<List<Map<String,Object>>> anyOfList = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.ANY_OF);
            if( anyOfList.isPresent() ){ // Checking for References in Any of List
                for( Map<String,Object> m :  anyOfList.get() ){
                    references.addAll( getReferences( new BrickMap(m) ) );
                }
            }

            Optional<List<Map<String,Object>>> oneOfList = brickMap.getOptionalListOfMap(OpenAPIKeyConstants.ONE_OF);
            if( oneOfList.isPresent() ){ // Checking References in One Of List
                for( Map<String,Object> m : oneOfList.get() ){
                    references.addAll( getReferences( new BrickMap(m) ) );
                }
            }


            SchemaType schemaType = SchemaType.fromString( brickMap.getString(OpenAPIKeyConstants.SCHEMA_TYPE) );
            switch (schemaType) { // Checking for References based on Schema Type
                case ARRAY:
                    references.addAll(Schema.getReferences( brickMap.getBrickMap(OpenAPIKeyConstants.ARRAY_SCHEMA) ));
                    break;

                case NUMBER:
                    break;

                case INTEGER:
                    break;

                case OBJECT:
                    BrickMap propertyMap = brickMap.getBrickMap(OpenAPIKeyConstants.PROPERTIES);
                    for( Map.Entry<String, Object> entry: propertyMap ){
                        references.addAll( Schema.getReferences( propertyMap.getBrickMap(entry.getKey()) )  );
                    }
                    break;

                case STRING:
                    break;

                default:
                    InvalidValue invalidValue = new InvalidValue(schemaType.toString());
                    Logger.logException(invalidValue);
                    throw invalidValue;
            }
        }

        return references;
    }
}
