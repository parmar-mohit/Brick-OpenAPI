# Using the OpenApi Parser Module

This document explains how to use the **OpenApi Parser** module — a lightweight Java parser that reads an OpenAPI YAML file and returns an in-memory **OpenApi** object representation.

---

## 🧩 Overview

The OpenApi parser module is designed to make it easy to read and work with OpenAPI specifications in YAML format.  
It exposes a base abstract class `OpenApiFileReader`, which defines the general parsing contract, and concrete implementations such as `OpenApiYamlFileReader` that perform the actual parsing.

---

## ⚙️ Class Hierarchy

```
OpenApiFileReader  (abstract)
   │
   └── OpenApiYamlFileReader
```

- `OpenApiFileReader`: Base abstract class defining the parsing structure.
- `OpenApiYamlFileReader`: Current implementation that supports YAML input files.

---

## 🧱 Usage Flow

### Step 1: Create an Instance

Create an instance of a class that extends `OpenApiFileReader`.  
Currently, the available implementation is `OpenApiYamlFileReader`.

You must pass the filename (path to your YAML file) in the constructor.

```java
OpenApiYamlFileReader reader = new OpenApiYamlFileReader("path/to/openapi.yaml");
```

---

### Step 2: Parse the File

Call the `OpenApi()` method on the reader object.  
This will parse the file and return an **OpenApi** object if successful.

If the file is invalid or cannot be parsed, the method will throw an exception (for example, `OpenApiParseException`).

```java
import com.brick.openapi.exception.InvalidOpenAPISpecification;
try{
    OpenAPI openApi = reader.OpenApi();
    System.out.println("Parsed successfully!");
}catch(InvalidOpenAPISpecification e){
    System.err.println("Failed to parse OpenAPI file: "+e.getMessage());
}
```

---

### Step 3: Access the Parsed Data

Once you get the `OpenApi` object, you can access its fields using **getter methods**.

Example:

```java
System.out.println("OpenAPI Version: " + openApi.getOpenApiVersion());
System.out.println("Title: " + openApi.getInfo().getTitle());
System.out.println("Version: " + openApi.getInfo().getVersion());
```

---

## 🧭 Handling Optional Fields

Some fields in the `OpenApi` model are **optional** and represented using `java.util.Optional`.  
You must check if the value is present before accessing it to avoid runtime exceptions.

Example:

```java
openApi.getInfo().getDescription().ifPresent(description -> {
    System.out.println("Description: " + description);
});
```

Or, with a default value:

```java
String description = openApi.getInfo().getDescription().orElse("No description provided.");
System.out.println(description);
```

---

## 🧩 Complete Example

### Example YAML (`example.yaml`)
```yaml
openapi: 3.0.3
info:
  title: Sample API
  version: 1.0.0
  description: This is a sample OpenAPI definition.
paths:
  /users:
    get:
      summary: Get all users
      responses:
        '200':
          description: Successful response
```

### Java Example
```java
import com.yourframework.openapi.reader.OpenApiYamlFileReader;
import com.yourframework.openapi.model.OpenApi;
import com.yourframework.openapi.exceptions.OpenApiParseException;

public class Example {
    public static void main(String[] args) {
        try {
            OpenApiYamlFileReader reader = new OpenApiYamlFileReader("example.yaml");
            OpenApi openApi = reader.OpenApi();

            System.out.println("OpenAPI Version: " + openApi.getOpenapi());
            System.out.println("Title: " + openApi.getInfo().getTitle());

            openApi.getInfo().getDescription().ifPresent(desc ->
                System.out.println("Description: " + desc)
            );
        } catch (OpenApiParseException e) {
            System.err.println("Error parsing OpenAPI file: " + e.getMessage());
        }
    }
}
```

---

## ⚠️ Notes

- Only **YAML** input is currently supported.
- Invalid or missing files will result in a parsing exception.
- Optional fields must be handled using `Optional.isPresent()` or `Optional.orElse()`.
- Future releases may include `OpenApiJsonFileReader` and support for additional OpenAPI features.

---