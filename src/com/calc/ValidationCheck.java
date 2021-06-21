package com.calc;

import com.calc.enums.EnumList;

import java.util.Map;

import static com.calc.enums.ReasonType.*;

public class ValidationCheck {

    /**
     * Проверка переданного объекта операнда на соответствие допустимых значений
     * Если значение допустимо, возврат объекта с обновленным значением поля value
     *
     * @param operand - объект операнд математического выражения
     */
    public static void validateFoundOperand(ExpressionObject operand) {

        // признак успеха. В случае успеха проверки int, не обрабатывать текстовую строку
        boolean success = false;

        // Проверка на тип double
        try {
            if (Double.parseDouble(operand.getStringV()) - Double.parseDouble(operand.getStringV()) < 0) {
                throw new ExceptionHandling(IS_DOUBLE);
            }
        } catch (Exception ignored) {
        }

        // Проверка на тип int.
        try {
            int newValue = Integer.parseInt(operand.getStringV());
            operand.setValue(newValue);
            operand.setEnumV(EnumList.NUM_ARABIC);
            success = true;
        } catch (Exception ignored) {
        }

        // проверка текстовой строки, обработкаи римских цифр и обновления числового значения операнда
        if (!success) {
            int countRomeNums = 0;
            Map<EnumList, Integer> mapEnumRomeNum = EnumList.convertInMapRomeNums();
            String strOperand = operand.getStringV();
            for (int i = 0; i < operand.getStringV().length(); i++) {
                countRomeNums = getCountRomeNumsByString(operand, countRomeNums, mapEnumRomeNum, strOperand, i);
            }
            if (countRomeNums != operand.getStringV().length()) {
                throw new ExceptionHandling(IS_NOT_CORRECT_ROME_NUMERALS);
            }
        }
        // проверка числового значения операнда. Разрешены числа в диапазоне 1-10 включительно
        if (operand.getValue() >= 11 || operand.getValue() <= 0) {
            throw new ExceptionHandling(INCORRECT_INTEGER);
        }
    }

    /**
     * @param operandA
     * @param operandB
     */
    //todo написать javadoc
    public static void validationTypes(ExpressionObject operandA, ExpressionObject operandB) {
        if (!operandA.getEnumV().equals(operandB.getEnumV())) {
            throw new ExceptionHandling(TYPES_NOT_EQUAL);
        }
    }

    //todo написать javadoc
    private static int getCountRomeNumsByString(ExpressionObject operand, int countRomeNums, Map<EnumList, Integer> mapEnumRomeNum,
                                                String strOperand, int i) {
        for (EnumList romeNum : mapEnumRomeNum.keySet()) {
            if (romeNum.getString().equals(Character.toString(strOperand.charAt(i)))) {
                operand.setValue(operand.getValue() + EnumList.getValueRomeNum(operand.getStringV(), i));
                operand.setEnumV(EnumList.NUM_ROME);
                countRomeNums += 1;
            }
        }
        return countRomeNums;
    }
}

