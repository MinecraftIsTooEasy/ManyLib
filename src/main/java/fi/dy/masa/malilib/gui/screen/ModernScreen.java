package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.Drawable;
import fi.dy.masa.malilib.gui.Element;
import fi.dy.masa.malilib.gui.ParentElement;
import net.minecraft.GuiScreen;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

public class ModernScreen extends GuiScreen implements ParentElement, Drawable {
    private final DrawContext DUMMY = new DrawContext();
    private @Nullable Element focused;

    @Deprecated
    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int dWheel = Mouse.getDWheel();
        if (dWheel != 0) {
            this.mouseScrolled(mouseX, mouseY, dWheel);
        }
        this.render(DUMMY, mouseX, mouseY, partialTicks);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawDefaultBackground();
    }

    @Deprecated
    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
    }

    @Deprecated
    @Override
    protected final void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
        if (mouseButton == -1) {
            this.mouseMoved(mouseX, mouseY);
        } else {
            this.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Deprecated
    @Override
    protected final void keyTyped(char charIn, int keyCode) {
        this.charTyped(charIn, keyCode);
    }

    @Deprecated
    @Override
    public final void updateScreen() {
        super.updateScreen();
        this.tick();
    }

    protected void tick() {
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        if (this.focused != null) {
            this.focused.setFocused(false);
        }

        if (focused != null) {
            focused.setFocused(true);
        }

        this.focused = focused;
    }

    @Override
    public @Nullable Element getFocused() {
        return this.focused;
    }
}
