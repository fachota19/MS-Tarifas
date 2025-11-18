# 📦 Microservicio de Tarifas

<div align="center">

**Proyecto TPI – UTN FRC – Grupo 114**

[![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen?style=for-the-badge&logo=spring)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?style=for-the-badge&logo=postgresql)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Enabled-2496ED?style=for-the-badge&logo=docker)](https://www.docker.com/)
[![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apache-maven)](https://maven.apache.org/)

*Sistema de gestión de tarifas con cálculo automatizado de costos*

</div>

---

## 📋 Tabla de Contenidos

- [Descripción](#-descripción)
- [Tecnologías](#-tecnologías)
- [Modelo de Datos](#-modelo-de-datos)
- [Endpoints API](#-endpoints-api)
- [Inicio Rápido](#-inicio-rápido)
- [Configuración de pgAdmin](#-configuración-de-pgadmin)
- [Ejemplos de Uso](#-ejemplos-de-uso)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Configuración por Entorno](#-configuración-por-entorno)
- [Características Técnicas](#-características-técnicas)
- [Estado del Proyecto](#-estado-del-proyecto)
- [Equipo](#-equipo)

---

## 🎯 Descripción

Microservicio especializado en la gestión integral de tarifas y sus componentes asociados. Proporciona funcionalidades completas para:

- ✅ Gestión CRUD de tarifas con vigencia temporal
- ✅ Administración de detalles y conceptos tarifarios
- ✅ Activación/desactivación dinámica de tarifas
- ✅ Cálculo automatizado de costos por unidades
- ✅ Persistencia robusta con PostgreSQL
- ✅ Datos de prueba precargados para testing inmediato

---

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.2.x | Framework backend |
| Spring Web | - | API REST |
| Spring Data JPA | - | ORM y persistencia |
| Hibernate | 6 | Motor JPA |
| PostgreSQL | 15 | Base de datos |
| Docker Compose | - | Orquestación |
| pgAdmin | 4 | Gestión de BD |
| Maven | - | Gestión de dependencias |

---

## 🗄️ Modelo de Datos

### Tabla: `tarifas`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único (PK) |
| `nombre` | VARCHAR(100) | Nombre descriptivo de la tarifa |
| `descripcion` | VARCHAR(255) | Información adicional |
| `fecha_inicio` | DATE | Fecha de inicio de vigencia |
| `fecha_fin` | DATE | Fecha de fin de vigencia |
| `activa` | BOOLEAN | Estado activo/inactivo |

### Tabla: `detalles_tarifa`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| `id` | BIGSERIAL | Identificador único (PK) |
| `concepto` | VARCHAR(100) | Nombre del concepto (ej: "Km recorrido") |
| `unidad` | VARCHAR(50) | Unidad de medida (ej: "km", "hora") |
| `valor` | NUMERIC(12,2) | Valor unitario |
| `tarifa_id` | BIGINT (FK) | Referencia a tarifa padre |

**Relación:** Una tarifa puede tener múltiples detalles (1:N con cascada)

---

## 🔌 Endpoints API

### 📊 Tarifas

#### Listar todas las tarifas
```http
GET /tarifas
```

#### Obtener tarifa con detalles
```http
GET /tarifas/{id}
```

#### Crear nueva tarifa
```http
POST /tarifas
Content-Type: application/json

{
  "nombre": "Tarifa Premium",
  "descripcion": "Tarifa para servicios express",
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-12-31",
  "activa": true
}
```

#### Activar tarifa
```http
PUT /tarifas/{id}/activar
```

#### Desactivar tarifa
```http
PUT /tarifas/{id}/desactivar
```

#### Eliminar tarifa
```http
DELETE /tarifas/{id}
```
> ⚠️ Elimina también todos los detalles asociados (cascada)

---

### 📝 Detalles de Tarifa

#### Crear detalle
```http
POST /tarifas/{id}/detalles
Content-Type: application/json

{
  "concepto": "Km recorrido",
  "unidad": "km",
  "valor": 50.5
}
```

#### Eliminar detalle
```http
DELETE /tarifas/{tarifaId}/detalles/{detalleId}
```

---

### 🧮 Cálculo de Costos

#### Calcular costo estimado
```http
POST /tarifas/calcular
Content-Type: application/json

{
  "tarifaId": 1,
  "unidad": "km",
  "cantidad": 100
}
```

**Respuesta:**
```json
{
  "tarifaId": 1,
  "unidad": "km",
  "cantidad": 100,
  "costoTotal": 5050.00
}
```

---

## 🚀 Inicio Rápido

### Prerequisitos

- Docker y Docker Compose instalados
- Maven 3.6+ (para compilación local)
- Puerto 8083 (API), 5433 (PostgreSQL) y 5050 (pgAdmin) disponibles

### 1️⃣ Clonar el repositorio

```bash
git clone https://github.com/Grupo-114/TPI-Backend---Grupo-114.git
cd MS-Tarifas
```

### 2️⃣ Construir el JAR

```bash
mvn clean package
```

> 💡 Esto genera el archivo `.jar` necesario para Docker

### 3️⃣ Levantar los servicios

```bash
docker compose up --build
```

### 4️⃣ Verificar servicios activos

- 🚀 **API REST**: [http://localhost:8083](http://localhost:8083)
- 🗄️ **PostgreSQL**: `localhost:5433`
- 🖥️ **pgAdmin**: [http://localhost:5050](http://localhost:5050)

---

## 🗃️ Configuración de pgAdmin

### Acceso inicial

Navega a: **http://localhost:5050**

**Credenciales:**
- 📧 Email: `admin@admin.com`
- 🔑 Password: `admin`

### Crear servidor

1. Click en **Add New Server** (➕)
2. Completa los campos:

#### ⚙️ General
| Campo | Valor |
|-------|-------|
| Name | `db_tarifas` |

#### ⚙️ Connection
| Campo | Valor |
|-------|-------|
| Host name/address | `tarifas-db` |
| Port | `5432` |
| Maintenance database | `tarifasdb` |
| Username | `postgres` |
| Password | `admin` |
| Save password | ✅ |

3. Click en **Save**

✅ **Resultado esperado:** Servidor conectado con la base `tarifasdb` y datos precargados

---

## 📝 Ejemplos de Uso

### Caso 1: Crear tarifa completa

#### Paso 1: Crear la tarifa base
```json
POST http://localhost:8083/tarifas

{
  "nombre": "Tarifa Nocturna",
  "descripcion": "Tarifa para horario nocturno",
  "fechaInicio": "2025-01-01",
  "fechaFin": "2025-06-30",
  "activa": true
}
```

#### Paso 2: Agregar detalles
```json
POST http://localhost:8083/tarifas/1/detalles

{
  "concepto": "Kilómetro",
  "unidad": "km",
  "valor": 75.50
}
```

```json
POST http://localhost:8083/tarifas/1/detalles

{
  "concepto": "Tiempo de espera",
  "unidad": "min",
  "valor": 15.00
}
```

### Caso 2: Calcular costo de viaje

```json
POST http://localhost:8083/tarifas/calcular

{
  "tarifaId": 1,
  "unidad": "km",
  "cantidad": 50
}
```

**Resultado:** Costo total = 50 km × $75.50 = **$3,775.00**

### Caso 3: Desactivar tarifa obsoleta

```http
PUT http://localhost:8083/tarifas/1/desactivar
```

---

## 📁 Estructura del Proyecto

```
MS-Tarifas/
├── 📂 src/
│   └── 📂 main/
│       ├── 📂 java/ar/edu/utn/frc/backend/grupo114/tarifas/
│       │   ├── 📂 controller/         # Controladores REST
│       │   ├── 📂 dto/                # Data Transfer Objects
│       │   ├── 📂 model/              # Entidades JPA
│       │   ├── 📂 repository/         # Repositorios JPA
│       │   ├── 📂 service/            # Lógica de negocio
│       │   └── 📂 exception/          # Excepciones personalizadas
│       └── 📂 resources/
│           ├── application.properties             # Config local
│           ├── application-docker.properties      # Config Docker
│           └── data.sql                          # Datos iniciales
├── 📄 Dockerfile                      # Imagen del microservicio
├── 📄 docker-compose.yml             # Orquestación
├── 📄 pom.xml                         # Dependencias Maven
└── 📄 test.http                       # Colección de pruebas
```

---

## ⚙️ Configuración por Entorno

### 🏠 Entorno Local (IDE)

**Archivo:** `application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tarifasdb
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=update
```

### 🐳 Entorno Docker

**Archivo:** `application-docker.properties`

```properties
spring.datasource.url=jdbc:postgresql://tarifas-db:5432/tarifasdb
spring.datasource.username=postgres
spring.datasource.password=admin
spring.jpa.hibernate.ddl-auto=create
```

> 💡 El perfil se activa automáticamente en Docker

---

## 🔧 Características Técnicas

### Cascada de Borrado

Implementada en `Tarifa.java`:

```java
@OneToMany(mappedBy = "tarifa",
           cascade = CascadeType.ALL,
           orphanRemoval = true)
private List<DetalleTarifa> detalles = new ArrayList<>();
```

**Beneficios:**
- ✅ Eliminar tarifa → elimina automáticamente todos sus detalles
- ✅ Eliminar detalle → no quedan registros huérfanos
- ✅ Integridad referencial garantizada

### Algoritmo de Cálculo

```java
costoTotal = SUM(valor_detalle_por_unidad) × cantidad
```

**Características:**
- Usa `BigDecimal` para precisión decimal
- Filtra solo detalles con la unidad especificada
- Manejo de errores para tarifas inexistentes

### Manejo de Errores

API REST consistente con códigos HTTP estándar:

| Código | Significado | Ejemplo |
|--------|-------------|---------|
| `200` | OK | Operación exitosa |
| `201` | Created | Recurso creado |
| `404` | Not Found | Tarifa no encontrada |
| `400` | Bad Request | Datos inválidos |
| `500` | Server Error | Error interno |

---

## 🧪 Testing con test.http

El archivo `test.http` incluye escenarios completos:

- ✅ CRUD de tarifas
- ✅ CRUD de detalles
- ✅ Activación/desactivación
- ✅ Cálculos con diferentes unidades
- ✅ Casos de error (IDs inexistentes, datos inválidos)

**Uso en VSCode:**
1. Instala la extensión "REST Client"
2. Abre `test.http`
3. Click en "Send Request" sobre cada endpoint

---

## 📊 Script Inicial – data.sql

Ubicado en `src/main/resources/data.sql`

Se ejecuta automáticamente al iniciar el microservicio en Docker.

**Contenido:**
- 🎯 3 tarifas de ejemplo (Básica, Premium, Express)
- 📝 Múltiples detalles (km, horas, peajes)
- ✅ Listo para testing inmediato

---

## 💡 Tips y Mejores Prácticas

### ✅ Fechas de Vigencia
- `fechaInicio` debe ser menor o igual a `fechaFin`
- Usa formato ISO: `YYYY-MM-DD`

### ✅ Unidades Consistentes
- Define unidades claras: "km", "hora", "min"
- Usa las mismas unidades en detalles y cálculos

### ✅ BigDecimal para Dinero
- El sistema usa `NUMERIC(12,2)` para precisión
- Evita errores de redondeo en cálculos financieros

### 🔄 Reset Completo

```bash
docker compose down -v
mvn clean package
docker compose up --build
```

---

## ✅ Estado del Proyecto

| Funcionalidad | Estado |
|---------------|--------|
| CRUD Tarifas | ✅ Operativo |
| CRUD Detalles | ✅ Operativo |
| Cálculo de Costos | ✅ Operativo |
| Integración Docker | ✅ Operativo |
| Datos Iniciales | ✅ Precargados |
| Mapeos JPA | ✅ Sin referencias circulares |
| Cascada de Borrado | ✅ Funcionando |
| pgAdmin | ✅ Configurado |
| API REST | ✅ Documentada |
| Testing | ✅ test.http incluido |

---

## 🔧 Solución de Problemas

### Puerto 5433 ocupado
```bash
# Cambiar el puerto en docker-compose.yml
ports:
  - "5434:5432"  # Usar 5434 en lugar de 5433
```

### JAR no se construye
```bash
# Limpiar cache de Maven
mvn clean
rm -rf target/
mvn package
```

### Datos no se cargan
1. Verifica que `data.sql` esté en `src/main/resources/`
2. Confirma que `spring.jpa.hibernate.ddl-auto=create` en Docker
3. Revisa logs: `docker compose logs ms-tarifas`

---

## 👥 Equipo

**Grupo 114 – UTN FRC**  
Microservicio de Tarifas – TPI Backend de Aplicaciones (2025)

---