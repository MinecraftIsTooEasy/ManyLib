package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.GuiButtonCommented;
import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import fi.dy.masa.malilib.gui.button.interfaces.IToggleableElement;
import fi.dy.masa.malilib.util.Constant;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class PullDownButton extends GuiButtonCommented implements IInteractiveElement, IToggleableElement {
    private boolean expand;

    public PullDownButton(int index, int x, int y, int width, int height, String comment) {
        super(index, x, y, width, height, "测试", comment);
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {// TODO
        if (!this.drawButton) {
            return;
        }
        minecraft.getTextureManager().bindTexture(Constant.buttonTexturesManyLib);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        int n = 0;
        if (this.enabled) {
            n = this.height;
            if (this.field_82253_i) {
                n += this.height;
            }
        }
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, n, this.width, this.height);
        this.tryDrawComment(minecraft.currentScreen, par2, par3);
    }

    @Override
    public void keyTyped(char c, int i) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int click) {
        if (!this.expand) return;

    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void toggle() {
        this.expand = !this.expand;
    }
}
