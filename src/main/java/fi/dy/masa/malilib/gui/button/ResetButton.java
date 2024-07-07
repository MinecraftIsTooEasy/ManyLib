package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.GuiButtonCommented;
import fi.dy.masa.malilib.util.Constant;
import net.minecraft.I18n;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class ResetButton extends GuiButtonCommented {
    public ResetButton(int index, int x, int y) {
        super(index, x, y, 20, 20, "", I18n.getString("manyLib.gui.button.reset"));
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
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
}
