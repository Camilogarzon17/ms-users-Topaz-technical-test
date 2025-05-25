# Microservicio de Gestión de Usuarios con Spring Boot

Este proyecto es un microservicio de gestión de usuarios que permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) utilizando Spring Boot. [cite: 2] Implementa la arquitectura hexagonal, buenas prácticas de programación, pruebas unitarias y gestión de seguridad básica.

## 📌 Requerimientos Funcionales

* **Gestión de Usuarios**: Operaciones CRUD para el modelo `User` con campos `ID` (autogenerado), `Nombre de usuario` y `Contraseña` (almacenada de forma encriptada). [cite: 4, 5]
* **Documentación API**: Generación de documentación interactiva de la API utilizando OpenAPI 3 (Swagger). [cite: 5]
* **Gestión de Errores**: Manejo adecuado de excepciones y provisión de mensajes de error significativos con códigos de estado HTTP adecuados. 

---

## ⚙️ Requerimientos Técnicos

- **Arquitectura Hexagonal**: Separación clara entre capas de dominio, aplicación e infraestructura mediante interfaces. 
- **Buenas Prácticas**: Aplicación de principios SOLID, código limpio y estándares de programación.
- **Pruebas Unitarias**: Uso de **JUnit** y **Mockito** con una cobertura mínima del 85%. Generación de informe con **JaCoCo**. 
  
- **Seguridad y Calidad del Código**: Sin vulnerabilidades críticas o altas simuladas con buenas prácticas y análisis estático. 
- **Gestión del Proyecto**: Uso de **Maven** para la gestión de dependencias y construcción. 

---

## 🚀 Tecnologías Utilizadas

**Spring Boot: Framework principal para el desarrollo del microservicio.**

**Java: Lenguaje de programación.**

**Maven: Herramienta de gestión de proyectos y dependencias.**

**JUnit: Framework para pruebas unitarias.**

**Mockito: Framework para la creación de mocks en pruebas unitarias.**

**JaCoCo: Herramienta para generar informes de cobertura de código.**

**OpenAPI 3 (Swagger): Para la documentación interactiva de la API.**

## 🛠️ Requisitos del Sistema

Para ejecutar este proyecto, necesitarás tener instalado:

Java Development Kit (JDK) versión 17 o superior.

Apache Maven versión 3.6.0 o superior.

## 📦 Cómo Ejecutar el Proyecto

1.  **Clonar el repositorio:**

    ```bash
    git clone https://github.com/Camilogarzon17/ms-users-Topaz-technical-test.git
    cd user_management
    ```
2.  **Construir el proyecto con Maven:**
    ```bash
    mvn clean install
    ```
3.  **Ejecutar la aplicación Spring Boot:**
    ```bash
    mvn spring-boot:run
    ```
    La aplicación se iniciará en `http://localhost:8080`.

### Endpoints de la API:

La documentación de la API interactiva está disponible en: `http://localhost:8080/swagger-ui.html`.

* `POST /topaz/v1/api/users`: Crear un nuevo usuario.
* `GET /topaz/v1/api/users/{id}`: Obtener un usuario por ID.
* `PUT /topaz/v1/api/users/{id}`: Actualizar un usuario existente.
* `DELETE /topaz/v1/api/users/{id}`: Eliminar un usuario por ID.
* `GET /topaz/v1/api/users/get-all-users`: Obtener todos los usuarios.

## 📊 Cómo Generar el Informe de Cobertura de Pruebas

Para generar el informe de cobertura de pruebas utilizando JaCoCo, ejecuta el siguiente comando Maven:

    ```bash
    mvn clean test jacoco:report
    ```

**O si estás en Windows con wrapper:**

    ```bash
    .\ mvnw clean test jacoco:report
    ```

## Cómo Visualisar el informe y la covertura

Para visualizar el informe se debe seguir los siguientes pasos:

1.  **navega hasta la carpeta .\ms-users\target\site\jacoco**
2.  **ubicar el archivo index.html**
2.  **Explora los resultados y métricas de cobertura.**

## 🗂️ Estructura del Proyecto
```plaintext
├── .mvn/
├── mvnw
├── mvnw.cmd
├── pom.xml
├── .gitignore
├── .gitattributes
├── src/
│   ├── main/
│   │   ├── java/com/topaz/ms_users/
│   │   │   ├── 📁 application/                        # 🧠 Capa de Aplicación: orquestación de casos de uso
│   │   │   │   ├── 📁 port/
│   │   │   │   │   ├── 📁 in/                         # 🎯 Puertos de entrada (interfaces para controladores)
│   │   │   │   │   │   └── 📄 UserServicePort.java
│   │   │   │   │   └── 📁 out/                        # 📤 Puertos de salida (interfaces para persistencia)
│   │   │   │   │       └── 📄 UserPersistencePort.java
│   │   │   │   └── 📁 service/                        # ⚙️ Implementaciones de la lógica de negocio
│   │   │   │       └── 📄 UserServiceImpl.java
│   │   │   ├── 📁 domain/                             # 🧩 Capa de Dominio: entidades y reglas del negocio
│   │   │   │   ├── 📁 exception/                      # 🚨 Excepciones del dominio
│   │   │   │   │   └── 📄 UserNotFoundException.java
│   │   │   │   └── 📁 model/                          # 📦 Modelos de dominio
│   │   │   │       └── 📄 User.java
│   │   │   ├── 📁 infrastructure/                     # 🏗️ Capa de Infraestructura: adaptadores y configuración
│   │   │   │   ├── 📁 adapter/
│   │   │   │   │   ├── 📁 input/web/                  # 🌐 Adaptadores de entrada (controladores REST)
│   │   │   │   │   │   ├── 📄 GlobalExceptionHandler.java
│   │   │   │   │   │   └── 📄 UserController.java
│   │   │   │   │   └── 📁 output/persistence/         # 💾 Adaptadores de salida (persistencia)
│   │   │   │   │       ├── 📄 UserJpaAdapter.java
│   │   │   │   │       └── 📄 UserJpaRepository.java
│   │   │   │   ├── 📁 config/                         # ⚙️ Configuraciones generales (CORS, Swagger, etc.)
│   │   │   │   │   ├── 📄 CorsConfig.java
│   │   │   │   │   └── 📄 SwaggerConfig.java
│   │   │   │   ├── 📁 constants/                      # 🧾 Constantes reutilizables
│   │   │   │   │   └── 📄 HttpConstants.java
│   │   │   │   └── 📁 security/                       # 🔐 Configuración de seguridad (auth/autz)
│   │   │   └── 📄 MsUsersApplication.java             # 🚀 Clase principal de la app Spring Boot
│   │   └── 📁 resources/
│   │       └── 📄 application.properties              # ⚙️ Archivo de configuración principal
│   └── test/java/com/topaz/ms_users/
│       ├── 📁 application/service/                    # 🧪 Pruebas unitarias - Capa de aplicación
│       │   └── 📄 UserServiceImplTest.java
│       ├── 📁 infrastructure/                         # 🧪 Pruebas unitarias - Capa de infraestructura
│       │   └── 📁 adapter/input/web/
│       │       ├── 📄 GlobalExceptionHandlerTest.java
│       │       └── 📄 UserControllerTest.java
│       └── 📄 MsUsersApplicationTests.java            # 🔍 Pruebas de integración de la app
```

## Autor

Desarrollado por Camilo Garzon – camilogarzon1722@hotmail.com