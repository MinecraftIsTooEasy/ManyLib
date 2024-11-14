package fi.dy.masa.malilib.gui.widgets;

import java.util.regex.Pattern;

public class WidgetTextFieldDouble extends WidgetTextField {
    private static final Pattern PATTER_NUMBER = Pattern.compile("^-?([0-9]+(\\.[0-9]*)?)?");

    public WidgetTextFieldDouble(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTextPredicate(input -> input.isEmpty() || PATTER_NUMBER.matcher(input).matches());
    }
}
