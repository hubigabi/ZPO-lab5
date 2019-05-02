package model;

public class Bean2 {

    private long fieldLong;
    private float fieldFloat;
    private double fieldDouble;

    public Bean2() {
    }

    public Bean2(long fieldLong, float fieldFloat, double fieldDouble) {
        this.fieldLong = fieldLong;
        this.fieldFloat = fieldFloat;
        this.fieldDouble = fieldDouble;
    }

    public long getFieldLong() {
        return fieldLong;
    }

    public void setFieldLong(long fieldLong) {
        this.fieldLong = fieldLong;
    }

    public float getFieldFloat() {
        return fieldFloat;
    }

    public void setFieldFloat(float fieldFloat) {
        this.fieldFloat = fieldFloat;
    }

    public double getFieldDouble() {
        return fieldDouble;
    }

    public void setFieldDouble(double fieldDouble) {
        this.fieldDouble = fieldDouble;
    }
}
