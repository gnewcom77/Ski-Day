# 🏔️ Ski Day — Spring Boot + MySQL REST API

A clean, fully functional REST API to log **ski and snowboard days** for both **resort** and **backcountry** outings.  
Built with **Java 17 / Spring Boot 3 / MySQL 8**, verified with **ARC**, and seeded automatically via `data.sql`.

---

## 🚀 Overview
Ski Day lets users record and query their ski sessions with proper validation for:
- Season and date alignment (e.g., *“2024/25” = Nov 1 2024 – Apr 30 2025*)
- Day Type enum: **RESORT** or **BACKCOUNTRY**
- Linked entities for **User ↔ Ski Session ↔ Resort**

Each app start automatically resets and seeds sample data for easy testing.

---

## 🧩 Tech Stack
| Layer | Technology |
|-------|-------------|
| Backend | Java 17, Spring Boot 3.5 |
| ORM | Spring Data JPA / Hibernate |
| Database | MySQL 8 |
| Build Tool | Maven |
| Testing / API | ARC / Postman |
| IDE | Eclipse / IntelliJ |

---

## ⚙️ How to Run Locally
1. **Create the database**
   ```sql
   CREATE DATABASE skiday CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **Configure credentials**  
   Copy the example file and update with your MySQL info:  
   `src/main/resources/application-example.yaml → application.yaml`

3. **Run the app**
   ```bash
   ./mvnw spring-boot:run
   ```
   or start from your IDE.

4. **Verify seed data**
   ```
   GET http://localhost:8080/users
   GET http://localhost:8080/resorts
   GET http://localhost:8080/sessions
   ```

---

## 🧠 Entity Model
```
User ───< SkiSession >─── Resort
```
**User**
- id (Long)  
- name (String)

**Resort**
- id (Long)  
- name (String)  
- region (String)  
- state (String)

**SkiSession**
- id (Long)  
- user (User FK)  
- resort (Resort FK, nullable for BACKCOUNTRY)  
- date (LocalDate)  
- season (String)  
- type (Enum: RESORT / BACKCOUNTRY)  
- conditions, notes, metrics (ascent, descent, miles, elapsedMinutes…)

---

## 🔗 Endpoints Summary

| Method | Endpoint | Description |
|:--|:--|:--|
| **GET** | `/users` | List all users |
| **POST** | `/users` | Create user |
| **PUT** | `/users/{id}` | Update user |
| **DELETE** | `/users/{id}` | Delete user |
| **GET** | `/resorts` | List resorts |
| **POST** | `/resorts` | Create resort |
| **PUT** | `/resorts/{id}` | Update resort |
| **DELETE** | `/resorts/{id}` | Delete resort |
| **GET** | `/sessions` | List all sessions |
| **GET** | `/sessions/{id}` | Get one session |
| **GET** | `/sessions/season?season=2024/25` | Filter by season |
| **GET** | `/sessions/season?season=2024/25&type=RESORT` | Filter by season + type |
| **POST** | `/sessions` | Add new ski session |
| **PUT** | `/sessions/{id}` | Update existing session |
| **DELETE** | `/sessions/{id}` | Delete session |

---

## 🧪 Example Requests

**POST /sessions – RESORT day**
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

**POST /sessions – BACKCOUNTRY day**
```json
{
  "userId": 2,
  "season": "2024/25",
  "date": "2025-03-01",
  "type": "BACKCOUNTRY",
  "region": "Hyalite",
  "conditions": "wind buff",
  "notes": "short tour"
}
```

**Response**
```json
{
  "id": 8,
  "user": {"id":1,"name":"Garrett"},
  "type": "RESORT",
  "resort": {"id":2,"name":"Jackson Hole"},
  "season": "2024/25",
  "date": "2025-03-10",
  "conditions": "soft groomers",
  "notes": "after work laps"
}
```

---

## 🧾 Dev Notes
- `data.sql` resets tables and seeds baseline users, resorts, and sessions each startup.  
- `application.yaml` is ignored in Git (.gitignore) to protect credentials.  
- The repo includes an `application-example.yaml` template for local setup.  
- All CRUD routes validated and tested via ARC (expected codes: 200/201/204).

---

## 📁 File Structure
```
src/main/java/skiday
 ├── controller
 ├── dto
 ├── entity
 ├── enums
 ├── repository
 └── util
src/main/resources
 ├── application.yaml
 └── data.sql
```

---

## 🧰 Next Steps / Ideas
- Add authentication (Spring Security + JWT)
- Deploy on Render or Fly.io for public demo
- Build a small React or JavaFX frontend to track ski days visually
- Extend model for weather, gear logs, or photos

---

## 🪪 License
MIT License — free for personal and educational use.

---

## 💼 LinkedIn / Resume Summary
- Developed a **Spring Boot REST API** to record and query ski sessions with validation and enum logic.  
- Built a relational schema linking **Users**, **Resorts**, and **Sessions** in **MySQL**.  
- Verified endpoints via **ARC** and documented with sample requests for fast onboarding.

**GitHub:** <<EDIT: ADD YOUR GITHUB REPO LINK HERE>>
**Dates:** <<EDIT: ENTER PROJECT DATES (e.g., JAN–FEB 2025)>>
**Author:** <<EDIT: GARRETT NEWCOMER>>

---
