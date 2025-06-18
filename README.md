
# 🧾 Engineering Requirements Document (ERD)

**Project:** Public Survey Platform
**Backend Stack:** Spring Boot 3.5.0 + MongoDB + JWT
**Language:** Java 21
**Build Tool:** Gradle (Kotlin DSL)
**Deployment Target:** Heroku / Render / Railway

---

## 0. 📦 Spring Boot Project Metadata

| Setting                 | Value                          |
| ----------------------- | ------------------------------ |
| **Spring Boot Version** | `3.5.0`                        |
| **Build Tool**          | `Gradle - Kotlin DSL`          |
| **Language**            | `Java`                         |
| **Java Version**        | `21`                           |
| **Group**               | `com.katadavivienda`           |
| **Artifact**            | `encuestas`                    |
| **Name**                | `encuestas`                    |
| **Description**         | `kata saas encuestas`          |
| **Package Name**        | `com.katadavivienda.encuestas` |
| **Packaging**           | `Jar`                          |

---

## 1. 🧠 Problem Statement

We need a backend that allows users to create surveys and share them publicly. Anyone (authenticated or not) can respond to a survey, providing basic identity data (email, firstname, lastname). The system must support customized surveys with a restricted question type set (age, rating, text). Responses should be collected anonymously but attached to a survey. The platform should provide endpoints to manage surveys, users, and responses with optional JWT-based authentication.

---

## 2. 📦 MongoDB Data Models

2.1. User
Collection Name: users

{
"_id": ObjectId,
"email": "string",
"firstname": "string",
"lastname": "string",
"password": "hashed_string",
"company": "string"
}
2.2. Survey
Collection Name: surveys
{
"_id": ObjectId,
"userId": ObjectId,         // Creator user
"company": "string",
"title": "string",
"description": "string",
"questions": [
{
"questionId": "uuid",   // To match in responses
"questionText": "string",
"type": "age" | "rating" | "text",
"required": true
}
],
"createdAt": ISODate
}
2.3. Response
Collection Name: responses

{
"_id": ObjectId,
"surveyId": ObjectId,
"respondent": {
"email": "string",
"firstname": "string",
"lastname": "string"
},
"answers": [
{
"questionId": "uuid",
"answer": 25 | 4 | "text under 300 chars"
}
],
"submittedAt": ISODate
}


---

## 3. 🔐 JWT Authentication

* JWT required for:

    * User creation, survey creation, deletion, and listing
* JWT NOT required for:

    * User registration, login
    * Submitting survey responses (anonymous allowed)

---

## 4. 🌐 API Endpoints

### Auth

| Method | Path             | Auth | Description                      |
| ------ | ---------------- | ---- | -------------------------------- |
| `POST` | `/auth/register` | ❌    | Register new user and return JWT |
| `POST` | `/auth/login`    | ❌    | Login and return JWT             |

---

### Survey Management

| Method   | Path                        | Auth | Description                                     |
| -------- | --------------------------- | ---- | ----------------------------------------------- |
| `POST`   | `/api/surveys`              | ✅    | Create a survey                                 |
| `GET`    | `/api/surveys`              | ✅    | List surveys by `company` or `userId` via query |
| `GET`    | `/api/surveys/{id}`         | ✅    | Get a survey by its ID                          |
| `DELETE` | `/api/surveys/{id}`         | ✅    | Delete a survey by ID                           |
| `DELETE` | `/api/surveys/{id}/cascade` | ✅    | Delete a survey and its responses               |

---

### Public Response Submission

| Method | Path                            | Auth | Description                       |
| ------ | ------------------------------- | ---- | --------------------------------- |
| `POST` | `/public/surveys/{id}/response` | ❌    | Publicly submit a survey response |

---

### Admin Utilities

| Method   | Path               | Auth | Description          |
| -------- | ------------------ | ---- | -------------------- |
| `DELETE` | `/admin/users`     | ✅    | Delete all users     |
| `DELETE` | `/admin/surveys`   | ✅    | Delete all surveys   |
| `DELETE` | `/admin/responses` | ✅    | Delete all responses |

---

### 5. Project Structure
```
encuestas/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/katadavivienda/encuestas/
│   │   │       ├── controller/
│   │   │       │   ├── AuthController.java
│   │   │       │   ├── SurveyController.java
│   │   │       │   ├── ResponseController.java
│   │   │       │   └── AdminController.java
│   │   │       ├── data/
│   │   │       │   ├── dto/
│   │   │       │   │   ├── UserDto.java
│   │   │       │   │   ├── TokenDto.java
│   │   │       │   │   ├── SurveyDto.java
│   │   │       │   │   └── ResponseDto.java
│   │   │       │   ├── entity/
│   │   │       │   │   ├── User.java
│   │   │       │   │   ├── Survey.java
│   │   │       │   │   └── Response.java
│   │   │       │   └── repository/
│   │   │       │       ├── UserRepository.java
│   │   │       │       ├── SurveyRepository.java
│   │   │       │       └── ResponseRepository.java
│   │   │       ├── service/
│   │   │       │   ├── UserService.java
│   │   │       │   ├── SurveyService.java
│   │   │       │   └── ResponseService.java
│   │   │       ├── util/
│   │   │       │   └── JwtUtil.java
│   │   │       ├── config/
│   │   │       │   └── SecurityConfig.java
│   │   │       └── Application.java
│   │   └── resources/
│   │       └── application.properties
├── .gitignore
├── build.gradle.kts
├── Procfile
└── README.md
```