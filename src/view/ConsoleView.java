package view;

import java.util.Scanner;

public class ConsoleView {
    private static Scanner scanner = new Scanner(System.in);

    public static int getUserChoice() {
        System.out.println("Введите ваш выбор:");
        return scanner.nextInt();
    }
}
