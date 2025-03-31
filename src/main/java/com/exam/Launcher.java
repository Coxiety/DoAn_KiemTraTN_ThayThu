package com.exam;

/**
 * Alternative launcher class for the application.
 * This is used to work around issues with JavaFX modules and class path.
 */
public class Launcher {
    
    /**
     * Main method that delegates to the JavaFX application main method
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        Main.main(args);
    }
}