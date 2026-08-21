package fi.dy.masa.malilib.client.gui.widgets.text;

import java.util.regex.Pattern;

public class TextFieldInteger extends TextField {
    private static final Pattern PATTER_NUMBER = Pattern.compile("-?[0-9]*");

    public TextFieldInteger(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.setTextPredicate(input -> input.isEmpty() || PATTER_NUMBER.matcher(input).matches());
    }
}
