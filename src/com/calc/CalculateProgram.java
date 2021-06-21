package com.calc;

import com.calc.enums.EnumList;

import static com.calc.enums.ReasonType.INCORRECT_INPUT;
import static com.calc.enums.ReasonType.OPERATOR_IS_NOT_FOUND;

public class CalculateProgram {

    private static final int MIN_LENGTH_STRING = 3;
    private static final String OUTPUT_TEMPLATE = "%s %s %s = %s";
    private static final int DEFAULT_RESULT = 0;

    /**
     * Метод анализирует введенные данные. Если корректные, выполняет мат.операцию воответствующую введенной строке
     *
     * @param inputString - введеленная строка
     * @return - true в случае соответсвии всех условия и успешного выполнения задачи
     */
    public static void calculate(String inputString) {
        analyzeStringCreateExpressionObject(inputString);
    }

    /**
     * Выполение математической операции.
     *
     * @param operandA - операнд А
     * @param operandB - операнд Б
     * @param operator - математическое действие
     */
    public static void runMathOperation(ExpressionObject operandA, ExpressionObject operandB, ExpressionObject operator) {
        int result = DEFAULT_RESULT;
        EnumList tmpOperator = operator.getEnumV();
        if (EnumList.MINUS.equals(tmpOperator)) {
            result = operandA.getValue() - operandB.getValue();
        } else if (EnumList.PLUS.equals(tmpOperator)) {
            result = operandA.getValue() + operandB.getValue();
        } else if (EnumList.DIVISION.equals(tmpOperator)) {
            result = operandA.getValue() / operandB.getValue();
        } else if (EnumList.MULTIPLICATION.equals(tmpOperator)) {
            result = operandA.getValue() * operandB.getValue();
        }

        if (EnumList.NUM_ROME.equals(operandA.getEnumV())) {
            System.out.println(String.format(OUTPUT_TEMPLATE, operandA.getStringV(), operator.getStringV(), operandB.getStringV(), EnumList.getInRomeNum(result)));
        } else {
            System.out.println(String.format(OUTPUT_TEMPLATE, operandA.getStringV(), operator.getStringV(), operandB.getStringV(), result));
        }
    }

    /**
     * Анализ введеленной строки, разбиение на операнды и математической операции
     *
     * @param text - введенная строка
     */
    private static void analyzeStringCreateExpressionObject(String text) {

        text = fixInputString(text);

        // разделение строки на массив символов
        char[] arrayCharInput = text.toCharArray();

        // Создание массива готовых объектов выражения, где [0],[2] - операнды, [1] - математическая операция
        ExpressionObject operandA = new ExpressionObject(-1, "", EnumList.EMPTY, 0);
        ExpressionObject operator = new ExpressionObject(-1, "", EnumList.EMPTY, 0);
        ExpressionObject operandB = new ExpressionObject(-1, "", EnumList.EMPTY, 0);

        findOperator(operator, arrayCharInput);
        findOperand(operandA, arrayCharInput, 0, operator.getIndex() - 1);
        findOperand(operandB, arrayCharInput, operator.getIndex() + 1, arrayCharInput.length - 1);

        ValidationCheck.validateFoundOperand(operandA);
        ValidationCheck.validateFoundOperand(operandB);

        ValidationCheck.validationTypes(operandA, operandB);
        runMathOperation(operandA, operandB, operator);
    }

    /**
     * Приведение введенной строки к рабочему виду, сделав операции:
     * - приведение букв в верхний регистр
     * - удаление пробелов
     *
     * @param text - введенная строка
     * @return
     */
    //todo javadoc
    private static String fixInputString(String text) {
        if (text.length() < MIN_LENGTH_STRING) {
            throw new ExceptionHandling(INCORRECT_INPUT);
        }
        return text.toUpperCase().replaceAll("\\s+", "");
    }

    /**
     * Поиск символа математической операции
     * Если найден корректный, заполняется объект operator, иначе вызываем исключение
     *
     * @param operator       - объект типа ExpressionObject. Свойства объекта заполняются в случае успеха
     * @param arrayCharInput - массив символов, полученный из введенной строки
     */
    private static void findOperator(ExpressionObject operator, char[] arrayCharInput) {
        // Поиск математичекого оператора
        // Цикл начинается со 2ого элемента, чтобы учесть в будущем операнд отрицательного числа
        for (int i = 1; i < arrayCharInput.length; i++) {

            EnumList enumFoundOperator = EnumList.findOperatorInEnum(arrayCharInput[i]);
            if (operator.getStringV().isEmpty() && !EnumList.EMPTY.equals(enumFoundOperator)) {
                operator.setFillValues(i, enumFoundOperator);
            } else if (!operator.getStringV().isEmpty() && !EnumList.EMPTY.equals(enumFoundOperator)) {
                throw new ExceptionHandling(OPERATOR_IS_NOT_FOUND);
            }
        }
        if (EnumList.EMPTY.equals(operator.getEnumV())) {
            throw new ExceptionHandling(OPERATOR_IS_NOT_FOUND);
        }
    }

    /**
     * Поиск Операндов в массиве символов.
     *
     * @param operand        - объект типа ExpressionObject
     * @param arrayCharInput - массив символов, полученный из введенной строки
     * @param firstIndex     - индекс первого символа формирующий строковое поле Операнда из массива символов
     * @param lastIndex      - индекс последнего символа формирующий строковое поле Операнда из массива символов
     */
    private static void findOperand(ExpressionObject operand, char[] arrayCharInput, int firstIndex, int lastIndex) {
        for (int i = firstIndex; i <= lastIndex; i++) {
            operand.setStringV(operand.getStringV() + arrayCharInput[i]);
        }
    }
}

