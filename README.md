# Smart Ward Management System V2

A comprehensive, production-ready administrative and citizen platform for urban ward management. Inspired by the **Swachh Survekshan** initiative, this system streamlines the interaction between citizens and local authorities to maintain and improve ward infrastructure.

## 🚀 Features

### For Citizens
- **Dashboard:** Personal overview of submitted complaints and feedback.
- **Complaint Management:** Detailed form to report civic issues with priority and category.
- **Ward Directory:** View details about various wards and the facilities available (Hospitals, Schools, Banks, etc.).
- **Feedback System:** Provide feedback on ward services to help improve local governance.

### For Administrators
- **Real-time Analytics:** Dashboard with visualizing KPIs using charts (complaint status, category distribution, feedback trends).
- **Ward Management:** Create, update, and manage ward-specific data and facilities.
- **Issue Resolution:** Track and manage citizen complaints efficiently.

## 🛠️ Technology Stack
- **Backend:** Java 17, Spring Boot 3.2.4
- **Security:** Spring Security (RBAC - Role-Based Access Control)
- **Database:** MySQL
- **ORM:** Spring Data JPA
- **Templating:** Thymeleaf
- **Styling:** Vanilla CSS (Modern UI/UX)
- **Utilities:** Lombok, Maven

## 📋 Prerequisites
- JDK 17 or higher
- MySQL Server
- Maven

## ⚙️ Installation & Setup

1. **Clone the repository:**
   ```bash
   git clone https://github.com/Raman-1166/smart-ward-management.git
   cd smart-ward-management
   ```

2. **Database Configuration:**
   Update `src/main/resources/application.properties` with your MySQL credentials:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/ward_system
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

3. **Build the project:**
   ```bash
   mvn clean install
   ```

4. **Run the application:**
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`.

## 📂 Project Structure
```text
src/main/java/com/ward/system/
├── config/       # Security and App configurations
├── controller/   # Web and API controllers
├── model/        # JPA Entities
├── repository/   # Data Access Layer
├── service/      # Business Logic
└── util/         # Helper classes
```

## 🤝 Contributing
Feel free to fork this project and submit pull requests. For major changes, please open an issue first to discuss what you would like to change.

## 📄 License
This project is licensed under the MIT License - see the LICENSE file for details.
