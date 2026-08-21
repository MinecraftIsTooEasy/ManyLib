package fi.dy.masa.malilib.client.gui.widgets.text;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.interfaces.Renderable;
import fi.dy.masa.malilib.client.gui.event.GuiEventListener;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.mixin.interfaces.IPositionMutable;
import net.minecraft.GuiTextField;

import java.util.function.Predicate;

public class TextField extends GuiTextField implements GuiEventListener, Renderable {
    Predicate<String> predicate = s -> true;

    public TextField(int x, int y, int width, int height) {
        super(RenderUtils.fontRenderer(), x, y, width, height);
    }

    public void setTextPredicate(Predicate<String> predicate) {
        this.predicate = predicate;
    }

    public void setPosition(int x, int y) {
        ((IPositionMutable) this).manylib$setPosition(x, y);
    }

    @Override
    public void setText(String string) {
        if (this.predicate.test(string)) {
            super.setText(string);
        }
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        this.drawTextBox();// checked visible in impl
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        super.mouseClicked((int) mouseX, (int) mouseY, button);
        return this.isFocused();
    }

    @Override
    public boolean charTyped(char charIn, int modifiers) {
        return this.textboxKeyTyped(charIn, modifiers);
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.xPos && mouseX < this.xPos + this.width &&
                mouseY >= this.yPos && mouseY < this.yPos + this.height;
    }
}
