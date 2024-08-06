package fi.dy.masa.malilib.gui.screen.util;

public class WidthAdder {
    private int width;

    public WidthAdder(int width) {
        this.width = width;
    }

    int addWidth(int addend) {
        this.width += addend;
        return this.width;
    }

    public int getWidth() {
        return this.width;
    }
}
