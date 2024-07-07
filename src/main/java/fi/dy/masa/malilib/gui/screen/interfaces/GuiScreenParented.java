package fi.dy.masa.malilib.gui.screen.interfaces;

import net.minecraft.GuiScreen;

public class GuiScreenParented extends GuiScreen implements ScreenWithParent {
    protected GuiScreen parentScreen;
    protected String screenTitle;

    public GuiScreenParented(GuiScreen parentScreen, String screenTitle) {
        this.parentScreen = parentScreen;
        this.screenTitle = screenTitle;
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 20, 16777215);
        super.drawScreen(i, j, f);
    }

    public void drawDefaultScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        if (par2 == 1) {
            this.leaveThisScreen();
        }
    }

    @Override
    public void leaveThisScreen() {
        this.mc.displayGuiScreen(this.parentScreen);
    }
}
