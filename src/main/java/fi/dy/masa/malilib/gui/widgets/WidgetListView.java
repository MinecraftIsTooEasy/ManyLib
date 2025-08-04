package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusElement;
import net.minecraft.MathHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class WidgetListView<T extends WidgetBase> extends WidgetContainer implements StatusElement {
    private int status;
    protected boolean singlePage;
    protected ScrollBar scrollBar;
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
            T entry = this.createEntry(i, i - this.status);
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

        this.markSinglePage();

        this.reloadEntries();
    }

    protected abstract T createEntry(int realIndex, int relativeIndex);

    protected abstract ScrollBar createScrollBar();

    @Override
    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = MathHelper.clamp_int(status, 0, this.getMaxStatus());
        if (status != oldStatus) this.onStatusChange();
    }

    public void onStatusChange() {
        this.markDirty();
        if (!this.singlePage) this.scrollBar.updateRatioByScreen(this.status);
    }

    public void onContentChange() {
        this.resetStatus();
        this.markSinglePage();
        this.markDirty();
    }

    public void markDirty() {
        this.dirty = true;
    }

    protected void markSinglePage() {
        this.singlePage = this.getContentSize() <= this.getPageCapacity();
        this.scrollBar.updateArguments(!this.singlePage);
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
