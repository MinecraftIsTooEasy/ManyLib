package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigColor;
import net.minecraft.GuiScreen;

class ColorBoard {
    final ConfigColor configColor;
    final int xPos;
    final int yPos;
    final int width;
    final int height;

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
    }
}
