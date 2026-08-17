package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.core.Color4f;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.util.Rectangle;

import javax.annotation.Nullable;
import java.util.List;

public class WidgetText extends WidgetBase {
    String content;
    boolean visible = true;
    boolean centered;
    Color4f color4f;
    Rectangle tooltipRange = null;

    public WidgetText(int x, int y, String content, @Nullable String tooltip, Color4f color4f) {
        super(x, y, 0, 0);
        this.content = content;
        this.addTooltip(tooltip);
        this.color4f = color4f;
        this.width = this.fontRenderer.getStringWidth(this.content);
        this.height = this.fontRenderer.FONT_HEIGHT;
    }

    public static WidgetText of(String content) {
        return new WidgetText(0, 0, content, null, Color4f.fromColor(16777215));
    }

    public WidgetText position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public WidgetText centered() {
        this.centered = true;
        return this;
    }

    @NotNull
    public Rectangle getTooltipRange() {
        if (this.tooltipRange == null) {
            this.tooltipRange = new Rectangle(this.x, this.y - ScreenConstants.commentedTextShift, this.width, ScreenConstants.commonButtonHeight);
        }
        return this.tooltipRange;
    }

    public WidgetText color(Color4f color4f) {
        this.color4f = color4f;
        return this;
    }

    public WidgetText content(String content) {
        this.content = content;
        return this;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.visible) {
            if (this.centered) {
                this.drawCenteredString(this.fontRenderer, this.content, this.x, this.y, this.color4f.intValue);
            } else {
                this.drawString(this.fontRenderer, this.content, this.x, this.y, this.color4f.intValue);
            }
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
//        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.visible && drawContext.isTopLayer() && this.getTooltipRange().contains(mouseX, mouseY)) {
            RenderUtils.renderTooltip(mouseX, mouseY, this.getHoverStrings(), drawContext);
        }
    }

    public void addTooltip(String tooltip) {
        this.addTooltip(tooltip, false);
    }

    public void addTooltip(String tooltip, boolean head) {
        if (tooltip != null) {
            List<String> list = this.hoverStrings;
            if (head) {
                list.add(0, tooltip);
            } else {
                list.add(tooltip);
            }
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
