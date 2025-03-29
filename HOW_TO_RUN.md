# How to Run the Multiple Choice Exam System

This guide provides step-by-step instructions for running the application after setting up your database.

## Prerequisites

1. **Java Development Kit (JDK) 17** installed on your system
2. **Maven** installed on your system
3. **Microsoft SQL Server** running with the THI_TRAC_NGHIEM database created
4. Database populated with schema and sample data

## Verify Database Configuration

Before running the application, check that the database connection settings in `src/main/java/com/exam/utils/DatabaseConfig.java` match your SQL Server setup:

```java
private static final String DB_URL = "jdbc:sqlserver://localhost;databaseName=THI_TRAC_NGHIEM;encrypt=false";
private static final String USER = "sa"; // Replace with your actual SQL Server username
private static final String PASSWORD = "Password.1"; // Replace with your actual SQL Server password
```

Update these values if necessary to match your SQL Server instance, username, and password.

## Option 1: Run from IDE (Visual Studio Code)

This is the simplest way to run the application during development:

1. Open the project in Visual Studio Code
2. Make sure you have the "Extension Pack for Java" installed
3. Open `src/main/java/com/exam/Main.java`
4. Click the "Run" button (usually a green triangle) near the `main` method
5. The application should start and display the login window

If you get any errors about JavaFX modules, you may need to add VM arguments. Add this to your launch configuration:
```
--module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml
```

## Option 2: Run with Maven

You can run the application directly using the Maven JavaFX plugin:

1. Open a terminal or command prompt
2. Navigate to the project directory (where the pom.xml file is located)
3. Run the following command:
   ```
   mvn javafx:run
   ```
4. The application should start and display the login window

## Option 3: Build and Run JAR

To create a standalone JAR file that you can distribute:

1. Open a terminal or command prompt
2. Navigate to the project directory
3. Run the following command to build the project:
   ```
   mvn clean package
   ```
4. This will create a JAR file in the `target` directory, named something like `multiple-choice-exam-1.0-SNAPSHOT.jar`
5. Run the JAR with:
   ```
   java -jar target/multiple-choice-exam-1.0-SNAPSHOT.jar
   ```

Note: If you encounter issues with the JAR not finding JavaFX modules, use:
```
java --module-path /path/to/javafx-sdk-17/lib --add-modules javafx.controls,javafx.fxml -jar target/multiple-choice-exam-1.0-SNAPSHOT.jar
```

## Login Credentials

After running the application, use these credentials to log in:

### PGV (Coordinator) Account
- Username: admin
- Password: password

### Teacher Accounts
- Username: teacher1, teacher2, teacher3
- Password: password

### Student Account
- Username: student
- Password: password

## Troubleshooting

### Database Connection Issues
- Verify SQL Server is running
- Check that the database name is correct (THI_TRAC_NGHIEM)
- Ensure the username and password in DatabaseConfig.java are correct
- Make sure the server name is correct (default is localhost)

### Application Won't Start
- Check that JavaFX is properly included in your classpath
- Verify that JDK 17 is being used
- Look for error messages in the console output

### Login Problems
- Verify that the sample data SQL script ran successfully and created user accounts
- Check the users table in the database to confirm accounts exist

### JavaFX Not Found
If you see errors about JavaFX modules not being found:
1. Download the JavaFX SDK from [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
2. Add the JavaFX modules to your execution command as shown in Option 3