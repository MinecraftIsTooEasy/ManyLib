package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.screen.interfaces.StatusElement;

/**
 * Invisible, only handles mouse scrolling
 */
public class WidgetScrollHandler extends WidgetBase {
    private final StatusElement target;
    private boolean enabled = true;

    public WidgetScrollHandler(StatusElement target) {
        super(0, 0, 0, 0);
        this.target = target;
    }

    @Override
    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
        if (mouseWheelDelta == 0) return false;
        if (!this.enabled) return false;
        this.target.scroll(mouseWheelDelta < 0);
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
