# Microservicio de Gestión de Usuarios con Spring Boot

Este proyecto es un microservicio de gestión de usuarios que permite realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) utilizando Spring Boot. [cite: 2] Implementa la arquitectura hexagonal, buenas prácticas de programación, pruebas unitarias y gestión de seguridad básica. [cite: 3, 8, 9, 10, 11]

## 📌 Requerimientos Funcionales

* **Gestión de Usuarios**: Operaciones CRUD para el modelo `User` con campos `ID` (autogenerado), `Nombre de usuario` y `Contraseña` (almacenada de forma encriptada). [cite: 4, 5]
* **Documentación API**: Generación de documentación interactiva de la API utilizando OpenAPI 3 (Swagger). [cite: 5]
* **Gestión de Errores**: Manejo adecuado de excepciones y provisión de mensajes de error significativos con códigos de estado HTTP adecuados. [cite: 6, 7]

---

## ⚙️ Requerimientos Técnicos

- **Arquitectura Hexagonal**: Separación clara entre capas de dominio, aplicación e infraestructura mediante interfaces. [cite: 8]
- **Buenas Prácticas**: Aplicación de principios SOLID, código limpio y estándares de programación. [cite: 9]
- **Pruebas Unitarias**: Uso de **JUnit** y **Mockito** con una cobertura mínima del 85%. Generación de informe con **JaCoCo**. [cite: 10, 11, 19]
- **Seguridad y Calidad del Código**: Sin vulnerabilidades críticas o altas simuladas con buenas prácticas y análisis estático. [cite: 11, 20]
- **Gestión del Proyecto**: Uso de **Maven** para la gestión de dependencias y construcción. [cite: 13]

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

La documentación de la API interactiva está disponible en: `http://localhost:8080/swagger-ui.html`[cite: 5].

* `POST /topaz/v1/api/users`: Crear un nuevo usuario.
* `GET /topaz/v1/api/users/{id}`: Obtener un usuario por ID.
* `PUT /topaz/v1/api/users/{id}`: Actualizar un usuario existente.
* `DELETE /topaz/v1/api/users/{id}`: Eliminar un usuario por ID.
* `GET /topaz/v1/api/users/get-all-users`: Obtener todos los usuarios.

## 📊 Cómo Generar el Informe de Cobertura de Pruebas

Para generar el informe de cobertura de pruebas utilizando JaCoCo, ejecuta el siguiente comando Maven: [cite: 11, 16]

```bash
mvn clean test jacoco:report

**O si estás en Windows con wrapper:**
    ```bash
   .\mvnw clean test jacoco:report
    ```
## Cómo Visualisar el informe y la covertura

Para visualizar el informe se debe seguir los siguientes pasos:

1.  **navega hasta la carpeta .\ms-users\target\site\jacoco\**
2.  **ubicar el archivo index.html**
2.  **Explora los resultados y métricas de cobertura.**

## 🗂️ Estructura del Proyecto

├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/com/topaz/ms_users/
│   │   │   ├── application/             # Lógica de negocio y orquestación (Capa de Aplicación)
│   │   │   │   ├── port/                # Interfaces de puertos (entrada y salida)
│   │   │   │   │   ├── in/              # Puertos de entrada (interfaz para el controlador)
│   │   │   │   │   │   └── UserServicePort.java
│   │   │   │   │   └── out/             # Puertos de salida (interfaz para la persistencia)
│   │   │   │   │       └── UserPersistencePort.java
│   │   │   │   └── service/             # Implementación de la lógica de negocio
│   │   │   │       └── UserServiceImpl.java
│   │   │   ├── domain/                  # Entidades de dominio y reglas de negocio (Capa de Dominio)
│   │   │   │   ├── exception/           # Excepciones personalizadas del dominio
│   │   │   │   │   └── UserNotFoundException.java
│   │   │   │   └── model/               # Modelos de datos del dominio
│   │   │   │       └── User.java
│   │   │   ├── infrastructure/          # Adaptadores y configuración (Capa de Infraestructura)
│   │   │   │   ├── adapter/             # Implementaciones de los puertos
│   │   │   │   │   ├── input/web/       # Adaptadores de entrada (controladores REST)
│   │   │   │   │   │   ├── GlobalExceptionHandler.java # Manejador global de excepciones
│   │   │   │   │   │   └── UserController.java
│   │   │   │   │   └── output/persistence/ # Adaptadores de salida (persistencia de datos)
│   │   │   │   │       ├── UserJpaAdapter.java
│   │   │   │   │       └── UserJpaRepository.java
│   │   │   │   ├── config/              # Clases de configuración (CORS, Swagger)
│   │   │   │   │   ├── CorsConfig.java
│   │   │   │   │   └── SwaggerConfig.java
│   │   │   │   ├── constants/           # Constantes de la aplicación
│   │   │   │   │   └── HttpConstants.java
│   │   │   │   └── security/            # Configuración de seguridad
│   │   │   │       # (Archivos de seguridad, como configuración de autenticación/autorización)
│   │   │   └── MsUsersApplication.java  # Clase principal de la aplicación Spring Boot
│   │   └── resources/
│   │       └── application.properties   # Archivo de configuración de la aplicación
│   └── test/java/com/topaz/ms_users/
│       ├── aplication/service/          # Pruebas unitarias para la capa de aplicación
│       │   └── UserServiceImplTest.java
│       ├── infraestructure              # Pruebas unitarias para la capa de infraestrctura 
│       │   │   ├── adapter/           
│       │   │   │   ├── input/web/       
│       │   │   │   │   ├── GlobalExceptionHandlerTes.java
│       │   │   │   │   └── UserControllerTest.java
│       └── MsUsersApplicationTests.java # Pruebas de integración básicas
├── .gitattributes
├── .gitignore
├── mvnw
├── mvnw.cmd
└── pom.xml  

## Autor

Desarrollado por Camilo Garzon – camilogarzon1722@hotmail.com