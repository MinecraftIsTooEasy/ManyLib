package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;

public interface StatusScreen extends ScrollableScreen {
    void setStatus(int status);

    int getStatus();

    int getContentSize();

    int getMaxCapacity();

    @Override
    default void scroll(boolean isScrollDown) {
        if (isScrollDown && this.getStatus() + this.getMaxCapacity() < this.getContentSize()) {
            this.addStatus(ScreenConstants.oneScroll);
        }
        if (!isScrollDown && this.getStatus() > 0) this.addStatus(-ScreenConstants.oneScroll);
    }

    default int getMaxStatus() {
        return StatusScreen.getMaxStatus(this.getMaxCapacity(), this.getContentSize());
    }

    default void setStatusByRatio(float ratio) {
        this.setStatus((int) (this.getMaxStatus() * ratio));
    }

    default void addStatus(int addend) {
        this.setStatus(this.getStatus() + addend);
    }

    static int getMaxStatus(int pageCapacity, int size) {
        if (size > pageCapacity) {
            return size - pageCapacity;
        } else {
            return 0;
        }
    }
}
