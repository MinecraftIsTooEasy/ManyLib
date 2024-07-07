package fi.dy.masa.malilib.gui.button.interfaces;

import net.minecraft.FontRenderer;
import net.minecraft.Gui;
import net.minecraft.GuiScreen;
import org.jetbrains.annotations.Nullable;

public class CommentedText extends Gui implements ICommentedElement {
    final int x;
    final int y;
    final String content;
    String comment;
    boolean visible;
    boolean isMouseOver;
    int commentBoundX;
    int commentBoundY;

    public CommentedText(int x, int y, String content, String comment, FontRenderer fontRenderer) {
        this.x = x;
        this.y = y;
        this.content = content;
        this.comment = comment;
        this.commentBoundX = fontRenderer.getStringWidth(this.content);
        this.commentBoundY = 10;
    }

    public void setCommentBoundX(int boundX) {
        this.commentBoundX = boundX;
    }

    public void setCommentBoundY(int boundY) {
        this.commentBoundY = boundY;
    }

    public void draw(GuiScreen guiScreen, int x, int y) {
        if (this.visible) {
            FontRenderer fontRenderer = guiScreen.fontRenderer;
            this.isMouseOver = x >= this.x && x < this.x + this.commentBoundX && y >= this.y && y < this.y + this.commentBoundY;
            this.drawString(fontRenderer, this.content, this.x, this.y, 16777215);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Nullable
    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public boolean shouldDrawComment() {
        return this.visible && this.isMouseOver;
    }
}
