package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.interfaces.TooltipWidget;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.util.Color4f;
import org.jetbrains.annotations.Nullable;

public class WidgetText extends WidgetBase implements TooltipWidget<WidgetText> {
    String content;
    String tooltip;
    boolean visible = true;
    boolean isMouseOver;
    boolean centered;
    Interval commentIntervalX;
    Interval commentIntervalY;
    Color4f color4f;

    public WidgetText(int x, int y, String content, String tooltip, Color4f color4f) {
        super(x, y, 0, 0);
        this.content = content;
        this.tooltip = tooltip;
        this.commentIntervalX = new Interval(0, this.fontRenderer.getStringWidth(this.content));
        this.commentIntervalY = new Interval(-ScreenConstants.commentedTextShift, ScreenConstants.commonButtonHeight - ScreenConstants.commentedTextShift);
        this.color4f = color4f;
        this.width = this.fontRenderer.getStringWidth(this.content);
        this.height = this.fontRenderer.FONT_HEIGHT;
    }

    public static WidgetText of(String message) {
        return new WidgetText(0, 0, message, null, Color4f.fromColor(16777215));
    }

    public WidgetText position(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public WidgetText centered(boolean centered) {
        this.centered = centered;
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
            this.isMouseOver = this.commentIntervalX.containsInclusive(mouseX - this.x) && this.commentIntervalY.containsInclusive(mouseY - this.y);
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
        this.tryDrawTooltip(mouseX, mouseY, drawContext);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public WidgetText tooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Nullable
    @Override
    public String getTooltip() {
        return this.tooltip;
    }

    @Override
    public boolean shouldDrawTooltip() {
        return this.visible && this.isMouseOver;
    }

    private record Interval(int min, int max) {
        private boolean containsInclusive(int x) {
            return x >= min && x <= max;
        }
    }
}
