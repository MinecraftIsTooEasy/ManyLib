package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.mixin.interfaces.IPositionMutable;
import net.minecraft.GuiTextField;

import java.util.function.Predicate;

public class WidgetTextField extends GuiTextField {
    Predicate<String> predicate = s -> true;

    public WidgetTextField(int x, int y, int width, int height) {
        super(RenderUtils.fontRenderer(), x, y, width, height);
    }

    public void setTextPredicate(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void setText(String string) {
        if (this.predicate.test(string)) {
            super.setText(string);
        }
    }

    public void setPosition(int x, int y) {
        ((IPositionMutable) this).manylib$setPosition(x, y);
    }

    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
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

    public boolean charTyped(char charIn, int modifiers) {
        return this.textboxKeyTyped(charIn, modifiers);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.xPos && mouseX < this.xPos + this.width &&
                mouseY >= this.yPos && mouseY < this.yPos + this.height;
    }
}
