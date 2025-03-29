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

The database consists of several interconnected tables:

- `GiaoVien`: Stores teacher information
- `SinhVien`: Stores student information
- `Lop`: Stores class information
- `MonHoc`: Stores subject information
- `GiaoVien_DangKy`: Maps teachers to subjects they teach
- `BoDe`: Stores questions and answers
- `BangDiem`: Stores exam results

## Getting Started

### Prerequisites

- JDK 17 or higher
- Maven
- Microsoft SQL Server

### Setup

1. Clone the repository
2. Create a SQL Server database and execute the schema.sql script
3. Update database connection parameters in DatabaseConfig.java
4. Build the project using Maven:
   ```
   mvn clean package
   ```
5. Run the application:
   ```
   java -jar target/exam-system.jar
   ```

## Developer Documentation

### Adding New Features

1. Create model classes in the models package
2. Create DAO interfaces and implementations
3. Create controller classes and FXML files
4. Register the new views in the appropriate dashboard controller

### Customizing the UI

The application uses CSS for styling. Modify the CSS files in `src/main/resources/css` to customize the appearance.