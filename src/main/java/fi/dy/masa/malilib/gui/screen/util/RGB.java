package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.gui.screen.ColorSelectScreen;

import java.awt.*;

public class RGB {
    private final float r;
    private final float g;
    private final float b;

    public float getR() {
        return r;
    }

    public float getG() {
        return g;
    }

    public float getB() {
        return b;
    }

    // do not apply bad data
    // 1, 1, 1
    private RGB(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public static RGB ofFFF(float r, float g, float b) {
        return new RGB(r, g, b);
    }

    public static RGB ofIII(int r, int g, int b) {
        return new RGB((float) r / 255, (float) g / 255, (float) b / 255);
    }

    public static RGB ofARGB(int argb) {
        return ofIII((argb >> 16) & 255, (argb >> 8) & 255, argb & 255);
    }

    public int toColor() {
        return this.toColor(255);
    }

    public int toColor(int a) {
        int[] array = this.standardize();
        return ColorSelectScreen.encodeARGB(a, array[0], array[1], array[2]);
    }

    // 255, 255, 255
    public int[] standardize() {
        return new int[]{(int) (r * 255), (int) (g * 255), (int) (b * 255)};
    }

    public HSV toHSV() {
        int[] ints = this.standardize();
        float[] floats = Color.RGBtoHSB(ints[0], ints[1], ints[2], null);
        return HSV.ofFArray(floats);
    }
}
