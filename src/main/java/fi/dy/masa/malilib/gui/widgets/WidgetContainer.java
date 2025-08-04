package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;

import java.util.ArrayList;
import java.util.List;

public abstract class WidgetContainer extends WidgetBase {
    private final List<WidgetBase> subWidgets = new ArrayList<>();

    public WidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected <T extends WidgetBase> T addWidget(T widget) {
        widget.init();
        this.subWidgets.add(widget);
        return widget;
    }

    protected <T extends WidgetBase> void removeWidget(T widget) {
        this.subWidgets.remove(widget);
    }

    /**
     * For always handle events
     */
    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return true;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        for (WidgetBase subWidget : this.subWidgets) {
            subWidget.render(mouseX, mouseY, selected, drawContext);
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        for (WidgetBase subWidget : this.subWidgets) {
            subWidget.postRenderHovered(mouseX, mouseY, selected, drawContext);
        }
    }

    @Override
    public void tick() {
        for (WidgetBase subWidget : this.subWidgets) {
            subWidget.tick();
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        for (WidgetBase subWidget : this.subWidgets) {
            if (subWidget.onMouseClicked(mouseX, mouseY, mouseButton)) return true;
        }
        return false;
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        for (WidgetBase subWidget : this.subWidgets) {
            subWidget.onMouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
        for (WidgetBase subWidget : this.subWidgets) {
            if (subWidget.onMouseScrolled(mouseX, mouseY, mouseWheelDelta)) return true;
        }
        return false;
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        for (WidgetBase subWidget : this.subWidgets) {
            if (subWidget.onCharTyped(charIn, modifiers)) return true;
        }
        return false;
    }
}
