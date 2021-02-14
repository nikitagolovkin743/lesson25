package com.golovkin.lesson25;

import com.golovkin.lesson25.consoleapp.ConsoleApp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson25Application {
    private static ConsoleApp consoleApp;

    public Lesson25Application(ConsoleApp consoleApp) {
        Lesson25Application.consoleApp = consoleApp;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lesson25Application.class, args);
        consoleApp.run();
    }

}
