package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.GuiTextField;

public class WidgetTextField extends GuiTextField {

    public WidgetTextField(int x, int y, int width, int height) {
        super(RenderUtils.fontRenderer(), x, y, width, height);
    }

    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.drawTextBox();// checked visible in impl
    }

    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        this.mouseClicked(mouseX, mouseY, mouseButton);
        return this.isFocused();
    }

    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (!this.isMouseOver(mouseX, mouseY) && this.isFocused()) {
            this.setFocused(false);
        }
    }

    public boolean onCharTyped(char charIn, int modifiers) {
        return this.textboxKeyTyped(charIn, modifiers);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.xPos && mouseX < this.xPos + this.width &&
                mouseY >= this.yPos && mouseY < this.yPos + this.height;
    }
}
