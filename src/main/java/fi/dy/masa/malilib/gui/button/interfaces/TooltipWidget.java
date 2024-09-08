package fi.dy.masa.malilib.gui.button.interfaces;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.render.RenderUtils;

import javax.annotation.Nullable;

public interface TooltipWidget<T> {
    T tooltip(String tooltip);

    @Nullable
    String getTooltip();

    boolean shouldDrawTooltip();

    default boolean tryDrawTooltip(int mouseX, int mouseY, DrawContext drawContext) {
        String tooltip = this.getTooltip();
        if (tooltip != null && !tooltip.isEmpty() && this.shouldDrawTooltip()) {
            RenderUtils.drawCreativeTabHoveringText(tooltip, mouseX, mouseY, drawContext);
            return true;
        }
        return false;
    }
}
