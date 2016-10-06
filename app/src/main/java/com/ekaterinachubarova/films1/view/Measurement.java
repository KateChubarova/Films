package com.ekaterinachubarova.films1.view;

/**
 * Created by ekaterinachubarova on 05.10.16.
 */

public class Measurement {


    private static Measurement measurement;
    private int centerX;
    private int centerY;
    private int r;

    public static Measurement newInstance (int centerX, int centerY, int r) {
        if (measurement == null) {
            measurement = new Measurement(centerX, centerY, r);
        } return measurement;
    }

    public static Measurement newInstance() {
        if (measurement == null) throw new NullPointerException();
        return measurement;
    }

    public Measurement(int centerX, int centerY, int r) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.r = r;


    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }
}
