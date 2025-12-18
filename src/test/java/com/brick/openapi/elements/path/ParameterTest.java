package com.brick.openapi.elements.path;

import org.junit.jupiter.api.Test;

import com.brick.openapi.elements.schema.StringSchema;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParameterTest {

    @Test
    public void testParameter() {
        Parameter parameter = new Parameter();
        parameter.setName("name");
        parameter.setIn(ParameterType.PATH);
        parameter.setDescription("description
");
        parameter.setRequired(true);
        parameter.setSchema(new StringSchema());

        assertEquals("name", parameter.getName());
        assertEquals(ParameterType.PATH, parameter.getIn());
        assertEquals("description\n", parameter.getDescription());
        assertEquals(true, parameter.getRequired());
        assertEquals(StringSchema.class, parameter.getSchema().getClass());
    }
}
