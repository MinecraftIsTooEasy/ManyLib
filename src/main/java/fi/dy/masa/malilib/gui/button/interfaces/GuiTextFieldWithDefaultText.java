package fi.dy.masa.malilib.gui.button.interfaces;

import net.minecraft.FontRenderer;
import net.minecraft.GuiTextField;

public class GuiTextFieldWithDefaultText extends GuiTextField {
    protected String defaultText;

    public GuiTextFieldWithDefaultText(FontRenderer fontRenderer, int xPos, int yPos, int width, int height, String defaultText) {
        super(fontRenderer, xPos, yPos, width, height);
        this.defaultText = defaultText;
    }

    @Override
    public void drawTextBox() {
        super.drawTextBox();
        if (this.getVisible() && this.getText().isEmpty()) {
            int var7 = this.enableBackgroundDrawing ? this.xPos + 4 : this.xPos;
            int var8 = this.enableBackgroundDrawing ? this.yPos + (this.height - 8) / 2 : this.yPos;
            this.fontRenderer.drawStringWithShadow(this.defaultText, var7, var8, 7368816);
        }
    }
}
