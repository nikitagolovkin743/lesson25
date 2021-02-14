package com.golovkin.lesson25.consoleapp;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Predicate;

public class ConsoleHelper {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static int requestInput(String message, Predicate<Integer> successCondition) {
        int chosenIndex = -1;
        do {
            System.out.print(message);
            try {
                chosenIndex = scanner.nextInt() - 1;
            } catch (InputMismatchException ignored) {
                System.out.println("Invalid input");
            }
            scanner.nextLine();
        } while (!successCondition.test(chosenIndex));

        return chosenIndex;
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
