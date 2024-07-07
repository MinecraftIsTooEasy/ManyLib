package fi.dy.masa.malilib.gui.screen.interfaces;

public interface StatusScreen extends ScrollableScreen {
    void setStatus(int status);

    int getStatus();

    int getMaxStatus();

    default void setStatusByRatio(float ratio) {
        this.setStatus((int) (this.getMaxStatus() * ratio));
    }

    default void addStatus(int addend) {
        this.setStatus(this.getStatus() + addend);
    }
}
