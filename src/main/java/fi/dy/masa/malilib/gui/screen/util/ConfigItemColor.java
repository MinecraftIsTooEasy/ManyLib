package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigColor;
import net.minecraft.GuiScreen;

class ConfigItemColor extends ConfigItemInputBox<ConfigColor> {
    final ColorBoard colorBoard;

    public ConfigItemColor(int index, ConfigColor config, GuiScreen screen) {
        super(index, config, screen);
        this.inputBox = ScreenConstants.getInputBoxForColor(index, config, screen);
        this.colorBoard = ScreenConstants.getColorBoard(index, config, screen);
    }

    @Override
    public void customDraw(GuiScreen guiScreen, int x, int y) {
        super.customDraw(guiScreen, x, y);
        this.colorBoard.draw(guiScreen, x, y);
    }

    @Override
    public void tryDrawComment(GuiScreen guiScreen, int x, int y) {
        super.tryDrawComment(guiScreen, x, y);
//        this.colorBoard.tryDrawComment(guiScreen, x, y);// TODO make a color select screen
    }

    @Override
    public void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click) {
        super.customMouseClicked(guiScreen, mouseX, mouseY, click);
        this.colorBoard.mouseClicked(mouseX, mouseY, click);
    }
}
