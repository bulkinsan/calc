package com.calc;

import com.calc.enums.EnumList;

public class ExpressionObject {

    private int index = -1;
    private String stringV = "";
    private EnumList enumV;
    private int value;

    /**
     * Конструктор класса объекта(выражения) математического выражения
     * @param index -  индекс символа в входящей строке. Применяется только для символа математической операции
     * @param stringV - строковое значение операнда
     * @param enumV - тип операнда(число араб/римское, оператор, пустой)
     * @param value - числовое значение операнда
     */
    public ExpressionObject(int index, String stringV, EnumList enumV, int value) {
        this.index = index;
        this.stringV = stringV;
        this.enumV = enumV;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getStringV() {
        return stringV;
    }

    public void setStringV(String stringV) {
        this.stringV = stringV;
    }

    public EnumList getEnumV() {
        return enumV;
    }

    public void setEnumV(EnumList enumV) {
        this.enumV = enumV;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setFillValues(int indexNew, EnumList enumVNew){
        setEnumV(enumVNew);
        setIndex(indexNew);
        setStringV(enumVNew.getString());
    }
}
