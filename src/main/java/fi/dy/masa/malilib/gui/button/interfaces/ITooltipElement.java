package fi.dy.masa.malilib.gui.button.interfaces;

import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.GuiScreen;

import javax.annotation.Nullable;

public interface ITooltipElement {
    void setTooltip(String tooltip);

    @Nullable
    String getTooltip();

    boolean shouldDrawTooltip();

    default boolean tryDrawTooltip(GuiScreen screen, int i, int j) {
        String tooltip = this.getTooltip();
        if (tooltip != null && !tooltip.isEmpty() && this.shouldDrawTooltip()) {
            RenderUtils.drawCreativeTabHoveringText(screen, tooltip, i, j);
            return true;
        }
        return false;
    }
}
