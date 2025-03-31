#!/bin/bash

echo "Multiple Choice Exam System Launcher"
echo "==================================="

# Check if the JavaFX path environment variable exists
if [ -z "$JAVAFX_HOME" ]; then
    echo "JAVAFX_HOME environment variable is not set."
    echo "Please download JavaFX SDK from https://gluonhq.com/products/javafx/"
    echo "Extract it to a folder and then either:"
    echo "1. Set JAVAFX_HOME environment variable to point to the extracted location"
    echo "2. Or edit this script to set JAVAFX_PATH directly"
    echo ""
    echo "Press Enter to open the download page..."
    read
    if command -v xdg-open &> /dev/null; then
        xdg-open "https://gluonhq.com/products/javafx/"
    elif command -v open &> /dev/null; then
        open "https://gluonhq.com/products/javafx/"
    fi
    exit 1
fi

# Option 1: Run with Maven if it's available
if command -v mvn &> /dev/null; then
    echo "Found Maven, attempting to run with Maven..."
    mvn clean javafx:run
    if [ $? -eq 0 ]; then
        exit 0
    else
        echo "Maven execution failed. Trying direct Java execution..."
    fi
fi

# Option 2: Run with direct Java command if Maven failed or not found
echo "Running with Java and JavaFX modules..."

# Check if JAR exists, build it if it doesn't
if [ ! -f "target/multiple-choice-exam-1.0-SNAPSHOT.jar" ]; then
    echo "JAR not found, attempting to build project..."
    if [ -f "pom.xml" ]; then
        if command -v mvn &> /dev/null; then
            mvn clean package
        else
            echo "Maven not found. Please install Maven or build the project manually."
            exit 1
        fi
    else
        echo "Cannot find pom.xml. Are you running this from the project directory?"
        exit 1
    fi
fi

# Run the application
java --module-path "$JAVAFX_HOME/lib" --add-modules javafx.controls,javafx.fxml -jar target/multiple-choice-exam-1.0-SNAPSHOT.jar

if [ $? -ne 0 ]; then
    echo ""
    echo "Application failed to start. Make sure you have:"
    echo "1. JDK 17 installed and in your PATH"
    echo "2. Set JAVAFX_HOME correctly (currently: $JAVAFX_HOME)"
    echo "3. Built the project successfully with 'mvn clean package'"
fi

echo "Press Enter to exit..."
read