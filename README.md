# App Example - Spring Boot con Arquitectura Hexagonal

Proyecto Spring Boot que implementa un CRUD de productos siguiendo una arquitectura hexagonal (o arquitectura limpia). El proyecto demuestra la separación de responsabilidades en diferentes capas y proporciona tests unitarios para las clases implementadas.

## Arquitectura Hexagonal

El proyecto está organizado en tres capas principales:

### 1. Capa de Dominio (Domain)
Contiene la lógica de negocio y las entidades principales del sistema.
- **Model**: `Product` - Entidad de dominio que representa un producto
- **Port**: `ProductRepository` - Interfaz que define las operaciones de persistencia

### 2. Capa de Aplicación (Application)
Contiene los casos de uso y servicios que coordinan la lógica de negocio.
- **Service**: `ProductService` - Servicio que implementa los casos de uso para la gestión de productos

### 3. Capa de Infraestructura (Infrastructure)
Contiene las implementaciones concretas de los puertos y adaptadores externos.
- **Persistence**: `ProductEntity`, `SpringDataProductRepository`, `ProductRepositoryImpl` - Implementación JPA del repositorio
- **Controller**: `ProductController` - Controlador REST que expone los endpoints de la API

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/example/app/
│   │   ├── AppApplication.java                    # Clase principal de Spring Boot
│   │   ├── domain/
│   │   │   ├── model/
│   │   │   │   └── Product.java                   # Entidad de dominio
│   │   │   └── port/
│   │   │       └── ProductRepository.java          # Interfaz del repositorio
│   │   ├── application/
│   │   │   └── service/
│   │   │       └── ProductService.java             # Servicio de aplicación
│   │   └── infrastructure/
│   │       ├── persistence/
│   │       │   ├── ProductEntity.java              # Entidad JPA
│   │       │   ├── SpringDataProductRepository.java # Repositorio Spring Data
│   │       │   └── ProductRepositoryImpl.java      # Implementación del puerto
│   │       └── controller/
│   │           └── ProductController.java         # Controlador REST
│   └── resources/
│       └── application.properties                 # Configuración de la aplicación
└── test/
    └── java/com/example/app/
        ├── application/
        │   └── service/
        │       └── ProductServiceTest.java        # Tests del servicio
        ├── infrastructure/
        │   ├── persistence/
        │   │   └── ProductRepositoryImplTest.java  # Tests del repositorio
        │   └── controller/
        │       └── ProductControllerTest.java      # Tests del controlador
        └── domain/
            └── model/
                └── ProductTest.java                # Tests de la entidad
```

## Requisitos Previos

- Java 17 o superior
- Maven 3.6 o superior
- IDE (IntelliJ IDEA, Eclipse, VS Code, etc.)

## Instrucciones de Ejecución

### 1. Clonar el repositorio (si aplica)
```bash
git clone <repository-url>
cd app-example
```

### 2. Compilar el proyecto
```bash
mvn clean compile
```

### 3. Ejecutar los tests
```bash
mvn test
```

### 4. Ejecutar la aplicación
```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

### 5. (Alternativa) Crear el JAR y ejecutarlo
```bash
mvn clean package
java -jar target/app-example-1.0.0.jar
```

## API Endpoints

La API expone los siguientes endpoints para la gestión de productos:

### Crear Producto
- **POST** `/api/products`
- **Body**:
```json
{
  "name": "Nombre del producto",
  "description": "Descripción del producto",
  "price": 19.99,
  "stock": 10
}
```

### Obtener Producto por ID
- **GET** `/api/products/{id}`

### Obtener Todos los Productos
- **GET** `/api/products`

### Actualizar Producto
- **PUT** `/api/products/{id}`
- **Body**:
```json
{
  "name": "Nombre actualizado",
  "description": "Descripción actualizada",
  "price": 25.99,
  "stock": 15
}
```

### Eliminar Producto
- **DELETE** `/api/products/{id}`

## Base de Datos

El proyecto utiliza H2 Database en memoria para simplificar el desarrollo y testing. La consola de H2 está disponible en:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuario: `sa`
- Contraseña: (vacía)

## Tests Unitarios

El proyecto incluye tests unitarios para todas las capas:

- **ProductTest**: Tests de la entidad de dominio
- **ProductServiceTest**: Tests del servicio de aplicación con Mockito
- **ProductRepositoryImplTest**: Tests de la implementación del repositorio
- **ProductControllerTest**: Tests del controlador REST con MockMvc

Para ejecutar todos los tests:
```bash
mvn test
```

Para ejecutar una clase de test específica:
```bash
mvn test -Dtest=ProductServiceTest
```

## Tecnologías Utilizadas

- **Spring Boot 3.2.0**: Framework principal
- **Spring Data JPA**: Para la persistencia de datos
- **H2 Database**: Base de datos en memoria
- **Maven**: Gestión de dependencias y build
- **JUnit 5**: Framework de testing
- **Mockito**: Framework de mocking para tests

## Configuración

La configuración principal se encuentra en `src/main/resources/application.properties`:

- Puerto del servidor: 8080
- Base de datos: H2 en memoria
- DDL Auto: create-drop (recrea las tablas al iniciar)
- Consola H2: habilitada para desarrollo

## Próximos Pasos Sugeridos

- Integrar una base de datos real (PostgreSQL, MySQL)
- Agregar validación de datos (@Valid, @NotNull, etc.)
- Implementar paginación y filtrado en los endpoints
- Agregar documentación de API con Swagger/OpenAPI
- Implementar autenticación y autorización
- Agregar integración con Docker
