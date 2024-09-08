package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.gui.DrawContext;
import net.minecraft.GuiScreen;

class ConfigItemColor extends ConfigItemInputBox<ConfigColor> {
    final ColorBoard colorBoard;

    public ConfigItemColor(int index, ConfigColor config, GuiScreen screen) {
        super(index, config, screen);
        this.inputBox = ScreenConstants.getInputBoxForColor(index, config, screen);
        this.colorBoard = ScreenConstants.getColorBoard(index, config, screen);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        this.colorBoard.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.colorBoard.postRenderHovered(mouseX, mouseY, selected, drawContext);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        return this.colorBoard.onMouseClickedImpl(mouseX, mouseY, mouseButton);
    }
}
