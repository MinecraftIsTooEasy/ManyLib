package fi.dy.masa.malilib.client.gui.widgets.text;

import fi.dy.masa.malilib.client.gui.DrawContext;

public class TextFieldDefaulted extends TextField {
    protected String defaultText;

    public TextFieldDefaulted(int x, int y, int width, int height, String defaultText) {
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
