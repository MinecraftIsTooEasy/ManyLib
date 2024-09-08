package fi.dy.masa.malilib.gui;

import net.minecraft.FontRenderer;
import net.minecraft.Gui;

public class DrawContext extends Gui {

    public DrawContext() {
    }

    public void drawText(FontRenderer fontRenderer, String text, int x, int y, int color, boolean shadow) {
        fontRenderer.drawString(text, x, y, color, shadow);
    }

    public void drawTextWithShadow(FontRenderer fontRenderer, String text, int x, int y, int color) {
        fontRenderer.drawStringWithShadow(text, x, y, color);
    }

    public void drawCenteredTextWithShadow(FontRenderer fontRenderer, String text, int x, int y, int color) {
        this.drawText(fontRenderer, text, x - fontRenderer.getStringWidth(text) / 2, y, color, true);
    }
}
