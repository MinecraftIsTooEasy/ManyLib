package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class ResetButton extends ButtonWidget {
    protected ResetButton(int x, int y, int width, int height, String message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
    }

    public ResetButton(int x, int y, String tooltip, PressAction onPress) {
        this(x, y, 20, 20, "", onPress);
        this.setTooltip(tooltip);
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        if (!this.drawButton) {
            return;
        }
        IGuiIcon icon = ManyLibIcons.ResetButton;
        RenderUtils.bindTexture(icon.getTexture());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        icon.renderAt(this.xPosition, this.yPosition, 0, this.enabled, this.enabled && this.field_82253_i);
        this.tryDrawTooltip(minecraft.currentScreen, par2, par3);
    }
}
