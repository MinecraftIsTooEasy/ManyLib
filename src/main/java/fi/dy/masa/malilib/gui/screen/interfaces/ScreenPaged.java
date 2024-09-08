package fi.dy.masa.malilib.gui.screen.interfaces;

import net.minecraft.GuiScreen;

public abstract class ScreenPaged extends ScreenParented implements ScreenWithPages {
    protected final int rows;
    protected int columns;
    protected int pageCapacity;
    protected int pageIndex;
    protected int pageCount;

    public ScreenPaged(GuiScreen parentScreen, int rows, int columns) {
        this(parentScreen, rows, columns, 1);
    }

    public ScreenPaged(GuiScreen parentScreen, int rows, int columns, int configSize) {
        super(parentScreen);
        this.rows = rows;
        this.columns = columns;
        this.pageCapacity = rows * columns;
        this.updatePageCount(configSize);
    }

    protected void updatePageCount(int configSize) {
        this.pageCount = (configSize - 1) / this.pageCapacity;
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

    @Override
    protected void update() {
        super.update();
        this.wheelListener();
    }

    public boolean isVisible(int index) {
        return index >= this.pageIndex * pageCapacity && index < (this.pageIndex + 1) * pageCapacity;
    }

    public void scroll(boolean isPageDown) {
        if (this.pageCount == 0) return;
        if (isPageDown) {
            this.pageIndex++;
        } else {
            this.pageIndex--;
        }
        if (this.pageIndex > this.pageCount) {
            this.pageIndex = 0;
        } else if (this.pageIndex < 0) {
            this.pageIndex = this.pageCount;
        }
        this.setVisibilities();
    }
}
