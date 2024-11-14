package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.render.RenderUtils;

public class DropDownEntry extends ButtonGeneric {
    public static final int Width = 100;
    public static final int HeightUnit = 16;

    public DropDownEntry(int x, int y, boolean present, String content, IButtonActionListener listener) {
        super(x, y, Width, HeightUnit, content, listener);
        if (present) this.setDisplayString(GuiBase.TXT_AQUA + this.displayString);
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
