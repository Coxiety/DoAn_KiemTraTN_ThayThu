@echo off
echo Multiple Choice Exam System Launcher
echo ===================================

REM Check if the JavaFX path environment variable exists
if not defined JAVAFX_HOME (
    echo JAVAFX_HOME environment variable is not set.
    echo Please download JavaFX SDK from https://gluonhq.com/products/javafx/
    echo Extract it to a folder and then either:
    echo 1. Set JAVAFX_HOME environment variable to point to the extracted location
    echo 2. Or edit this batch file to set JAVAFX_PATH directly
    echo.
    echo Press any key to open the download page...
    pause >nul
    start "" "https://gluonhq.com/products/javafx/"
    exit /b 1
)

REM Option 1: Run with Maven if it's available
where mvn >nul 2>&1
if %ERRORLEVEL% EQU 0 (
    echo Found Maven, attempting to run with Maven...
    call mvn clean javafx:run
    if %ERRORLEVEL% EQU 0 (
        exit /b 0
    ) else (
        echo Maven execution failed. Trying direct Java execution...
    )
)

REM Option 2: Run with direct Java command if Maven failed or not found
echo Running with Java and JavaFX modules...

REM Check if JAR exists, build it if it doesn't
if not exist target\multiple-choice-exam-1.0-SNAPSHOT.jar (
    echo JAR not found, attempting to build project...
    if exist "pom.xml" (
        where mvn >nul 2>&1
        if %ERRORLEVEL% EQU 0 (
            call mvn clean package
        ) else (
            echo Maven not found. Please install Maven or build the project manually.
            exit /b 1
        )
    ) else (
        echo Cannot find pom.xml. Are you running this from the project directory?
        exit /b 1
    )
)

REM Run the application
java --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml -jar target\multiple-choice-exam-1.0-SNAPSHOT.jar

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo Application failed to start. Make sure you have:
    echo 1. JDK 17 installed and in your PATH
    echo 2. Set JAVAFX_HOME correctly ^(currently: %JAVAFX_HOME%^)
    echo 3. Built the project successfully with 'mvn clean package'
)

pause