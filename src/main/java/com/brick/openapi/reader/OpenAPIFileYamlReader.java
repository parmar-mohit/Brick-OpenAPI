package com.brick.openapi.reader;

import com.brick.utilities.exception.InvalidData;
import com.brick.utilities.exception.KeyNotFound;
import com.brick.logger.Logger;
import com.brick.openapi.exception.*;
import com.brick.openapi.OpenAPI;
import com.brick.utilities.BrickMap;
import com.brick.utilities.file.FileReader;
import com.brick.utilities.file.YamlFileReader;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class OpenAPIFileYamlReader extends OpenAPIFileReader {

    public OpenAPIFileYamlReader(String filePath) {
        super(filePath);
    }

    /*
        Description: Getting OpenApi Object From File
     */
    @Override
    public OpenAPI getOpenAPI() throws FileNotFoundException, InvalidData, InvalidOpenAPISpecification {
        try {
            Logger.info("Trying to Create OpenAPI Object from : "+this.fileName);

            FileReader fileReader = new YamlFileReader(this.fileName);
            BrickMap brickMap = fileReader.getMap();

            OpenAPI openAPI = new OpenAPI( brickMap );
            Logger.info("OpenAPI Object Created from : "+this.fileName);
            return openAPI;
        } catch (KeyNotFound e) {
            InvalidOpenAPISpecification invalidOpenAPISpecification = new InvalidOpenAPISpecification(fileName, InvalidOpenAPISpecification.Reason.KEY_NOT_FOUND, e.getKeyNotFound());
            Logger.logException(invalidOpenAPISpecification);
            throw invalidOpenAPISpecification;
        }catch (PropertyNotFound e){
            InvalidOpenAPISpecification invalidOpenAPISpecification = new InvalidOpenAPISpecification(fileName, InvalidOpenAPISpecification.Reason.PROPERTY_NOT_FOUND ,e.getPropertyNotFound());
            Logger.logException(invalidOpenAPISpecification);
            throw invalidOpenAPISpecification;
        }catch (InvalidValue e){
            InvalidOpenAPISpecification invalidOpenAPISpecification = new InvalidOpenAPISpecification(fileName, InvalidOpenAPISpecification.Reason.INVALID_VALUE ,e.getInvalidValue());
            Logger.logException(invalidOpenAPISpecification);
            throw invalidOpenAPISpecification;
        }catch (ClassCastException e){
            InvalidOpenAPISpecification invalidOpenAPISpecification = new InvalidOpenAPISpecification(fileName, InvalidOpenAPISpecification.Reason.INVALID_DATA_TYPE ,e.getMessage());
            Logger.logException(invalidOpenAPISpecification);
            throw invalidOpenAPISpecification;
        }catch (CyclicReferenceFound e){
            InvalidOpenAPISpecification invalidOpenAPISpecification = new InvalidOpenAPISpecification(fileName, InvalidOpenAPISpecification.Reason.CYCLIC_REFERENCE ,e.getMessage());
            Logger.logException(invalidOpenAPISpecification);
            throw invalidOpenAPISpecification;
        }
    }
}
