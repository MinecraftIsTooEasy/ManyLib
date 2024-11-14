package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.DrawContext;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class WidgetContainer extends WidgetBase {
    protected final List<WidgetBase> subWidgets = new ArrayList<>();
    @Nullable
    protected WidgetBase hoveredSubWidget = null;
    @Nullable
    protected WidgetBase clickedSubWidget = null;

    public WidgetContainer(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    protected <T extends WidgetBase> T addWidget(T widget) {
        this.subWidgets.add(widget);

        return widget;
    }

    protected <T extends WidgetBase> void removeWidget(T widget) {
        this.subWidgets.remove(widget);
    }

    public void syncSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void initWidgets() {
    }

    public void addLabel(int x, int y, int width, int height, int textColor, String... lines) {
        if (lines != null && lines.length >= 1) {
            if (width == -1) {
                for (String line : lines) {
                    width = Math.max(width, this.getStringWidth(line));
                }
            }

            WidgetLabel label = new WidgetLabel(x, y, width, height, textColor, lines);
            this.addWidget(label);
        }
    }

    @Override
    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean handled = false;
        if (this.isMouseOver(mouseX, mouseY)) {
            if (this.subWidgets.isEmpty() == false) {
                for (WidgetBase widget : this.subWidgets) {
                    if (widget.isMouseOver(mouseX, mouseY) && widget.onMouseClicked(mouseX, mouseY, mouseButton)) {
                        // Don't call super if the button press got handled
                        handled = true;
                        this.clickedSubWidget = widget;
                    }
                }
            }

            if (handled == false) {
                handled = this.onMouseClickedImpl(mouseX, mouseY, mouseButton);
            }
        }

        return handled;
    }

    @Override
    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (this.subWidgets.isEmpty() == false) {
            for (WidgetBase widget : this.subWidgets) {
                widget.onMouseReleased(mouseX, mouseY, mouseButton);
            }
        }

        this.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
        if (this.isMouseOver(mouseX, mouseY)) {
            if (this.subWidgets.isEmpty() == false) {
                for (WidgetBase widget : this.subWidgets) {
                    if (widget.onMouseScrolled(mouseX, mouseY, mouseWheelDelta)) {
                        return true;
                    }
                }
            }

            return this.onMouseScrolledImpl(mouseX, mouseY, mouseWheelDelta);
        }

        return false;
    }

    @Override
    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers) {
        boolean handled = false;

        if (this.subWidgets.isEmpty() == false) {
            for (WidgetBase widget : this.subWidgets) {
                if (widget.onKeyTyped(keyCode, scanCode, modifiers)) {
                    // Don't call super if the key press got handled
                    handled = true;
                }
            }
        }

        if (handled == false) {
            handled = this.onKeyTypedImpl(keyCode, scanCode, modifiers);
        }

        return handled;
    }

    @Override
    public boolean onCharTyped(char charIn, int modifiers) {
        boolean handled = false;

        if (this.subWidgets.isEmpty() == false) {
            for (WidgetBase widget : this.subWidgets) {
                if (widget.onCharTyped(charIn, modifiers)) {
                    // Don't call super if the key press got handled
                    handled = true;
                }
            }
        }

        if (handled == false) {
            handled = this.onCharTypedImpl(charIn, modifiers);
        }

        return handled;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.drawSubWidgets(mouseX, mouseY, drawContext);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.drawHoveredSubWidget(mouseX, mouseY, drawContext);
    }

    @Nullable
    public WidgetBase getHoveredSubWidget() {
        return this.hoveredSubWidget;
    }

    protected void drawSubWidgets(int mouseX, int mouseY, DrawContext drawContext) {
        this.hoveredSubWidget = null;

        if (this.subWidgets.isEmpty() == false) {
            for (WidgetBase widget : this.subWidgets) {
                widget.render(mouseX, mouseY, false, drawContext);

                if (widget.isMouseOver(mouseX, mouseY)) {
                    this.hoveredSubWidget = widget;
                }
            }
        }
    }

    protected void drawHoveredSubWidget(int mouseX, int mouseY, DrawContext drawContext) {
        if (this.hoveredSubWidget != null) {
            this.hoveredSubWidget.postRenderHovered(mouseX, mouseY, false, drawContext);
        }
    }
}
