package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.I18n;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class PageButton extends ButtonWidget {
    protected boolean isPageDown;

    public PageButton(int x, int y, boolean isPageDown, PressAction onPress) {
        super(x, y, 20, 20, "", onPress);
        this.setTooltip(I18n.getString(isPageDown ? "manyLib.gui.button.pageDown" : "manyLib.gui.button.pageUp"));
        this.isPageDown = isPageDown;
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        if (!this.drawButton) {
            return;
        }
        IGuiIcon icon = this.isPageDown ? ManyLibIcons.PageDownButton : ManyLibIcons.PageUpButton;
        RenderUtils.bindTexture(icon.getTexture());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        icon.renderAt(this.xPosition, this.yPosition, 0, this.enabled, this.enabled && this.field_82253_i);
        this.tryDrawTooltip(minecraft.currentScreen, par2, par3);
    }
}
