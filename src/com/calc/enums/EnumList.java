package com.calc.enums;

import com.calc.ExceptionHandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EnumList {

    MINUS("-", 0, enumTypeNum.OPERATOR),
    PLUS("+", 0, enumTypeNum.OPERATOR),
    MULTIPLICATION("*", 0, enumTypeNum.OPERATOR),
    DIVISION("/", 0, enumTypeNum.OPERATOR),

    EMPTY("", 0, enumTypeNum.EMPTY),

    I("I", 1, enumTypeNum.NUMERALS_ROME),
    V("V", 5, enumTypeNum.NUMERALS_ROME),
    X("X", 10, enumTypeNum.NUMERALS_ROME),
    L("L", 50, enumTypeNum.NUMERALS_ROME),
    C("C", 100, enumTypeNum.NUMERALS_ROME),

    NUM_ARABIC("arabicNum", 0, enumTypeNum.NUMERALS_ARABIC),
    NUM_ROME("romeNum", 0, enumTypeNum.NUMERALS_ROME);

    private final String string;
    private final int value;
    private final enumTypeNum type;

    EnumList(String string, int value, enumTypeNum type) {
        this.string = string;
        this.value = value;
        this.type = type;
    }

    /**
     * Поиск символа математической операции в списке значений по символу
     *
     * @param ch символ
     * @return список enum
     */
    public static EnumList findOperatorInEnum(char ch) {
        EnumList operator = EnumList.EMPTY;
        String InputSymbol = Character.toString(ch);
        List<EnumList> enumArray = getEnumOperators();
        for (EnumList enumOperator : enumArray) {
            if (enumOperator.getString().equals(InputSymbol)) {
                operator = enumOperator;
            }
        }
        return operator;
    }

    /**
     * Получить список перечислений допустимых математических операций из общего списка перечислений по значению operator поля type
     *
     * @return - список перечислений допустимых математических операций
     */
    public static List<EnumList> getEnumOperators() {
        List<EnumList> enumArray = new ArrayList<>();
        for (int i = 0; i < values().length; i++) {
            if (values()[i].getType() == enumTypeNum.OPERATOR) {
                enumArray.add(values()[i]);
            }
        }
        return enumArray;
    }

    /**
     * Получение перечислений римских цифр по значению numRome поля type
     *
     * @return - массив перечислений римских цифр
     */
    public static Map<EnumList, Integer> convertInMapRomeNums() {
        Map<EnumList, Integer> MapEnumsRomeNums = new HashMap<>();
        for (EnumList enumRome : values()) {
            if (enumRome.getType().equals(enumTypeNum.NUMERALS_ROME) && !enumRome.equals(EnumList.NUM_ROME)) {
                MapEnumsRomeNums.put(enumRome, enumRome.getValue());
            }
        }
        return MapEnumsRomeNums;
    }

    /**
     * Получение числового значение по индексу символа в строке римский цифр
     *
     * @param valueString - строка римских цифр
     * @param index       - индекс символа в строке
     * @return - целое число
     */
    public static int getValueRomeNum(String valueString, int index) {
        int newValue;
        char[] chars = valueString.toCharArray();
        char ch = valueString.charAt(index);
        int valueStringLength = valueString.length();

        switch (Character.toString(ch)) {
            case "V":
                if (valueStringLength < index) {
                    if ("I".equals(Character.toString(chars[index - 1]))) {
                        newValue = +3;
                    }
                    newValue = +5;
                } else {
                    newValue = +5;
                }
                break;
            case "I":
                newValue = 1;
                break;
            case "X":
                if (0 < index) {
                    if ("I".equals(Character.toString(chars[index - 1]))) {
                        newValue = +8;
                    } else {
                        newValue = +10;
                    }
                } else {
                    newValue = +10;
                }

                break;
            default:
                throw new ExceptionHandling(ReasonType.IS_NOT_CORRECT_ROME_NUMERALS);
        }

        return newValue;
    }

    /**
     * Перевод и получение целых чисел из в римскими цифрами
     *
     * @param intNumber - целое число
     * @return - строка, переведенное число
     */
    public static String getInRomeNum(int intNumber) {
        String stringRomeNum = "";

        // Если число отрицательное, перевод в положительное, чтобы безпроблем конвертировать,
        // но при этом запоминаем, чтобы вернуть знак
        boolean minus = false;
        if (intNumber < 0) {
            intNumber = Math.abs(intNumber);
            minus = true;
        } else if (0 == intNumber) {
            return "0";
        }

        // разбираемся с десятками, разделив без остатка на 10, получим кол-во десятков
        int numberOfTens = intNumber / 10;
        if (99 < intNumber) {
            stringRomeNum += "C";
        }
        if (9 < numberOfTens * 10) {

            int newNumberOfTens = numberOfTens;
            if (9 == numberOfTens) {
                stringRomeNum += "XC";
                newNumberOfTens = 0;
            } else if (4 == numberOfTens) {

                stringRomeNum += "XL";
                newNumberOfTens = 0;
            } else if (5 <= numberOfTens) {
                stringRomeNum += "L";
                newNumberOfTens = numberOfTens - 5;
            }
            if (1 <= newNumberOfTens) {
                for (int i = 1; i <= newNumberOfTens; i++) {
                    stringRomeNum += "X";
                }
            }
        }

        // с десятками разобрались, получаем единицы и конвернтируем
        int remainder = intNumber - numberOfTens * 10;
        if (0 == remainder) {
            return stringRomeNum;
        }
        if (9 == remainder) {
            return stringRomeNum += "IX";
        }
        if (5 == remainder) {
            return stringRomeNum += "V";
        } else if (5 < remainder) {
            String tempStr = "";
            for (int i = 5; i < remainder; i++) {
                tempStr += "I";
            }
            stringRomeNum += "V" + tempStr;
        } else if (4 == remainder) {
            stringRomeNum += "IV";
        } else if (4 > remainder) {
            for (int i = 1; i <= remainder; i++) {
                stringRomeNum += "I";
            }
        }

        // если число было отрицательное, возврат в строку знака минуса
        if (minus) {
            stringRomeNum = "-" + stringRomeNum;
        }
        return stringRomeNum;
    }

    public String getString() {
        return string;
    }

    public int getValue() {
        return value;
    }

    public enumTypeNum getType() {
        return type;
    }

    public enum enumTypeNum {
        OPERATOR, NUMERALS_ARABIC, NUMERALS_ROME, EMPTY
    }

}

