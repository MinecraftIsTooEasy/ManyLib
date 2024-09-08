package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.interfaces.TooltipWidget;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import org.jetbrains.annotations.Nullable;

class ColorBoard extends WidgetBase implements TooltipWidget<ColorBoard> {
    ConfigColor configColor;
    boolean mouseOver;
    String tooltip;

    ColorBoard(ConfigColor configColor, int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        this.configColor = configColor;
//        this.tooltip("点我打开取色板");// TODO
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        int colorInteger = this.configColor.getColorInteger();
        drawContext.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, colorInteger, colorInteger);
        this.mouseOver = mouseX >= this.x && mouseX <= this.x + width && mouseY >= this.y && mouseY <= this.y + height;
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.tryDrawTooltip(mouseX, mouseY, drawContext);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
//        Minecraft minecraft = Minecraft.getMinecraft();
//        minecraft.displayGuiScreen(new ColorSelectScreen(minecraft.currentScreen));// TODO
        return true;
    }

    @Override
    public ColorBoard tooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Nullable
    @Override
    public String getTooltip() {
        return this.tooltip;
    }

    @Override
    public boolean shouldDrawTooltip() {
        return this.mouseOver;
    }
}
