package com.calc;

import com.calc.enums.ReasonType;

public class ExceptionHandling extends RuntimeException {

    public ExceptionHandling(ReasonType reason) {
        exceptionHandling(reason);
    }

    /**
     * Метод вызывающий исключение по типу, взависимости от причины ошибки
     *
     * @param reason причина ошибки
     * @throws UnsupportedOperationException не поддерживаемая операция
     */
    private static void exceptionHandling(ReasonType reason) throws UnsupportedOperationException {
        throw new UnsupportedOperationException(reason.getText());
    }
}
