# Student Management API

## Название проекта
**Student Management API** – REST-сервис для управления данными студентов с JWT-авторизацией.

## Описание предметной области
Приложение предназначено для автоматизации работы с данными студентов. Основная сущность – **Студент (Student)**, которая содержит следующую информацию:
- **id** – уникальный идентификатор (генерируется автоматически).
- **fullName** – полное имя (обязательное, 2–100 символов).
- **groupName** – название учебной группы (1–50 символов).
- **email** – электронная почта (обязательное, уникальное, валидный формат).
- **age** – возраст (обязательное, 16–100 лет).
- **active** – статус активности (обязательное, `true` – учится, `false` – не учится).

**Бизнес-правила**:
- Email должен быть уникальным среди всех студентов.
- Возраст строго от 16 до 100 лет.
- При создании или полном обновлении все поля обязательны.
- При частичном обновлении (PATCH) можно передать только изменяемые поля.

**Операции**:
- Получение списка всех студентов.
- Получение студента по ID.
- Создание нового студента.
- Полное обновление студента (PUT).
- Частичное обновление студента (PATCH).
- Удаление студента.

Все операции, кроме аутентификации, доступны только авторизованным пользователям. Авторизация реализована на основе **JWT** (JSON Web Token).

## Стек технологий
- **Java 21**
- **Spring Boot 3.5.x**
- **Spring Security** + **JWT** (jjwt 0.12.6)
- **Spring Data JPA** (Hibernate)
- **PostgreSQL 16 / 17**
- **Lombok**
- **MapStruct** (маппинг DTO ↔ Entity)
- **Jakarta Validation** (валидация)
- **SpringDoc OpenAPI** (Swagger UI)
- **Gradle** (сборка)

## Как запустить проект
1. **Клонирование репозитория**  
   ```bash
   git clone https://github.com/CereEvse/REST-API-Stud
   cd student-api
2. **Настройка базы данных**
Убедитесь, что PostgreSQL установлен и запущен.
В файле src/main/resources/application.properties укажите параметры подключения
3. **Сборка и запуск**
   ```bash
   ./gradlew clean build
   ./gradlew bootRun
- Приложение будет доступно по адресу: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

## Описание маршрутов API
| Метод   | URL                | Описание                      | Доступ        |
|---------|--------------------|-------------------------------|---------------|
| POST    | `/auth/login`      | Аутентификация, получение JWT | Публичный     |
| POST    | `/auth/logout`     | Выход (заглушка для JWT)      | Публичный     |
| GET     | `/students`        | Получить список студентов     | Только с JWT  |
| GET     | `/students/{id}`   | Получить студента по ID       | Только с JWT  |
| POST    | `/students`        | Создать нового студента       | Только с JWT  |
| PUT     | `/students/{id}`   | Полностью обновить студента   | Только с JWT  |
| PATCH   | `/students/{id}`   | Частично обновить студента    | Только с JWT  |
| DELETE  | `/students/{id}`   | Удалить студента              | Только с JWT  |


## Примеры запросов и ответов

### 1. Аутентификация
**Запрос:**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'
```
**Ответ (200 OK):**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDA5MDAwMCwiZXhwIjoxNzM0MDkzNjAwfQ.some-signature",
  "type": "Bearer"
}
```

### 2. Создание студента
**Запрос:**
```bash
curl -X POST http://localhost:8080/students \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "Иван Петров",
    "groupName": "ЦИС-11",
    "email": "ivan@example.com",
    "age": 20,
    "active": true
  }'
```
**Ответ (201 Created):**
```json
{
  "id": 1,
  "fullName": "Иван Петров",
  "groupName": "ЦИС-12",
  "email": "ivan@example.com",
  "age": 20,
  "active": true
}
```

### 3. Получение списка студентов
**Запрос:**
```bash
curl -X GET http://localhost:8080/students \
  -H "Authorization: Bearer <token>"
```
**Ответ (200 OK):**
```json
[
  {
    "id": 1,
    "fullName": "Иван Петров",
    "groupName": "ЦИС-12",
    "email": "ivan@example.com",
    "age": 20,
    "active": true
  }
]
```

