package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusScreen;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import net.minecraft.MathHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class ListScreen<T extends WidgetBase> extends GuiBase implements StatusScreen {
    private int status;
    protected boolean singlePage;
    protected ScrollBar<?> scrollBar;
    protected final List<T> entries = new ArrayList<>();

    @Override
    public void initGui() {
        super.initGui();
        this.scrollBar = ScreenConstants.getScrollBar(this, this.getMaxCapacity(), 0);// dummy
        this.addWidget(this.scrollBar);
    }

    private boolean shouldUpdateEntries;

    @Override
    protected void tickScreen() {
        super.tickScreen();
        if (this.shouldUpdateEntries) {
            this.entries.forEach(this::removeWidget);
            this.entries.clear();
            for (int i = this.status; i < this.getContentSize() && i < this.status + this.getMaxCapacity(); i++) {
                T entry = this.createEntry(i, i - this.status);
                this.entries.add(entry);
                this.addWidget(entry);
            }
            this.shouldUpdateEntries = false;
        }
        if (!this.singlePage) this.wheelListener();
    }

    protected abstract T createEntry(int realIndex, int relativeIndex);

    @Override
    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = MathHelper.clamp_int(status, 0, this.getMaxStatus());
        if (status != oldStatus) this.onStatusChange();
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    protected void onStatusChange() {
        this.markShouldUpdateEntries();
        if (!this.singlePage) this.scrollBar.updateRatioByScreen(this.status);
    }

    protected void onContentChange() {
        this.status = 0;
        this.singlePage = this.getContentSize() <= this.getMaxCapacity();
        this.scrollBar.updateArguments(!this.singlePage);
        this.markShouldUpdateEntries();
    }

    protected void markShouldUpdateEntries() {
        this.shouldUpdateEntries = true;
    }

    protected void resetStatus(){
        this.status = 0;
    }
}
