package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.layer.Layer;
import fi.dy.masa.malilib.gui.screen.LayeredScreen;
import fi.dy.masa.malilib.gui.widgets.WidgetScrollHandler;
import net.minecraft.GuiScreen;

public abstract class ScreenPaged extends LayeredScreen implements ScreenWithPages {
    protected final int rows;
    protected int columns;
    protected int pageCapacity;
    protected int pageIndex;
    protected int maxPageIndex;

    public ScreenPaged(GuiScreen parent, int rows, int columns) {
        this(parent, rows, columns, 1);
    }

    public ScreenPaged(GuiScreen parent, int rows, int columns, int configSize) {
        super();
        this.setParent(parent);
        this.rows = rows;
        this.columns = columns;
        this.pageCapacity = rows * columns;
        this.updatePageCount(configSize);
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);
        layer.addWidget(new WidgetScrollHandler(this));
    }

    protected void updatePageCount(int configSize) {
        this.maxPageIndex = (configSize - 1) / this.pageCapacity;
    }

    protected int getLeftBorder() {
        return this.width / 2 - 155;
    }

    protected int getButtonPosX(int index) {
        index %= pageCapacity;
        return this.getLeftBorder() + index % columns * 160;
    }

    protected int getButtonPosY(int index) {
        index %= pageCapacity;
        return this.height / 6 + 24 * (index / columns) - 6;
    }

    public boolean isVisible(int index) {
        return index >= this.pageIndex * pageCapacity && index < (this.pageIndex + 1) * pageCapacity;
    }

    public void scroll(boolean isPageDown) {
        if (this.maxPageIndex == 0) return;
        if (isPageDown && this.canPageDown()) {
            this.pageIndex++;
        }
        if (!isPageDown && this.canPageUp()) {
            this.pageIndex--;
        }
        this.setVisibilities();
    }

    protected boolean canPageUp() {
        return this.pageIndex > 0;
    }

    protected boolean canPageDown() {
        return this.pageIndex < this.maxPageIndex;
    }
}