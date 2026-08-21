package fi.dy.masa.malilib.client.gui.event;

/**
 * There are some changes to the original code from 26.2
 */
public interface GuiEventListener {
    default void mouseMoved(double mouseX, double mouseY) {
    }

    default boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    default boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    default boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        return false;
    }

    default boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        return false;
    }

    default boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    default boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    default boolean charTyped(char chr, int keyCode) {
        return false;
    }

    default boolean isMouseOver(double mouseX, double mouseY) {
        return false;
    }

    void setFocused(boolean focused);

    boolean isFocused();

    default boolean shouldTakeFocusAfterInteraction() {
        return true;
    }
}
