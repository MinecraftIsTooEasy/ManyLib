package fi.dy.masa.malilib.client.gui.screen.interfaces;

public interface ScreenWithPages extends Scrollable {
    boolean isVisible(int index);

    void setVisibilities();
}
