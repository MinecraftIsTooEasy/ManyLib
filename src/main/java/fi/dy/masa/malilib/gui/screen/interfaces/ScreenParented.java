package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import net.minecraft.GuiScreen;

public class ScreenParented extends GuiScreen implements ScreenWithParent {
    protected GuiScreen parentScreen;
    protected WidgetContainer container;

    public ScreenParented(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public final void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        this.render(i, j, false, new DrawContext());
    }

    protected void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.drawDefaultBackground();
        if (this.container != null) {
            this.container.render(mouseX, mouseY, selected, drawContext);
            this.container.postRenderHovered(mouseX, mouseY, selected, drawContext);
        }
    }

    public GuiScreen getParentScreen() {
        return this.parentScreen;
    }

    public WidgetBase getHoveredSubWidget() {
        return this.container.getHoveredSubWidget();
    }

    @Override
    public final void updateScreen() {
        super.updateScreen();
        this.update();
    }

    protected void update() {
        if (this.container != null) this.container.update();
    }

    @Override
    public void initGui() {
        if (this.container != null) {
            this.container.syncSize(this.width, this.height);
            this.container.initWidgets();
        }
    }

    @Override
    protected final void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.container != null && mouseButton == 0) this.onMouseClicked(mouseX, mouseY, mouseButton);
    }

    protected boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        return this.container.onMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected final void mouseMovedOrUp(int par1, int par2, int par3) {
        super.mouseMovedOrUp(par1, par2, par3);
        this.onMouseReleased(par1, par2, par3);
    }

    protected void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.container != null) this.container.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    protected final void keyTyped(char par1, int par2) {
        this.onCharTyped(par1, par2);
    }

    protected boolean onCharTyped(char charIn, int modifiers) {
        if (this.container.onCharTyped(charIn, modifiers)) return true;
        if (modifiers == 1) {
            this.leaveThisScreen();
            return true;
        }
        return false;
    }

    @Override
    public void leaveThisScreen() {
        this.mc.displayGuiScreen(this.parentScreen);
    }
}
