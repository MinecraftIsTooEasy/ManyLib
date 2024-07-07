package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.IToggleableElement;
import fi.dy.masa.malilib.util.Constant;
import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class ToggleButton extends GuiButton implements IToggleableElement {
    private boolean value;

    public ToggleButton(int index, int x, int y) {
        super(index, x, y, 20, 20, "");
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        if (this.drawButton) {
            minecraft.getTextureManager().bindTexture(Constant.buttonTexturesManyLib);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int textureX = 0;
            int textureY = this.value ? 60 : 80;
            this.drawTexturedModalRect(this.xPosition, this.yPosition, textureX, textureY, 20, 20);
        }
    }

    @Override
    public void toggle() {
        this.value = !this.value;
    }

    public boolean getIsOn() {
        return this.value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}

