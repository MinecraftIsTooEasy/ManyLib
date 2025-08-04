package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;

import java.util.ArrayList;
import java.util.List;

public class WidgetText extends WidgetBase {
    String content;
    List<String> tooltipList = new ArrayList<>();
    boolean visible = true;
    boolean isMouseOver;
    boolean centered;
    Interval commentIntervalX;
    Interval commentIntervalY;
    Color4f color4f;

    public WidgetText(int x, int y, String content, String tooltip, Color4f color4f) {
        super(x, y, 0, 0);
        this.content = content;
        this.addTooltip(tooltip);
        this.commentIntervalX = new Interval(0, this.fontRenderer.getStringWidth(this.content));
        this.commentIntervalY = new Interval(-ScreenConstants.commentedTextShift, ScreenConstants.commonButtonHeight - ScreenConstants.commentedTextShift);
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

    public void setCommentIntervalX(int left, int right) {
        this.commentIntervalX = new Interval(left, right);
    }

    public void setCommentIntervalY(int up, int down) {
        this.commentIntervalY = new Interval(up, down);
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
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (!this.visible) return;
        if (this.tooltipList == null) return;
        if (!drawContext.isTopLayer()) return;
        if (!this.commentIntervalX.containsInclusive(mouseX - this.x)) return;
        if (!this.commentIntervalY.containsInclusive(mouseY - this.y)) return;
        RenderUtils.drawHoverText(mouseX, mouseY, this.tooltipList, drawContext);
    }

    public void addTooltip(String tooltip) {
        this.addTooltip(tooltip, false);
    }

    public void addTooltip(String tooltip, boolean head) {
        if (tooltip != null) {
            if (head) {
                this.tooltipList.add(0, tooltip);
            } else {
                this.tooltipList.add(tooltip);
            }
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    private record Interval(int min, int max) {
        private boolean containsInclusive(int x) {
            return x >= min && x <= max;
        }
    }
}
