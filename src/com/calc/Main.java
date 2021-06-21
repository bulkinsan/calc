package com.calc;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Введите выражение формата а+в");
        CalculateProgram.calculate(new Scanner(System.in).nextLine());
    }
}