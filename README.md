# 🏥 HMS - Appointment Service
**Member 4 | Port: 8083**

This is the Appointment Service for the Hospital Management System microservices project.

---

## 📁 File Structure

```
appointment-service/
├── src/
│   └── main/
│       ├── java/com/hms/appointment/
│       │   ├── AppointmentServiceApplication.java   ← Main class (entry point)
│       │   ├── controller/
│       │   │   └── AppointmentController.java       ← API endpoints
│       │   ├── service/
│       │   │   └── AppointmentService.java          ← Business logic
│       │   ├── repository/
│       │   │   └── AppointmentRepository.java       ← Database queries
│       │   ├── model/
│       │   │   └── Appointment.java                 ← Database table
│       │   ├── dto/
│       │   │   └── AppointmentDTO.java              ← Request/Response shape
│       │   ├── client/
│       │   │   └── ExternalServiceClient.java       ← Calls other services
│       │   └── config/
│       │       ├── RestTemplateConfig.java          ← HTTP client setup
│       │       └── SwaggerConfig.java               ← Swagger docs setup
│       └── resources/
│           └── application.properties               ← ⚠️ PUT YOUR DB CREDENTIALS HERE
├── database/
│   └── schema.sql                                   ← DB reference (auto-created by Spring)
├── pom.xml                                          ← Dependencies
├── .gitignore
└── README.md
```

---

## 🚀 Step-by-Step Setup Guide (Beginner Friendly)

---

### STEP 1 — Create Your Neon.tech Database

1. Go to [neon.tech](https://neon.tech) and sign up (free)
2. Click **"New Project"**
3. Name it: `appointment-service-db`
4. Region: choose closest to Sri Lanka (e.g., Singapore)
5. Click **"Create Project"**
6. You will see a **Connection String** like:
   ```
   postgresql://username:password@ep-xxx-xxx.ap-southeast-1.aws.neon.tech/neondb?sslmode=require
   ```
7. Copy these values — you need them in Step 3

---

### STEP 2 — Create Your Spring Boot Project

**Option A: Use Spring Initializr (Recommended)**

1. Go to [start.spring.io](https://start.spring.io)
2. Fill in:
   - **Project:** Maven
   - **Language:** Java
   - **Spring Boot:** 3.2.3
   - **Group:** `com.hms`
   - **Artifact:** `appointment-service`
   - **Java:** 17
3. Add Dependencies:
   - ✅ Spring Web
   - ✅ Spring Data JPA
   - ✅ PostgreSQL Driver
   - ✅ Lombok
4. Click **"Generate"** → download ZIP
5. Extract the ZIP into your `appointment-service` GitHub repo folder

**Option B: Copy These Files**

Just copy ALL the code files from this repo into your project. Replace the generated files if needed.

---

### STEP 3 — Configure Your Database

Open `src/main/resources/application.properties` and replace the placeholder values:

```properties
# BEFORE (placeholder):
spring.datasource.url=jdbc:postgresql://ep-REPLACE-THIS.neon.tech/appointment_db?sslmode=require
spring.datasource.username=REPLACE_WITH_YOUR_USERNAME
spring.datasource.password=REPLACE_WITH_YOUR_PASSWORD

# AFTER (your actual values):
spring.datasource.url=jdbc:postgresql://ep-cool-sun-123456.ap-southeast-1.aws.neon.tech/neondb?sslmode=require
spring.datasource.username=neondb_owner
spring.datasource.password=abc123xyz456
```

> ⚠️ **Important:** The database name in the URL should be `neondb` (Neon's default) OR create a new database called `appointment_db` in Neon's SQL editor first:
> ```sql
> CREATE DATABASE appointment_db;
> ```

---

### STEP 4 — Open in IntelliJ IDEA

1. Open IntelliJ IDEA
2. **File → Open** → Select the `appointment-service` folder
3. Wait for Maven to download all dependencies (bottom bar will show progress)
4. Make sure Java 17 is configured: **File → Project Structure → SDK → Java 17**

---

### STEP 5 — Run the Application

1. Find `AppointmentServiceApplication.java` in the Project panel
2. Right-click → **Run 'AppointmentServiceApplication'**
3. Check the console — you should see:
   ```
   Started AppointmentServiceApplication in X.X seconds
   Hibernate: create table appointments (...)
   ```
4. If you see errors, check Step 3 (database credentials)

---

### STEP 6 — Test with Swagger UI

1. Open your browser
2. Go to: **http://localhost:8083/swagger-ui.html**
3. You should see the Swagger UI with all your endpoints!

---

### STEP 7 — Test Each Endpoint

**Create an appointment (POST /appointments):**
```json
{
  "patientId": 1,
  "patientName": "John Silva",
  "doctorId": 1,
  "doctorName": "Dr. Nimal Perera",
  "departmentId": 1,
  "departmentName": "Cardiology",
  "appointmentDate": "2025-04-10",
  "appointmentTime": "09:00:00",
  "status": "SCHEDULED",
  "reason": "Regular checkup"
}
```

**Get all appointments:**
- GET http://localhost:8083/appointments

**Get by patient:**
- GET http://localhost:8083/appointments/patient/1

**Get by doctor:**
- GET http://localhost:8083/appointments/doctor/1

**Update status to COMPLETED:**
```json
{
  "status": "COMPLETED"
}
```
- PUT http://localhost:8083/appointments/1

**Delete:**
- DELETE http://localhost:8083/appointments/1

---

### STEP 8 — Push to GitHub

```bash
cd appointment-service

git init           # (if not already a git repo)
git add .
git commit -m "feat: initial appointment service setup with CRUD endpoints"
git branch -M main
git remote add origin https://github.com/HMS-Microservices-SLIIT/appointment-service.git
git push -u origin main
```

---

## 📋 API Endpoints Summary

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/appointments` | Create new appointment |
| GET | `/appointments` | Get all appointments |
| GET | `/appointments/{id}` | Get by ID |
| PUT | `/appointments/{id}` | Update appointment |
| DELETE | `/appointments/{id}` | Delete appointment |
| GET | `/appointments/patient/{patientId}` | Get by patient |
| GET | `/appointments/doctor/{doctorId}` | Get by doctor |
| GET | `/appointments/department/{departmentId}` | Get by department |
| GET | `/appointments/status/{status}` | Get by status |
| GET | `/appointments/date/{date}` | Get by date (YYYY-MM-DD) |

---

## 🔗 Inter-Service Communication

This service fetches names from other services when creating/updating appointments:
- **Patient Service** → to get patient's full name
- **Doctor Service** → to get doctor's name
- **Department Service** → to get department name

If those services are not running yet, it's fine — the appointment will still be saved with the name you manually provide in the request body.

---

## ✅ Checklist Before Submission

- [ ] Database connected and `appointments` table created
- [ ] All 10 endpoints working in Swagger
- [ ] Screenshots taken from http://localhost:8083/swagger-ui.html
- [ ] Screenshots taken from http://localhost:8080/appointments (via gateway)
- [ ] Code pushed to GitHub
- [ ] Inter-service communication tested
