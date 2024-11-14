package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;

public class WidgetTextFieldWithDefaultText extends WidgetTextField {
    protected String defaultText;

    public WidgetTextFieldWithDefaultText(int x, int y, int width, int height, String defaultText) {
        super(x, y, width, height);
        this.defaultText = defaultText;
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        super.render(drawContext, mouseX, mouseY, partialTicks);
        if (this.getVisible() && this.getText().isEmpty()) {
            int var7 = this.enableBackgroundDrawing ? this.xPos + 4 : this.xPos;
            int var8 = this.enableBackgroundDrawing ? this.yPos + (this.height - 8) / 2 : this.yPos;
            this.fontRenderer.drawStringWithShadow(this.defaultText, var7, var8, 7368816);
        }
    }
}
