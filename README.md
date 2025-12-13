# OpenApi Module

The **OpenApi** module is a lightweight Java-based parser that reads an OpenAPI YAML specification file and generates an in-memory **OpenAPI Specification object**.  
It is designed to be modular, extensible, and easy to integrate with other components of the framework.

---

## 🧩 Overview

This module provides a simple, dependency-light implementation of an **OpenAPI parser**, allowing other modules or services to consume, inspect, and process OpenAPI definitions programmatically.

Unlike full-featured third-party libraries, this parser is purpose-built for the framework — it focuses on **core OpenAPI structure parsing** and **maintainable extensibility** for future features.

---

## ⚙️ Features

### ✅ Currently Supported
- Parsing of OpenAPI documents written in **YAML** format
- Support for core OpenAPI specification sections:
    - `openapi` version
    - `info`
    - `servers`
    - `paths`
    - `operations`
    - `parameters`
    - `requestBody`
    - `responses`
    - `schemas` (basic data types, objects, arrays, enums)
- Generation of internal **OpenApiSpec** model for use across the framework
- Basic error handling and validation via `InvalidOpenApiSpecification`

---

## 🚧 Not Yet Supported (Planned for Future Versions)

The following OpenAPI features are currently **excluded** but may be added in future iterations:

| Category | Excluded Feature |
|-----------|------------------|
| **Linking** | `links` |
| **Object Schema Extensions** | `readOnly`, `writeOnly` |
| **Custom Extensions** | `x-*` vendor extensions |
| **Documentation Metadata** | `externalDocs` |
| **Security Schemes** | API Key authentication |
| **Security Schemes** | OAuth 2.0 |
| **Security Schemes** | OpenID Connect |
| **Not Keyword**   |  not |

> These features are intentionally omitted to keep the parser lightweight and focused on essential structure parsing.

---

## 📦 Installation

Clone the repository:

```bash
git clone https://github.com/parmar-mohit/Brick-OpenAPI.git
cd Brick-OpenAPI
```

Build with Maven/Gradle (depending on your project setup):

```bash
mvn clean install
```

or

```bash
gradle build
```

Add it as a dependency in your project:

```xml
<dependency>
    <groupId>com.brick</groupId>
    <artifactId>com.brick.openapi</artifactId>
    <version>1.0.0</version>
</dependency>
```

---

## 🧭 Design Goals

- **Simplicity:** Focus on the most frequently used OpenAPI features
- **Extensibility:** Modular architecture to allow incremental feature addition
- **Lightweight:** Minimal dependencies and fast parsing
- **Integration Ready:** Designed to integrate with other framework modules (e.g., code generation, validation)

---

## ⚠️ Limitations

- Only YAML input is supported (JSON support may be added later)
- No schema reference resolution (`$ref`) across files yet
- Limited validation (basic structural checks only)
- Excluded OpenAPI features listed above are ignored during parsing

---
