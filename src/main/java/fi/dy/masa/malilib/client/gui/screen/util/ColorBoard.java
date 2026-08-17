package fi.dy.masa.malilib.client.gui.screen.util;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.config.options.ConfigColor;

public class ColorBoard extends WidgetBase {
    ConfigColor configColor;

    public ColorBoard(ConfigColor configColor, int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        this.configColor = configColor;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        int colorInteger = this.configColor.getColorInteger();
        drawContext.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, colorInteger, colorInteger);
    }
}
