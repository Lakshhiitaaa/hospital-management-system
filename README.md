# 🏥 Hospital Management System

A **Full Stack Microservices-based Hospital Management System** built using **Spring Boot, Spring Cloud, Angular, and MySQL**.
The system manages hospital operations including **authentication, appointments, billing, doctor management, and medicine inventory**.

---

# 🚀 Tech Stack

### Backend

* Java
* Spring Boot
* Spring Cloud
* Eureka Discovery Server
* API Gateway
* Microservices Architecture

### Frontend

* Angular
* HTML
* CSS
* TypeScript

### Database

* MySQL

### Tools

* Git & GitHub
* VS Code
* Postman

---

# 🏗 System Architecture

The application follows **Microservices Architecture**.

```
                ┌─────────────────────┐
                │     Angular UI      │
                │   (Frontend 4200)   │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │     API Gateway     │
                │   (Spring Cloud)    │
                └──────────┬──────────┘
                           │
                           ▼
                ┌─────────────────────┐
                │   Eureka Discovery  │
                │      Server         │
                └──────────┬──────────┘
                           │
        ┌──────────────────┼──────────────────┐
        ▼                  ▼                  ▼
┌───────────────┐  ┌───────────────┐  ┌───────────────┐
│ Signup/Login  │  │ Doctor Dept   │  │ Billing       │
│ Service       │  │ Service       │  │ Service       │
└───────────────┘  └───────────────┘  └───────────────┘

        ▼
┌──────────────────────────────┐
│       MySQL Database         │
│   (Hospital Records)         │
└──────────────────────────────┘
```

---

# ⚙️ Microservices

The backend consists of multiple services:

* Discovery Service (Eureka Server)
* Gateway Service
* Signup/Login Service
* Doctor Department Service
* Billing Service
* EMR Service
* Medicine Inventory Service

---

# ✨ Features

✔ User Authentication (Login / Signup)
✔ Doctor & Department Management
✔ Appointment Scheduling
✔ Billing System
✔ Electronic Medical Records
✔ Medicine Inventory Management

---

# 📂 Project Structure

```
hospital-management-system
│
├── backend
│   ├── Discovery-Service
│   ├── Gateway-Service
│   ├── SignupLogin-Service
│   ├── Doctor-Department-Service
│   ├── Billing-Service
│   ├── EMR-Service
│   └── MedicineInventory-Service
│
└── frontend
    └── Angular Application
```

---

# ▶️ How to Run the Project

### 1️⃣ Start MySQL Database

### 2️⃣ Run Discovery Server

```
localhost:8761
```

### 3️⃣ Run Backend Microservices

Start all services inside the backend folder.

### 4️⃣ Run Angular Frontend

```
cd frontend
npm install
ng serve
```

Frontend runs at:

```
http://localhost:4200
```

---

# 👩‍💻 Author

**Lakshita Bhatnagar**

B.Tech Student | Full Stack Developer

GitHub: https://github.com/Lakshhiitaaa
