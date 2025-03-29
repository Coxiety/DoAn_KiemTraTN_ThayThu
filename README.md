# Multiple Choice Exam System

## Overview

This is a Java-based desktop application for managing multiple choice exams in an educational institution. The application provides different interfaces for teachers and students, allowing teachers to create questions, manage classes, subjects, and exams, while students can take exams and view their results.

## Project Structure

The project follows a standard JavaFX application structure:

- `src/main/java/com/exam/models`: Contains data models used in the application
- `src/main/java/com/exam/dao`: Contains interfaces for data access operations
- `src/main/java/com/exam/dao/impl`: Contains implementations for DAO interfaces
- `src/main/java/com/exam/controllers`: Contains JavaFX controllers
- `src/main/java/com/exam/utils`: Contains utility classes
- `src/main/resources/fxml`: Contains FXML files for UI
- `src/main/resources/css`: Contains CSS files for styling
- `src/main/resources/db`: Contains database scripts

## Features

### Teacher Features

- **User Authentication**: Secure login for teachers
- **Class Management**: Create, edit, and delete classes
- **Student Management**: Add and remove students from classes
- **Subject Management**: Create, edit, and delete subjects
- **Subject Registration**: Register to teach specific subjects
- **Question Management**: Create, edit, and delete exam questions
- **Exam Preparation**: Create and configure exams
- **Results Viewing**: View student exam results

### Student Features

- **User Authentication**: Secure login for students
- **Exam Taking**: Take assigned exams
- **Result Viewing**: View exam results and performance

## Technical Implementation

The application is built using:

- **Java 17+**: Core programming language
- **JavaFX**: UI framework
- **SQL Server**: Database engine
- **JDBC**: Database connectivity

## Database Structure

The database THI_TRAC_NGHIEM consists of several interconnected tables:

- `LOP`: Class information
- `MONHOC`: Subject information
- `SINHVIEN`: Student information
- `GIAOVIEN`: Teacher information
- `GIAOVIEN_DANGKY`: Teacher-subject-class registration mapping
- `BODE`: Contains exam questions
- `BANGDIEM`: Stores exam results
- `USERS`: User authentication information (added for application functionality)

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven
- Microsoft SQL Server

### Setup

1. **Clone the repository**

2. **Set up the database**
   - Create a new database in SQL Server named `THI_TRAC_NGHIEM`
   - Execute the schema script:
     ```
     src/main/resources/db/schema.sql
     ```
   - Load sample data:
     ```
     src/main/resources/db/sample-data.sql
     ```

3. **Configure database connection**
   - Open `src/main/java/com/exam/utils/DatabaseConfig.java`
   - Update the connection string, username, and password to match your SQL Server configuration:
     ```java
     private static final String DB_URL = "jdbc:sqlserver://localhost;databaseName=THI_TRAC_NGHIEM;encrypt=false";
     private static final String USER = "your_username";
     private static final String PASSWORD = "your_password";
     ```

4. **Build the project**
   ```
   mvn clean package
   ```

5. **Run the application**
   ```
   java -jar target/exam-system.jar
   ```
   or run the `Main.java` class directly from your IDE.

### Login Credentials (When using sample data)

#### PGV (Coordinator) Account
- Username: admin
- Password: password

#### Teacher Accounts
- Username: teacher1, teacher2, teacher3
- Password: password

#### Student Account
- Username: student
- Password: password

## Exam Rules and Implementation

- **Question Selection**: Random questions are selected based on levels (A, B, C) without duplication. Higher-level exams can include up to 30% lower-level questions if at least 70% are high-level.
- **Scoring**: Each exam has a maximum score of 10, with points distributed equally among all questions.
- **Time Limit**: Exams have configurable time limits (5-60 minutes). When time expires, the exam automatically ends.
- **Results**: Scores are displayed immediately and recorded in the BANGDIEM table.
- **Reports**: Teachers can view and print exam results and subject scoreboards for their classes.

## User Roles

1. **PGV (Coordinator)**:
   - Full system access
   - Can create PGV and Teacher accounts
   - Full access to all management features

2. **Teacher**:
   - Can update their own exam questions
   - Can conduct practice tests
   - Can review student exam papers
   - Can print subject scoreboards

3. **Student**:
   - Can take exams
   - Can review their own exam history

## Troubleshooting Database Issues

If you encounter issues with the sample data SQL script:

1. **Foreign Key Constraint Errors**:
   - Make sure to run the schema script completely before running the sample data script
   - Check that all referenced entities exist before inserting records that reference them

2. **Column Size Limitations**:
   - All string data in the sample data script has been sized appropriately for the schema
   - If you add custom data, ensure it respects the column size limits

3. **Database Connection Issues**:
   - Verify SQL Server is running and accessible
   - Check that your SQL Server authentication settings are correct in DatabaseConfig.java
   - Ensure the database name matches exactly: THI_TRAC_NGHIEM

## Developer Documentation

### Adding New Features

1. Create model classes in the models package
2. Create DAO interfaces and implementations
3. Create controller classes and FXML files
4. Register the new views in the appropriate dashboard controller

### Customizing the UI

The application uses CSS for styling. Modify the CSS files in `src/main/resources/css` to customize the appearance.