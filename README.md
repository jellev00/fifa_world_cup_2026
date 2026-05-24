# ⚽ FIFA World Cup 2026 – Team Prediction

A Spring Boot web application where users can create teams with friends and compete by predicting FIFA World Cup 2026 match results.

This project was developed for the course **Enterprise Web Development: Java**.

---

## 📌 Project Overview

The application allows users to:
- Create an account and log in
- Create or join teams using invite codes
- Predict match results
- Compete against friends through a scoring system
- View team rankings and statistics

Administrators can manage matches and enter official results after games are played.

The project focuses on applying enterprise Java concepts in a real-world web application using Spring Boot and related technologies.

---

## 🚀 Features

### 👤 Authentication & Security
- User registration and login
- Role-based authorization using Spring Security
- Different access levels for Guests, Users, and Admins
- Logout available on every page

### 👥 Team Management
- Create teams
- Join teams using invite codes
- Regenerate invite codes
- Team owner permissions
- Private team pages

### ⚽ Match Management
- View all FIFA World Cup matches
- Match details page
- Admin match creation and editing
- Official result management

### 📊 Prediction System
- Predict match scores
- Edit predictions until 1 hour before kickoff
- Automatic score calculation
- Bonus point system

### 🏆 Rankings
- Private team scoreboard
- Public Top 10 teams ranking
- Individual user scores

### 🔌 REST API Integration
- Fetch matches by date
- Fetch stadium capacity
- Reactive WebClient integration

### ✅ Validation & Error Handling
- Jakarta Validation
- Custom validator annotations
- Custom error pages
- Invalid URL handling

### 🧪 Testing
- MVC Controller tests
- REST Controller tests
- Security tests
- Validation tests

---

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Backend programming language |
| Spring Boot | Main application framework |
| Spring Security | Authentication & authorization |
| Thymeleaf | Server-side templating |
| JPA / Hibernate | Database ORM |
| MySQL | Relational database |
| Jakarta Validation | Form validation |
| REST API | External data communication |
| Reactive WebClient | Reactive API calls |
| JUnit & MockMvc | Testing |

---

## 👥 User Roles

### Guest
- View public information
- View Top 10 team rankings

### User
- Register and log in
- Create teams
- Join teams using invite codes
- Make match predictions
- View personal and team scores

### Admin
- Add and edit matches
- Enter official match results
- Manage match information

---

## 🧮 Scoring System

Users earn points based on prediction accuracy.

### Standard Points
- Correct exact score → X points
- Correct winner/draw → Y points

### Bonus Points
- Only user in the team with exact prediction → +B
- Only user in the team with correct winner → +C

> Values for X, Y, B, and C are configurable through resource bundles.

---

## 📂 Project Structure

```bash
src
├── main
│   ├── java
│   ├── resources
│   │   ├── templates
│   │   ├── static
│   │   └── messages.properties
│   └── test
```

---

## ⚙️ Installation

### Prerequisites
Make sure you have installed:
- Java 17+
- Maven
- MySQL

### Clone the Repository

```bash
git clone https://github.com/your-username/fifa-world-cup-2026.git
```

### Configure Database

Update the `application.properties` file:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fifa_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run the Application

```bash
mvn spring-boot:run
```

The application will run on:

```bash
http://localhost:8080
```

---

## 🔐 Security

The application uses Spring Security with role-based authorization.

Roles:
- ROLE_USER
- ROLE_ADMIN

Protected routes and actions are restricted depending on user permissions.

---

## 🌍 Internationalization

Resource bundles are used for:
- Validation messages
- Labels and UI text
- Date formatting
- Error messages

---

## 📸 Screenshots

> Add screenshots of your application here.

Example:
- Home page
- Team page
- Match prediction page
- Admin dashboard

---

## 📖 Assignment Requirements

This project includes all required features from the assignment:
- Spring Boot + Thymeleaf
- Spring Security
- JPA & MySQL
- Jakarta Validation
- REST API integration
- Reactive WebClient
- Unit testing
- Resource bundles
- Error handling

---

## 🎓 Course Information

**Course:** Enterprise Web Development: Java  
**Academic Year:** 2025–2026

---

## 📄 License

This project was created for educational purposes.
