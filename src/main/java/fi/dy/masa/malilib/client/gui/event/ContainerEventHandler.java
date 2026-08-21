package fi.dy.masa.malilib.client.gui.event;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface ContainerEventHandler extends GuiEventListener {
    List<? extends GuiEventListener> children();

    default Optional<GuiEventListener> getChildAt(final double x, final double y) {
        for (GuiEventListener child : this.children()) {
            if (child.isMouseOver(x, y)) {
                return Optional.of(child);
            }
        }

        return Optional.empty();
    }

    @Override
    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        Optional<GuiEventListener> child = this.getChildAt(mouseX, mouseY);
        if (child.isEmpty()) {
            return false;
        }

        GuiEventListener widget = child.get();
        if (widget.mouseClicked(mouseX, mouseY, button) && widget.shouldTakeFocusAfterInteraction()) {
            this.setFocused(widget);
            if (button == 0) {
                this.setDragging(true);
            }
        }

        return true;
    }

    @Override
    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0 && this.isDragging()) {
            this.setDragging(false);
            if (this.getFocused() != null) {
                return this.getFocused().mouseReleased(mouseX, mouseY, button);
            }
        }

        return false;
    }

    @Override
    default boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return this.getFocused() != null && this.isDragging() && button == 0 && this.getFocused().mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }

    boolean isDragging();

    void setDragging(boolean dragging);

    @Override
    default boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return this.getChildAt(mouseX, mouseY).filter(child -> child.mouseScrolled(mouseX, mouseY, amount)).isPresent();
    }

    @Override
    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && this.getFocused().keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return this.getFocused() != null && this.getFocused().keyReleased(keyCode, scanCode, modifiers);
    }

    @Override
    default boolean charTyped(char chr, int keyCode) {
        return this.getFocused() != null && this.getFocused().charTyped(chr, keyCode);
    }

    @Nullable
    GuiEventListener getFocused();

    void setFocused(final @Nullable GuiEventListener focused);

    @Override
    default void setFocused(final boolean focused) {
        if (!focused) {
            this.setFocused(null);
        }
    }

    @Override
    default boolean isFocused() {
        return this.getFocused() != null;
    }
}
