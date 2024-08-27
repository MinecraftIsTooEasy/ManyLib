package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonWidget;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

class SlideableToggleButton extends ButtonWidget {
    private boolean useSlider;

    public SlideableToggleButton(int x, int y, boolean useSlider, PressAction onPress) {
        super(x, y, 16, 16, "", onPress);
        this.useSlider = useSlider;
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        if (this.drawButton) {
            IGuiIcon icon = this.useSlider ? ManyLibIcons.SlideIdentifier : ManyLibIcons.InputIdentifier;
            RenderUtils.bindTexture(icon.getTexture());
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            icon.renderAt(this.xPosition, this.yPosition, 0, true, this.field_82253_i);
        }
    }

    public void toggle() {
        this.useSlider = !this.useSlider;
    }

}
