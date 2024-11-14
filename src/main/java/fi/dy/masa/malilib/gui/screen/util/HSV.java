package fi.dy.masa.malilib.gui.screen.util;

import java.awt.*;

public class HSV {
    private final int h;
    private final float s;
    private final float v;

    public int getH() {
        return h;
    }

    public float getS() {
        return s;
    }

    public float getV() {
        return v;
    }

    // do not apply bad data
    // 359, 1, 1
    private HSV(int h, float s, float v) {
        this.h = h;
        this.s = s;
        this.v = v;
    }

    public static HSV ofIFF(int h, float s, float v) {
        return new HSV(h, s, v);
    }

    // 359, 100, 100
    public static HSV ofIII(int h, int s, int v) {
        return new HSV(h, (float) s / 100, (float) v / 100);
    }

    // 1, 1, 1
    public static HSV ofFFF(float h, float s, float v) {
        return new HSV((int) (h * 359), s, v);
    }

    // 359, 1, 100
    public static HSV ofIFI(int h, float s, int v) {
        return new HSV(h, s, (float) v / 100);
    }

    // 359, 100, 1
    public static HSV ofIIF(int h, int s, float v) {
        return new HSV(h, (float) s / 100, v);
    }

    // 1,1,1
    public static HSV ofFArray(float[] array) {
        return ofFFF(array[0], array[1], array[2]);
    }

    public static HSV ofARGB(int argb) {
        return RGB.ofARGB(argb).toHSV();
    }

    //359, 100, 100
    public int[] standardize() {
        return new int[]{h, (int) (s * 100), (int) (v * 100)};
    }

    public int toColor() {
        return Color.HSBtoRGB((float) h / 359, s, v);
    }

    public int toColor(int a) {
        return ((this.toColor() << 8) >>> 8) | (a << 24);
    }

    public RGB toRGB() {
        return RGB.ofARGB(Color.HSBtoRGB((float) h / 359, s, v));
    }
}
