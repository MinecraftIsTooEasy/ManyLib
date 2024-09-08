package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;

public class PullDownButton extends ButtonGeneric {

    private boolean expand;

    public PullDownButton(int x, int y, int width, int height, String message, String tooltip, IButtonActionListener listener) {
        super(x, y, width, height, message, null);
        this.actionListener = button -> {
            listener.actionPerformedWithButton(button);
            this.expand = !this.expand;
        };
        this.tooltip(tooltip);
        this.setTextCentered(false);
        this.setRenderDefaultBackground(false);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.visible) {
            if (expand) {
                RenderUtils.drawRect(0, 0, GuiUtils.getScaledWindowWidth(), GuiUtils.getScaledWindowHeight(), ManyLibConfig.HighlightColor.getColorInteger());
            }
            RenderUtils.drawOutline(this.x, this.y, this.width, this.height, GuiBase.COLOR_WHITE);
            this.bindTexture(ManyLibIcons.ARROW_DOWN.getTexture());
            ManyLibIcons.ARROW_DOWN.renderAt(this.x + this.width - 20, this.y + 1, 0, false, false);
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        if (this.expand && !this.isMouseOver(mouseX, mouseY)) this.actionListener.actionPerformedWithButton(this);
    }
}
