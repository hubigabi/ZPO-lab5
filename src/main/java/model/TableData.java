package model;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class TableData {

    int no;
    Field field;
    String fieldName;
    String fieldValue;
    Method getterMethod;
    Method setterMethod;

    public TableData(int no, Field field, String fieldName, String fieldValue, Method getterMethod, Method setterMethod) {
        this.no = no;
        this.field = field;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.getterMethod = getterMethod;
        this.setterMethod = setterMethod;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Method getGetterMethod() {
        return getterMethod;
    }

    public void setGetterMethod(Method getterMethod) {
        this.getterMethod = getterMethod;
    }

    public Method getSetterMethod() {
        return setterMethod;
    }

    public void setSetterMethod(Method setterMethod) {
        this.setterMethod = setterMethod;
    }

    @Override
    public String toString() {
        return "TableData{" +
                "no=" + no +
                ", field=" + field +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                ", getterMethod=" + getterMethod +
                ", setterMethod=" + setterMethod +
                '}';
    }
}