package fi.dy.masa.malilib.util;

public class ColorUtils {
    public static int[] decodeARGB(int color) {
        return new int[]{(color & 0xFF000000) >>> 24, (color & 0x00FF0000) >>> 16, (color & 0x0000FF00) >>> 8, color & 0x000000FF};
    }

    public static int encodeARGB(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
