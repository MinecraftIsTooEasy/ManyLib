package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.ITooltipElement;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.util.Color4f;
import net.minecraft.FontRenderer;
import net.minecraft.Gui;
import net.minecraft.GuiScreen;
import org.jetbrains.annotations.Nullable;

public class CommentedText extends Gui implements ITooltipElement {
    final int x;
    final int y;
    final String content;
    String comment;
    boolean visible = true;
    boolean isMouseOver;
    Interval commentIntervalX;
    Interval commentIntervalY;
    Color4f color4f;

    public CommentedText(int x, int y, String content, String comment, FontRenderer fontRenderer, Color4f color4f) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.comment = comment;
        this.commentIntervalX = new Interval(0, fontRenderer.getStringWidth(this.content));
        this.commentIntervalY = new Interval(-ScreenConstants.commentedTextShift, ScreenConstants.commonButtonHeight - ScreenConstants.commentedTextShift);
        this.color4f = color4f;
    }

    public void setCommentIntervalX(int left, int right) {
        this.commentIntervalX = new Interval(left, right);
    }

    public void setCommentIntervalY(int up, int down) {
        this.commentIntervalY = new Interval(up, down);
    }

    public void setColor4f(Color4f color4f) {
        this.color4f = color4f;
    }

    public void draw(GuiScreen guiScreen, int x, int y) {
        if (this.visible) {
            FontRenderer fontRenderer = guiScreen.fontRenderer;
            this.isMouseOver = this.commentIntervalX.containsInclusive(x - this.x) && this.commentIntervalY.containsInclusive(y - this.y);
            this.drawString(fontRenderer, this.content, this.x, this.y, this.color4f.intValue);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setTooltip(String tooltip) {
        this.comment = tooltip;
    }

    @Nullable
    @Override
    public String getTooltip() {
        return this.comment;
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
