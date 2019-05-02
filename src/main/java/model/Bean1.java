package model;

public class Bean1 {

    private int fieldInt;
    private byte fieldByte;
    private short fieldShort;

    public Bean1() {
    }

    public Bean1(int fieldInt, byte fieldByte, short fieldShort) {
        this.fieldInt = fieldInt;
        this.fieldByte = fieldByte;
        this.fieldShort = fieldShort;
    }

    public int getFieldInt() {
        return fieldInt;
    }

    public void setFieldInt(int fieldInt) {
        this.fieldInt = fieldInt;
    }

    public byte getFieldByte() {
        return fieldByte;
    }

    public void setFieldByte(byte fieldByte) {
        this.fieldByte = fieldByte;
    }

    public short getFieldShort() {
        return fieldShort;
    }

    public void setFieldShort(short fieldShort) {
        this.fieldShort = fieldShort;
    }

}