### 4. Частичное обновление (PATCH)
**Запрос:**
```bash
curl -X PATCH http://localhost:8080/students/1 \
  -H "Authorization: Bearer <token>" \
  -H "Content-Type: application/json" \
  -d '{"age": 21}'
```
**Ответ (200 OK):**
```json
{
  "id": 1,
  "fullName": "Иван Петров",
  "groupName": "ЦИС-11",
  "email": "ivan@example.com",
  "age": 21,
  "active": true
}
```

### 5. Удаление студента
**Запрос:**
```bash
curl -X DELETE http://localhost:8080/students/1 \
  -H "Authorization: Bearer <token>"
```
**Ответ (204 No Content)** – тело отсутствует.

---

## Примеры ошибок

Все ошибки возвращаются в едином JSON-формате:
```json
{
  "timestamp": "2025-03-22T13:54:12",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/students"
}
```

### 401 Unauthorized – отсутствует или неверный токен
**Запрос без токена:**
```bash
curl -X GET http://localhost:8080/students
```
**Ответ:**
```json
{
  "timestamp": "2025-03-22T13:54:12",
  "status": 401,
  "error": "Unauthorized",
  "message": "Full authentication is required to access this resource",
  "path": "/students"
}
```

### 400 Bad Request – некорректный JSON или параметр
**Пример:** передача строки вместо числа в age.
```json
{
  "timestamp": "2025-03-22T13:55:10",
  "status": 400,
  "error": "Bad Request",
  "message": "JSON parse error: Cannot deserialize value...",
  "path": "/students"
}
```

### 404 Not Found – студент не найден
```json
{
  "timestamp": "2025-03-22T13:56:05",
  "status": 404,
  "error": "Not Found",
  "message": "Student not found with id: 999",
  "path": "/students/999"
}
```

### 422 Unprocessable Entity – бизнес-валидация
**Пример:** попытка создать студента с уже существующим email.
```json
{
  "timestamp": "2025-03-22T13:57:20",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Email already exists: ivan@example.com",
  "path": "/students"
}
```
**Пример:** неверный возраст.
```json
{
  "timestamp": "2025-03-22T13:58:00",
  "status": 422,
  "error": "Unprocessable Entity",
  "message": "Age must be at least 16",
  "path": "/students"
}
```

### 405 Method Not Allowed – неподдерживаемый метод для ресурса
```json
{
  "timestamp": "2025-03-22T13:59:15",
  "status": 405,
  "error": "Method Not Allowed",
  "message": "Request method 'DELETE' is not supported",
  "path": "/students"
}
```

### 500 Internal Server Error – непредвиденная ошибка сервера
```json
{
  "timestamp": "2025-03-22T14:00:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/students"
}
```


## Описание структуры проекта
```bash
src/main/java/com/example/studentapi/
├── config/                     # Конфигурации Spring Security, JWT, OpenAPI
│   ├── SecurityConfig.java
│   ├── JwtAuthenticationFilter.java
│   ├── JwtUtils.java
│   ├── JacksonConfig.java
│   └── OpenApiConfig.java
├── controller/                 # REST-контроллеры
│   ├── AuthController.java
│   └── StudentController.java
├── dto/                        # Data Transfer Objects
│   ├── request/                # Входящие DTO (AuthRequest, StudentCreateRequest и др.)
│   ├── response/               # Исходящие DTO (AuthResponse, StudentResponse, ErrorResponse)
│   └── mapper/                 # MapStruct мапперы
├── entity/                     # JPA-сущности (User, Student)
├── repository/                 # Spring Data JPA репозитории
├── service/                    # Бизнес-логика (интерфейсы и реализации)
├── security/                   # UserDetailsService
├── exception/                  # Кастомные исключения и глобальный обработчик ошибок
└── StudentApiApplication.java  # Точка входа

src/main/resources/
├── application.properties      # Настройки приложения (БД, JWT, логирование)
└──  schema.sql                  # Скрипт создания таблиц

