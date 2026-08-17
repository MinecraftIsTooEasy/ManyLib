package fi.dy.masa.malilib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtils {
    public static int[] decodeARGB(int color) {
        return new int[]{(color & 0xFF000000) >>> 24, (color & 0x00FF0000) >>> 16, (color & 0x0000FF00) >>> 8, color & 0x000000FF};
    }

    public static int encodeARGB(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    /**
     * Parses the given string as a hexadecimal value, if it begins with '#' or '0x'.
     * Otherwise tries to parse it as a regular base 10 integer.
     */
    public static int getColor(String colorStr, int defaultColor) {
        Pattern pattern = Pattern.compile("(?:0x|#)([a-fA-F0-9]{1,8})");
        Matcher matcher = pattern.matcher(colorStr);

        if (matcher.matches()) {
            try {
                return (int) Long.parseLong(matcher.group(1), 16);
            } catch (NumberFormatException e) {
                return defaultColor;
            }
        }

        try {
            return Integer.parseInt(colorStr, 10);
        } catch (NumberFormatException e) {
            return defaultColor;
        }
    }
}
