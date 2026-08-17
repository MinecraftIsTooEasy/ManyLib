package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.screen.interfaces.StatusElement;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import net.minecraft.MathHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class WidgetListView<T extends WidgetBase> extends WidgetContainer implements StatusElement {
    private int status;
    protected boolean singlePage;
    protected WidgetScrollBar scrollBar;
    protected WidgetScrollHandler scrollHandler;
    protected final List<T> entries = new ArrayList<>();
    private boolean dirty = false;

    protected WidgetListView(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.dirty) {
            this.reloadEntries();
            this.dirty = false;
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    protected void reloadEntries() {
        this.entries.forEach(this::removeWidget);
        this.entries.clear();
        for (int i = this.status; i < this.getContentSize() && i < this.status + this.getPageCapacity(); i++) {
            int relativeIndex = i - this.status;
            T entry = this.createEntry(i, relativeIndex);
            this.placeEntry(entry, relativeIndex);
            this.entries.add(entry);
            this.addWidget(entry);
        }
    }

    @Override
    public void init() {
        super.init();
        this.createListWidgets();
    }

    protected void createListWidgets() {
        this.scrollBar = this.createScrollBar();
        this.addWidget(this.scrollBar);
        this.scrollHandler = new WidgetScrollHandler(this);
        this.addWidget(this.scrollHandler);

        this.checkPageCount();

        this.reloadEntries();
    }

    protected WidgetScrollBar createScrollBar() {
        return ScreenConstants.getScrollBar(this);
    }

    protected abstract T createEntry(int realIndex, int relativeIndex);

    // empty this to skip placing
    protected void placeEntry(T entry, int relativeIndex) {
        int entryHeight = this.getEntryHeight();
        entry.setDimensions(this.getX(), this.getY() + relativeIndex * entryHeight, this.getWidth(), entryHeight);
    }

    public abstract int getEntryHeight();

    @Override
    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = MathHelper.clamp_int(status, 0, this.getMaxStatus());
        if (status != oldStatus) this.onStatusChange();
    }

    public void onStatusChange() {
        this.markDirty();
        if (!this.singlePage) this.scrollBar.onStatusChanged(this.status);
    }

    public void onContentChange() {
        this.resetStatus();
        this.checkPageCount();
        this.markDirty();
    }

    public void markDirty() {
        this.dirty = true;
    }

    protected void checkPageCount() {
        this.singlePage = this.getContentSize() <= this.getPageCapacity();
        boolean visible = !this.singlePage;
        this.scrollBar.setVisible(visible);
        this.scrollBar.updateArguments();
        this.scrollHandler.setEnabled(!this.singlePage);
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    public void resetStatus() {
        this.status = 0;
    }
}
