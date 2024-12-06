package fr.istic.aco.editor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main class for the Text Editor application.
 * This is the entry point of the application and uses Spring Boot to initialize and run the application context.
 *
 * @author Benedict Wolff
 * @version 1.0
 */
@SpringBootApplication
public class Editor {

    /**
     * The main method for starting the Text Editor application.
     *
     * @param args command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        SpringApplication.run(Editor.class, args);
    }

}