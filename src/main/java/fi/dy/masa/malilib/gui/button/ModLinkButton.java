package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.render.RenderUtils;

public class ModLinkButton extends ButtonGeneric {
    public ModLinkButton(int x, int y, int width, int height, String message, String tooltip) {
        super(x, y, width, height, message, null);
        this.setHoverStrings(tooltip);
        this.setTextCentered(false);
        this.setRenderDefaultBackground(false);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.visible) {
            RenderUtils.drawOutline(this.x, this.y, this.width, this.height, GuiBase.COLOR_WHITE);
            this.bindTexture(ManyLibIcons.ARROW_DOWN.getTexture());
            ManyLibIcons.ARROW_DOWN.renderAt(this.x + this.width - 20, this.y + 1, 0, false, false);
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }
}

