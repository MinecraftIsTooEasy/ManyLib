package fi.dy.masa.malilib.gui.screen.interfaces;

import org.lwjgl.input.Mouse;

public interface Scrollable {
    void scroll(boolean isScrollDown);

    default void wheelListener() {
        int wheelStatus = Mouse.getDWheel();
        if (wheelStatus == 0) return;
        this.scroll(wheelStatus < 0);
    }
}
