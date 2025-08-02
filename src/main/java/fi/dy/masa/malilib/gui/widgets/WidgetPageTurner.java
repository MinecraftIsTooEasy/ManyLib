package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.screen.interfaces.PagedElement;

/**
 * Invisible, only handles mouse scrolling
 */
public class WidgetPageTurner extends WidgetBase {
    private final PagedElement target;

    public WidgetPageTurner(PagedElement target) {
        super(0, 0, 0, 0);
        this.target = target;
    }

    @Override
    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
        if (mouseWheelDelta == 0) return false;
        this.target.scroll(mouseWheelDelta < 0);
        return true;
    }
}
