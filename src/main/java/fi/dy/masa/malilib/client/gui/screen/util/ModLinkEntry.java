package fi.dy.masa.malilib.client.gui.screen.util;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.client.util.RenderUtils;

public class ModLinkEntry extends ButtonGeneric {
    public static final int HeightUnit = 16;

    public ModLinkEntry(boolean present, String content, IButtonActionListener listener) {
        super(0, 0, 0, 0, content, listener);
        if (present) this.setDisplayString(GuiBase.TXT_AQUA + this.getDisplayString());
        this.setTextCentered(false);
        this.setRenderDefaultBackground(false);
        this.setVisible(false);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        if (this.visible) {
            RenderUtils.drawOutline(this.x, this.y, this.width, this.height, GuiBase.COLOR_WHITE);
        }
    }
}
