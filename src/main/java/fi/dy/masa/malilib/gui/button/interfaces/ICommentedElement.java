package fi.dy.masa.malilib.gui.button.interfaces;

import fi.dy.masa.malilib.util.RenderUtils;
import net.minecraft.GuiScreen;

import javax.annotation.Nullable;

public interface ICommentedElement {
    void setComment(String comment);

    @Nullable
    String getComment();

    boolean shouldDrawComment();

    default boolean tryDrawComment(GuiScreen screen, int i, int j) {
        String comment = this.getComment();
        if (comment != null && !comment.isEmpty() && this.shouldDrawComment()) {
            RenderUtils.drawCreativeTabHoveringText(screen, comment, i, j);
            return true;
        }
        return false;
    }
}
