# Franquicia API

## Descripción
API REST para gestionar franquicias, sucursales y productos con stock. Permite crear y administrar franquicias, gestionar sucursales, y mantener un control de inventario de productos.

## Tecnologías
- Java 17
- Spring Boot 3
- MySQL 8
- Docker & Docker Compose
- Maven

## Requisitos previos
- Java 17 o superior
- Maven (opcional si usa el wrapper)
- Docker y Docker Compose (opcional)
- MySQL 8 (si no usa Docker)

## Configuración

### Usando Docker
1. Copiar el archivo de variables de entorno:
   ```bash
   cp .env_example .env
   ```
2. Ejecutar con Docker Compose:
   ```bash
   docker-compose up --build -d
   ```

### Ejecución local sin Docker
1. Crear base de datos MySQL
2. Copiar y configurar variables de entorno:
   ```bash
   cp .env_example .env
   # Editar .env con los datos de conexión a tu base de datos
   ```
3. Ejecutar la aplicación:
   ```bash
   ./mvnw clean package
   java -jar target/*.jar
   ```

### Despliegue en AWS con Terraform

#### Requisitos
- [Terraform](https://www.terraform.io/downloads.html) instalado
- [AWS CLI](https://aws.amazon.com/cli/) instalado y configurado
- Cuenta de AWS con permisos necesarios

#### Variables de Terraform
Crear un archivo `terraform.tfvars`:
```hcl
project_name = "franchise-api"
db_name      = "franchise"
db_username  = "franchise"
db_password  = "tu-contraseña-segura"
```

#### Infraestructura provisionada
- Base de datos MySQL RDS
  - Instance class: db.t3.micro
  - Engine: MySQL 8.0
  - Storage: 20GB
  - Subnet group privado
  - Security group configurado

#### Comandos de Terraform

1. Inicializar Terraform:
   ```bash
   terraform init
   ```

2. Ver plan de ejecución:
   ```bash
   terraform plan
   ```

3. Aplicar cambios:
   ```bash
   terraform apply
   ```

4. Para destruir la infraestructura:
   ```bash
   terraform destroy
   ```

#### Variables de entorno para RDS
Después del despliegue, actualizar el archivo `.env` con los datos de conexión a RDS:
```bash
SPRING_DATASOURCE_URL=jdbc:mysql://<rds-endpoint>:3306/franchise
MYSQL_USER=franchise
MYSQL_PASSWORD=<tu-contraseña-segura>
MYSQL_DATABASE=franchise
```

## Pruebas
Ejecutar las pruebas unitarias:
```bash
./mvnw test
```

## API Endpoints

Base URL: `http://localhost:8080`

### Franquicias

> **Nota**: Todos los ejemplos asumen que la API está corriendo en localhost en el puerto 8080. 
> Ajusta la URL base según tu entorno de despliegue.

#### Crear Franquicia
- **URL**: `POST http://localhost:8080/api/franchises`
- **cURL**:
  ```bash
  curl -X POST http://localhost:8080/api/franchises \
       -H "Content-Type: application/json" \
       -d '{"name": "Nombre Franquicia"}'
  ```
- **Body**:
  ```json
  {
    "name": "Nombre Franquicia"
  }
  ```
- **Respuesta Exitosa**: 
  ```json
  {
    "id": 1,
    "name": "Nombre Franquicia",
    "branches": []
  }
  ```
- **Códigos de Estado**:
  - `200 OK`: Franquicia creada exitosamente
  - `400 Bad Request`: Datos inválidos

#### Actualizar Nombre de Franquicia
- **URL**: `PUT http://localhost:8080/api/franchises/{franchiseId}`
- **cURL**:
  ```bash
  curl -X PUT http://localhost:8080/api/franchises/1 \
       -H "Content-Type: application/json" \
       -d '{"name": "Nuevo Nombre"}'
  ```
- **Parámetros URL**: 
  - `franchiseId`: ID de la franquicia
- **Body**:
  ```json
  {
    "name": "Nuevo Nombre"
  }
  ```
- **Códigos de Estado**:
  - `204 No Content`: Actualización exitosa
  - `404 Not Found`: Franquicia no encontrada

### Sucursales

#### Crear Sucursal
- **URL**: `POST http://localhost:8080/api/franchises/{franchiseId}/branches`
- **cURL**:
  ```bash
  curl -X POST http://localhost:8080/api/franchises/1/branches \
       -H "Content-Type: application/json" \
       -d '{"name": "Nombre Sucursal"}'
  ```
- **Parámetros URL**: 
  - `franchiseId`: ID de la franquicia
- **Body**:
  ```json
  {
    "name": "Nombre Sucursal"
  }
  ```
- **Respuesta Exitosa**:
  ```json
  {
    "id": 1,
    "name": "Nombre Franquicia",
    "branches": [
      {
        "id": 1,
        "name": "Nombre Sucursal",
        "products": []
      }
    ]
  }
  ```
- **Códigos de Estado**:
  - `200 OK`: Sucursal creada exitosamente
  - `404 Not Found`: Franquicia no encontrada

#### Actualizar Nombre de Sucursal
- **URL**: `PUT http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}`
- **cURL**:
  ```bash
  curl -X PUT http://localhost:8080/api/franchises/1/branches/1 \
       -H "Content-Type: application/json" \
       -d '{"name": "Nuevo Nombre Sucursal"}'
  ```
- **Parámetros URL**:
  - `franchiseId`: ID de la franquicia
  - `branchId`: ID de la sucursal
- **Body**:
  ```json
  {
    "name": "Nuevo Nombre Sucursal"
  }
  ```
- **Códigos de Estado**:
  - `204 No Content`: Actualización exitosa
  - `404 Not Found`: Franquicia o sucursal no encontrada

### Productos

#### Agregar Producto
- **URL**: `POST http://localhost:8080/api/franchises/{franchiseId}/{branchId}/product`
- **cURL**:
  ```bash
  curl -X POST http://localhost:8080/api/franchises/1/1/product \
       -H "Content-Type: application/json" \
       -d '{"name": "Nombre Producto", "stock": 100}'
  ```
- **Parámetros URL**:
  - `franchiseId`: ID de la franquicia
  - `branchId`: ID de la sucursal
- **Body**:
  ```json
  {
    "name": "Nombre Producto",
    "stock": 100
  }
  ```
- **Respuesta Exitosa**:
  ```json
  {
    "id": 1,
    "name": "Nombre Franquicia",
    "branches": [
      {
        "id": 1,
        "name": "Nombre Sucursal",
        "products": [
          {
            "id": 1,
            "name": "Nombre Producto",
            "stock": 100
          }
        ]
      }
    ]
  }
  ```
- **Códigos de Estado**:
  - `200 OK`: Producto agregado exitosamente
  - `404 Not Found`: Franquicia o sucursal no encontrada

#### Actualizar Stock
- **URL**: `PUT http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock`
- **cURL**:
  ```bash
  curl -X PUT http://localhost:8080/api/franchises/1/branches/1/products/1/stock \
       -H "Content-Type: application/json" \
       -d '{"stock": 150}'
  ```
- **Parámetros URL**:
  - `franchiseId`: ID de la franquicia
  - `branchId`: ID de la sucursal
  - `productId`: ID del producto
- **Body**:
  ```json
  {
    "stock": 150
  }
  ```
- **Códigos de Estado**:
  - `204 No Content`: Stock actualizado exitosamente
  - `404 Not Found`: Franquicia, sucursal o producto no encontrado

#### Eliminar Producto
- **URL**: `DELETE http://localhost:8080/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}`
- **cURL**:
  ```bash
  curl -X DELETE http://localhost:8080/api/franchises/1/branches/1/products/1
  ```
- **Parámetros URL**:
  - `franchiseId`: ID de la franquicia
  - `branchId`: ID de la sucursal
  - `productId`: ID del producto
- **Códigos de Estado**:
  - `204 No Content`: Producto eliminado exitosamente
  - `404 Not Found`: Franquicia, sucursal o producto no encontrado

#### Obtener Productos con Mayor Stock
- **URL**: `GET http://localhost:8080/api/franchises/{franchiseId}/branches/top-products`
- **cURL**:
  ```bash
  curl http://localhost:8080/api/franchises/1/branches/top-products
  ```
- **Parámetros URL**:
  - `franchiseId`: ID de la franquicia
- **Respuesta Exitosa**:
  ```json
  [
    {
      "productName": "Nombre Producto",
      "stock": 150
    }
  ]
  ```
- **Códigos de Estado**:
  - `200 OK`: Lista de productos obtenida exitosamente
  - `404 Not Found`: Franquicia no encontrada

## Desarrollo

### Estructura del proyecto
```
src/
├── main/
│   ├── java/
│   │   └── com/saulmoralespa/franchiseapi/
│   │       ├── controller/    # Controladores REST
│   │       ├── dto/          # Objetos de transferencia de datos
│   │       ├── model/        # Entidades JPA
│   │       ├── repository/   # Repositorios JPA
│   │       └── service/      # Lógica de negocio
│   └── resources/
│       └── application.properties
└── test/                    # Pruebas unitarias
```