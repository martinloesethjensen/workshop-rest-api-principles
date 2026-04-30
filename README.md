# [Prosa WORKSHOP] TODO API — Workshop Starter

> [!NOTE] 
> This is my implementation of the [REST API Principles workshop](https://github.com/codeclubcph/rest-api-principles), run by [Prosa (the Danish IT union)](https://www.prosa.dk/) in April 2026. 
> The workshop covers building a production-style CRUD REST API with Java, Spring Boot, and PostgreSQL.

> A hands-on exercise to build a production-style REST API with Spring Boot 4 and Gradle.

---

## What you'll build

A fully functional **TODO List REST API** with:

- Full CRUD — Create, Read, Update, Delete
- Filtering by status (`OPEN` / `IN_PROGRESS` / `DONE`)
- Input validation with Bean Validation
- Structured JSON error responses
- Correct HTTP status codes and `Location` headers

**Stack:** Java 25 · Spring Boot 4.0.6 · Spring Data JPA · PostgreSQL · Lombok · Gradle

---

## Getting started

### Prerequisites

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (recommended — no local Java install needed)
- **or** Java 25 — [download Temurin](https://adoptium.net) for running without Docker
- IntelliJ IDEA (recommended) or VS Code with Java extensions
- Postman or curl for testing

> **IntelliJ users:** enable annotation processing for Lombok
> Settings → Build → Compiler → Annotation Processors → ✅ Enable annotation processing

### Option A — Docker Compose (recommended)

No local Java or database setup required.

```bash
git clone https://github.com/codeclubcph/rest-api-principles.git
cd rest-api-principles
docker compose up --build
```

The app starts on **http://localhost:8080**.
Verify: `curl http://localhost:8080/api/v1/todos` → should return `[]`

> **After editing code**, rebuild with `docker compose up --build` to pick up your changes.

### Option B — Local with Gradle

Requires Java 25 and a running PostgreSQL instance.
The app reads datasource config from environment variables (see `application.properties` for defaults).

```bash
./gradlew bootRun
```

---

## Project structure

```
src/main/java/com/prosa/workshop/rest/
├── RestApplication.java             ✅ provided
├── hello/
│   └── HelloController.java         ✅ provided  GET / → health check
└── todo/
    ├── controller/
    │   └── TodoController.java          ✏️  YOU IMPLEMENT  (TODOs A–F)
    ├── service/
    │   └── TodoService.java             ✏️  YOU IMPLEMENT  (TODOs 1–6)
    ├── repository/
    │   └── TodoRepository.java          ✅ provided
    ├── model/
    │   ├── Todo.java                    ✅ provided
    │   └── TodoStatus.java              ✅ provided
    ├── dto/
    │   ├── TodoDto.java                 ✅ provided
    │   ├── CreateTodoRequest.java       ✅ provided
    │   └── UpdateTodoRequest.java       ✅ provided
    └── exception/
        ├── ResourceNotFoundException.java   ✅ provided
        ├── ErrorResponse.java               ✅ provided
        └── GlobalExceptionHandler.java      ✏️  YOU IMPLEMENT  (TODOs G–I)
```

---

## Your tasks

Work layer by layer — service first, then controller, then exception handler.

### Service layer (`TodoService.java`)

| TODO | Method | What to do |
|------|--------|-----------|
| 1 | `findAll(String status)` | Return all todos; filter by status if provided |
| 2 | `findById(Long id)` | Return single todo or throw `ResourceNotFoundException` |
| 3 | `create(CreateTodoRequest)` | Build entity, save, return DTO |
| 4 | `update(Long id, UpdateTodoRequest)` | Apply non-null fields, save, return DTO |
| 5 | `updateStatus(Long id, TodoStatus)` | Update status field only |
| 6 | `delete(Long id)` | Throw 404 if missing, then delete |

### Controller layer (`TodoController.java`)

| TODO | Endpoint | Expected response |
|------|----------|------------------|
| A | `GET /api/v1/todos` | `200 OK` — list (supports `?status=`) |
| B | `GET /api/v1/todos/{id}` | `200 OK` or `404` |
| C | `POST /api/v1/todos` | `201 Created` + `Location` header |
| D | `PUT /api/v1/todos/{id}` | `200 OK` or `404` |
| E | `PATCH /api/v1/todos/{id}/status` | `200 OK` or `404` |
| F | `DELETE /api/v1/todos/{id}` | `204 No Content` or `404` |

### Exception handler (`GlobalExceptionHandler.java`)

| TODO | Exception | HTTP response |
|------|-----------|--------------|
| G | `ResourceNotFoundException` | `404 NOT_FOUND` |
| H | `MethodArgumentNotValidException` | `400 VALIDATION_ERROR` |
| H | `HttpMessageNotReadableException` | `400 VALIDATION_ERROR` (empty/malformed body) |
| I | `Exception` (catch-all) | `500 INTERNAL_ERROR` |

---

## Testing your API

```bash
# Create a todo
curl -X POST http://localhost:8080/api/v1/todos \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn REST"}' -v

# Get all todos
curl http://localhost:8080/api/v1/todos

# Filter by status
curl "http://localhost:8080/api/v1/todos?status=OPEN"

# Get by id
curl http://localhost:8080/api/v1/todos/1

# Update status
curl -X PATCH "http://localhost:8080/api/v1/todos/1/status?status=IN_PROGRESS"

# Full update
curl -X PUT http://localhost:8080/api/v1/todos/1 \
  -H "Content-Type: application/json" \
  -d '{"title": "Learn REST APIs", "status": "DONE"}'

# Delete
curl -X DELETE http://localhost:8080/api/v1/todos/1 -v

# Test 404 error
curl http://localhost:8080/api/v1/todos/999

# Test validation error
curl -X POST http://localhost:8080/api/v1/todos \
  -H "Content-Type: application/json" \
  -d '{"title": ""}'
```

---

## Running the tests

```bash
./gradlew test
```

Unit tests for `TodoService` are in `src/test/java/com/prosa/workshop/rest/todo/TodoServiceTest.java`.
They will fail until you implement the service methods — that's expected!

---

## Bonus challenges

Once you have all endpoints working:

1. **Pagination** — replace `List<TodoDto>` with `Page<TodoDto>`, add `Pageable` to the controller, test with `?page=0&size=5&sort=createdAt,desc`
2. **Search** — add `?search=keyword` filtering with `findByTitleContainingIgnoreCase`
3. **Swagger UI** — add `org.springdoc:springdoc-openapi-starter-webmvc-ui` to `build.gradle`, visit `/swagger-ui.html`
4. **Integration tests** — write `@WebMvcTest(TodoController.class)` tests verifying status codes, the `Location` header on POST, and validation error format

---

## Useful Gradle commands

```bash
./gradlew bootRun          # Start the application
./gradlew build            # Compile + test + build JAR
./gradlew test             # Run tests only
./gradlew dependencies     # Print dependency tree
./gradlew clean            # Clean build outputs
```

---

*Built with love by [The Better Software Initiative](https://bettersoftwareinitiative.com)*
