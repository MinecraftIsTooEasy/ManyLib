package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.screen.ColorSelectScreen;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

class ConfigItemColor extends ConfigItemInputBox<ConfigColor> {
    final ColorBoard colorBoard;

    public ConfigItemColor(int index, ConfigColor config, GuiScreen screen) {
        super(index, config, screen);
        this.textFieldWrapper = ScreenConstants.getWrapperForColor(index, config, screen);
        this.textFieldWrapper.setText(this.config.getColorString());
        this.colorBoard = ScreenConstants.getColorBoard(index, config, screen);
        this.colorBoard.setHoverStrings(StringUtils.translate("manyLib.gui.comment.clickToSelectColor"));
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        this.colorBoard.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.colorBoard.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawHoverText(mouseX, mouseY, this.colorBoard.getHoverStrings(), drawContext);
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        if (this.colorBoard.isMouseOver(mouseX, mouseY)) {
            GuiBase.openGui(new ColorSelectScreen(GuiUtils.getCurrentScreen(), this.config));
            return true;
        }
        return false;
    }
}
