package com.calc.enums;

public enum ReasonType {
    OPERATOR_IS_NOT_FOUND("Не корректное выражение!\nМатематический оператор не найден\nВведите выражение формата a+b"),
    INCORRECT_INPUT("Не коррекктное выражение. Введите выражение формата a+b"),
    IS_DOUBLE("Неверный тип данных операнда. Введите целые числа"),
    IS_NOT_CORRECT_ROME_NUMERALS("Неверный тип данных операнда. Римские цифры не совсем правильные или выходят за границу"),
    INCORRECT_INTEGER("Калькулятор принимает на вход числа от 1 до 10 включительно, не более"),
    TYPES_NOT_EQUAL("Калькулятор умеет работать только с арабскими или римскими цифрами одновременно");

    private final String text;

    ReasonType(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
