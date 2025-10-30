package com.brick.openapi.elements;

import com.brick.openapi.exception.CyclicReferenceFound;
import com.brick.utilities.BrickMap;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ComponentsTest {

    @Test
    public void componentGeneration_cyclicReference(){
        Map<String,Object> componentsMap = new HashMap<>();
        Map<String,Object> schemaMap = new HashMap<>();

        Map<String,Object> schemaA = new HashMap<>();
        schemaA.put("type","object");
        Map<String,Object> schemaAProperties = new HashMap<>();
        Map<String,Object> relatedB = new HashMap<>();
        relatedB.put("$ref","#/components/schemas/B");
        schemaAProperties.put("relatedB", relatedB);
        schemaA.put("properties", schemaAProperties);

        Map<String,Object> schemaB = new HashMap<>();
        schemaB.put("type","object");
        Map<String,Object> schemaBProperties = new HashMap<>();
        Map<String,Object> relatedA = new HashMap<>();
        relatedA.put("$ref","#/components/schemas/A");
        schemaBProperties.put("relatedA", relatedA);
        schemaB.put("properties", schemaBProperties);

        schemaMap.put("A", schemaA);
        schemaMap.put("B", schemaB);

        componentsMap.put("schemas",schemaMap);

        assertThrows(CyclicReferenceFound.class,()->{
            new Components( new BrickMap(componentsMap) );
        });
    }
}
