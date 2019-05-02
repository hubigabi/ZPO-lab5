package model;

import java.time.LocalDate;

public class Bean3 {

    private char fieldChar;
    private String fieldString;
    private LocalDate fieldLocalDate;
    private ColorEnum fieldEnum;

    public Bean3() {
    }

    public Bean3(char fieldChar, String fieldString, LocalDate fieldLocalDate, ColorEnum fieldEnum) {
        this.fieldChar = fieldChar;
        this.fieldString = fieldString;
        this.fieldLocalDate = fieldLocalDate;
        this.fieldEnum = fieldEnum;
    }

    public char getFieldChar() {
        return fieldChar;
    }

    public void setFieldChar(char fieldChar) {
        this.fieldChar = fieldChar;
    }

    public String getFieldString() {
        return fieldString;
    }

    public void setFieldString(String fieldString) {
        this.fieldString = fieldString;
    }

    public LocalDate getFieldLocalDate() {
        return fieldLocalDate;
    }

    public void setFieldLocalDate(LocalDate fieldLocalDate) {
        this.fieldLocalDate = fieldLocalDate;
    }

    public ColorEnum getFieldEnum() {
        return fieldEnum;
    }

    public void setFieldEnum(ColorEnum fieldEnum) {
        this.fieldEnum = fieldEnum;
    }

}
