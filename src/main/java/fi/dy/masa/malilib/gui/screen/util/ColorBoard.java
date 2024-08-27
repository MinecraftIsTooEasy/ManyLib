package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import fi.dy.masa.malilib.gui.button.interfaces.ITooltipElement;
import net.minecraft.GuiScreen;
import org.jetbrains.annotations.Nullable;

class ColorBoard implements ITooltipElement, IInteractiveElement {
    ConfigColor configColor;
    int xPos;
    int yPos;
    int width;
    int height;
    boolean mouseOver;

    ColorBoard(ConfigColor configColor, int xPos, int yPos, int width, int height) {
        this.configColor = configColor;
        this.xPos = xPos;
        this.yPos = yPos;
        this.width = width;
        this.height = height;
    }

    void draw(GuiScreen guiScreen, int x, int y) {
        int colorInteger = this.configColor.getColorInteger();
        guiScreen.drawGradientRect(this.xPos, this.yPos, this.xPos + this.width, this.yPos + this.height, colorInteger, colorInteger);
        this.mouseOver = x >= xPos && x <= xPos + width && y >= yPos && y <= yPos + height;
    }

    @Override
    public void keyTyped(char c, int i) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int click) {
        if (this.mouseOver) {
//            System.out.println("i will call the color board");
        }
    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void setTooltip(String tooltip) {

    }

    @Nullable
    @Override
    public String getTooltip() {
        return "点我打开取色板";
    }

    @Override
    public boolean shouldDrawTooltip() {
        return this.mouseOver;
    }
}
