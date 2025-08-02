package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.interfaces.PagedElement;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import net.minecraft.MathHelper;

import java.util.ArrayList;
import java.util.List;

public abstract class LegacyListScreen<T extends WidgetBase> extends GuiBase implements PagedElement {
    private int page;
    protected boolean singlePage;
    protected ScrollBar<?> scrollBar;
    protected final List<T> entries = new ArrayList<>();

    @Override
    public void initGui() {
        super.initGui();
        this.scrollBar = ScreenConstants.getScrollBar(this, this.getPageCapacity(), 0);// dummy
        this.addWidget(this.scrollBar);
    }

    private boolean dirty;

    @Override
    protected void tick() {
        super.tick();
        if (this.dirty) {
            this.entries.forEach(this::removeWidget);
            this.entries.clear();
            for (int i = this.page; i < this.getContentSize() && i < this.page + this.getPageCapacity(); i++) {
                T entry = this.createEntry(i, i - this.page);
                this.entries.add(entry);
                this.addWidget(entry);
            }
            this.dirty = false;
        }
        if (!this.singlePage) this.wheelListener();
    }

    protected abstract T createEntry(int realIndex, int relativeIndex);

    @Override
    public void setPage(int page) {
        int oldSPage = this.page;
        this.page = MathHelper.clamp_int(page, 0, this.getMaxPage());
        if (page != oldSPage) this.onPageChange();
    }

    @Override
    public int getPage() {
        return this.page;
    }

    protected void onPageChange() {
        this.markDirty();
        if (!this.singlePage) this.scrollBar.updateRatioByScreen(this.page);
    }

    protected void onContentChange() {
        this.page = 0;
        this.singlePage = this.getContentSize() <= this.getPageCapacity();
        this.scrollBar.updateArguments(!this.singlePage);
        this.markDirty();
    }

    protected void markDirty() {
        this.dirty = true;
    }

    protected void resetPage(){
        this.page = 0;
    }
}
