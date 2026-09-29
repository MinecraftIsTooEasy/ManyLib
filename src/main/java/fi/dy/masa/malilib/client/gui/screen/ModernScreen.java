package fi.dy.masa.malilib.client.gui.screen;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.event.ContainerEventHandler;
import fi.dy.masa.malilib.client.gui.event.GuiEventListener;
import fi.dy.masa.malilib.client.gui.interfaces.Renderable;
import net.minecraft.GuiScreen;
import org.jspecify.annotations.Nullable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ModernScreen extends GuiScreen implements ContainerEventHandler, Renderable {
    private final List<GuiEventListener> children = new ArrayList<>();
    private @Nullable GuiEventListener focused;
    private boolean isDragging;
    private final DrawContext dummyContext = new DrawContext();
    private @Nullable GuiScreen parent;

    @Deprecated
    @Override
    public void initGui() {
        super.initGui();
        this.reload();
    }

    protected void reload() {
    }

    @Deprecated
    @Override
    public final void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int dWheel = Mouse.getDWheel();
        if (dWheel != 0) {
            this.mouseScrolled(mouseX, mouseY, dWheel);
        }
        this.render(this.dummyContext, mouseX, mouseY, partialTicks);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawDefaultBackground();
    }

    @Deprecated
    @Override
    protected final void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
    }

    @Deprecated
    @Override
    protected final void mouseMovedOrUp(int mouseX, int mouseY, int mouseButton) {
        super.mouseMovedOrUp(mouseX, mouseY, mouseButton);
        this.mouseReleased(mouseX, mouseY, mouseButton);
    }

    @Deprecated
    @Override
    protected final void mouseClickMove(int mouseX, int mouseY, int mouseButton, long timeSinceLastClick) {
        this.mouseMoved(mouseX, mouseY);
    }

    @Deprecated
    @Override
    protected final void keyTyped(char charIn, int keyCode) {
        this.charTyped(charIn, keyCode);
    }

    /**
     * Call this at the end, if you want to use the esc
     */
    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.close();
            return true;
        }
        return false;
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
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    @Override
    public boolean isDragging() {
        return this.isDragging;
    }

    @Override
    public void setDragging(boolean dragging) {
        this.isDragging = dragging;
    }

    @Override
    public void setFocused(@Nullable GuiEventListener focused) {
        if (this.focused != null) {
            this.focused.setFocused(false);
        }

        if (focused != null) {
            focused.setFocused(true);
        }

        this.focused = focused;
    }

    @Override
    public @Nullable GuiEventListener getFocused() {
        return this.focused;
    }

    public @Nullable GuiScreen getParent() {
        return this.parent;
    }

    public void setParent(@Nullable GuiScreen parent) {
        this.parent = parent;
    }

    protected void close() {
        if (this.parent != null) {
            this.mc.displayGuiScreen(this.parent);
        } else {
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }
}
