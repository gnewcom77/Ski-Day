# Ski Day

This project is a simple Spring Boot REST API I built to log and track ski days.  
It lets you record information about users, resorts, and ski sessions, including the date, season, conditions, and whether it was a resort or backcountry day.

It was built mainly as a practice project to improve my understanding of REST APIs, MySQL databases, and how data flows between entities in Spring Boot.

---

## Overview

Ski Day has three main parts:
- **Users** – the people logging their ski sessions  
- **Resorts** – the locations where sessions happen  
- **Ski Sessions** – the actual logged days, connected to a user and (optionally) a resort

Each session includes fields for conditions, type (RESORT or BACKCOUNTRY), and notes.  
Basic validation checks make sure that sessions fall within the correct season (e.g., the 2024/25 season runs from November 1, 2024 through April 30, 2025).

---

## How to Run

1. Create a MySQL database called `skiday`
   ```sql
   CREATE DATABASE skiday CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. Copy the `application-example.yaml` file from  
   `src/main/resources/`  
   and rename it to `application.yaml`

3. Edit the new file with your own MySQL username and password.

4. Start the app:
   ```bash
   ./mvnw spring-boot:run
   ```
   or run it from your IDE.

5. Once running, test with:
   ```
   GET http://localhost:8080/users
   GET http://localhost:8080/resorts
   GET http://localhost:8080/sessions
   ```

---

## Example Request (POST)

**Add a new resort ski session**
```json
{
  "userId": 1,
  "resortId": 2,
  "season": "2024/25",
  "date": "2025-03-10",
  "type": "RESORT",
  "conditions": "soft groomers",
  "notes": "after work laps"
}
```

**Add a backcountry day**
```json
{
  "userId": 1,
  "season": "2024/25",
  "date": "2025-03-01",
  "type": "BACKCOUNTRY",
  "region": "Hyalite",
  "conditions": "powder",
  "notes": "Great tour with friends"
}
```

---

## API Endpoints

| Method | Endpoint | Description |
|:--|:--|:--|
| GET | `/users` | Get all users |
| POST | `/users` | Create a new user |
| PUT | `/users/{id}` | Update a user |
| DELETE | `/users/{id}` | Delete a user |
| GET | `/resorts` | Get all resorts |
| POST | `/resorts` | Create a new resort |
| PUT | `/resorts/{id}` | Update a resort |
| DELETE | `/resorts/{id}` | Delete a resort |
| GET | `/sessions` | Get all sessions |
| GET | `/sessions/{id}` | Get one session |
| POST | `/sessions` | Create a new session |
| PUT | `/sessions/{id}` | Update a session |
| DELETE | `/sessions/{id}` | Delete a session |

---

## Notes

- The `data.sql` file loads a few sample users, resorts, and sessions at startup.  
- The `application.yaml` file should never be uploaded to GitHub — credentials stay local.  
- `application-example.yaml` is included for reference.

---

## About

This project was built with:
- Java 17  
- Spring Boot 3  
- MySQL 8  
- Maven  

It’s a simple backend project meant to show solid REST API design and CRUD operations.  
The main goal was to get more comfortable with JPA, entity relationships, and validation in Spring.

---

## Author

**Garrett Newcomer**  
[<<ADD YOUR GITHUB LINK HERE>>](<<ADD YOUR GITHUB LINK HERE>>)