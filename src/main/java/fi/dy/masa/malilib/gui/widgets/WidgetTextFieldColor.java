package fi.dy.masa.malilib.gui.widgets;

import java.util.regex.Pattern;

public class WidgetTextFieldColor extends WidgetTextField {

    private static final Pattern PATTER_COLOR = Pattern.compile("(?:0x|#)([a-fA-F0-9]{1,8})");

    public WidgetTextFieldColor(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTextPredicate(input -> input.isEmpty() || PATTER_COLOR.matcher(input).matches());
    }
}
