package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;

/**
 * Status: 0 for the top, max for the bottom
 */
public interface StatusElement extends Scrollable {
    void setStatus(int status);

    int getStatus();

    /**
     * @return Total number of elements
     */
    int getContentSize();

    /**
     * @return The number of elements to show in a single page
     */
    int getPageCapacity();

    @Override
    default void scroll(boolean isScrollDown) {
        if (isScrollDown && this.getStatus() + this.getPageCapacity() < this.getContentSize()) {
            this.addStatus(ScreenConstants.oneScroll);
        }
        if (!isScrollDown && this.getStatus() > 0) this.addStatus(-ScreenConstants.oneScroll);
    }

    default int getMaxStatus() {
        return StatusElement.getMaxStatus(this.getPageCapacity(), this.getContentSize());
    }

    default void setPageByRatio(float ratio) {
        this.setStatus((int) (this.getMaxStatus() * ratio));
    }

    default void addStatus(int status) {
        this.setStatus(this.getStatus() + status);
    }

    static int getMaxStatus(int pageCapacity, int contentSize) {
        if (contentSize > pageCapacity) {
            return contentSize - pageCapacity;
        } else {
            return 0;
        }
    }
}
