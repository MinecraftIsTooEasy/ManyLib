package fi.dy.masa.malilib.gui.screen.util;

public class WidthAdder {
    private int width;

    public WidthAdder(int width) {
        this.width = width;
    }

    public void addWidth(int addend) {
        this.width += addend;
    }

    public int getWidth() {
        return this.width;
    }
}
