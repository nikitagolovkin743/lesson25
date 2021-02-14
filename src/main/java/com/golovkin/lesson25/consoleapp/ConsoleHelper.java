package com.golovkin.lesson25.consoleapp;

import java.util.*;
import java.util.function.Predicate;

public class ConsoleHelper {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    public static int requestIndexInput(String message, Predicate<Integer> successCondition) {
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

    public static <T> int requestIndexInput(String message, List<?> list) {
        return  requestIndexInput(message, x -> x >= 0 && x < list.size());
    }

    public static <T> int requestIndexInput(String message, Object[] list) {
        return requestIndexInput(message, Arrays.asList(list));
    }

    public static Scanner getScanner() {
        return scanner;
    }
}
