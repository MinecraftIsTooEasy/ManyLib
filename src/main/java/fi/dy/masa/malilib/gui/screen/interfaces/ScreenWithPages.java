package fi.dy.masa.malilib.gui.screen.interfaces;

public interface ScreenWithPages extends ScrollableScreen {
    boolean isVisible(int index);

    void setVisibilities();
}
