package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;

/**
 * The page counts from 0.
 */
public interface PagedElement extends Scrollable {
    void setPage(int page);

    int getPage();

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
        if (isScrollDown && this.getPage() + this.getPageCapacity() < this.getContentSize()) {
            this.turnPage(ScreenConstants.oneScroll);
        }
        if (!isScrollDown && this.getPage() > 0) this.turnPage(-ScreenConstants.oneScroll);
    }

    default int getMaxPage() {
        return PagedElement.getMaxPage(this.getPageCapacity(), this.getContentSize());
    }

    default void setPageByRatio(float ratio) {
        this.setPage((int) (this.getMaxPage() * ratio));
    }

    default void turnPage(int pages) {
        this.setPage(this.getPage() + pages);
    }

    static int getMaxPage(int pageCapacity, int contentSize) {
        if (contentSize > pageCapacity) {
            return contentSize - pageCapacity;
        } else {
            return 0;
        }
    }
}
