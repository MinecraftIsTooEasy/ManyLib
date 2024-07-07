package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.GuiButtonCommented;
import fi.dy.masa.malilib.util.Constant;
import net.minecraft.I18n;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class PageButton extends GuiButtonCommented {
    protected boolean isPageDown;

    public PageButton(int index, int x, int y, boolean isPageDown) {
        super(index, x, y, 20, 20, "", I18n.getString(isPageDown ? "manyLib.gui.button.pageDown" : "manyLib.gui.button.pageUp"));
        this.isPageDown = isPageDown;
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        if (!this.drawButton) {
            return;
        }
        minecraft.getTextureManager().bindTexture(Constant.buttonTexturesManyLib);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        int textureY = 0;
        if (this.enabled) {
            textureY = this.height;
            if (this.field_82253_i) {
                textureY += this.height;
            }
        }
        int textureX = this.isPageDown ? this.width : this.width * 2;
        this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY, this.width, this.height);
        this.tryDrawComment(minecraft.currentScreen, par2, par3);
    }
}
