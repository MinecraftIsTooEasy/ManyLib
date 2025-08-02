package fi.dy.masa.malilib.gui.layer;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.Drawable;
import fi.dy.masa.malilib.gui.Element;
import fi.dy.masa.malilib.gui.ParentElement;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Layer implements ParentElement, Drawable {
    private final List<WidgetBase> widgets = new ArrayList<>();
    private @Nullable Element focused;

    /**
     * Lay out elements on new screen or window resizing
     */
    public void initGui() {
        this.widgets.clear();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        for (WidgetBase widget : this.widgets) {
            widget.render(mouseX, mouseY, false, context);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (WidgetBase widget : this.widgets) {
            if (widget.onMouseClicked((int) mouseX, (int) mouseY, button)) return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        for (WidgetBase widget : this.widgets) {
            if (widget.onMouseScrolled((int) mouseX, (int) mouseY, amount)) return true;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (WidgetBase widget : this.widgets) {
            widget.onMouseReleased((int) mouseX, (int) mouseY, button);
        }
        return false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        for (WidgetBase widget : this.widgets) {
            if (widget.onCharTyped(chr, keyCode)) return true;
        }
        return false;
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        if (this.focused != null) {
            this.focused.setFocused(false);
        }

        if (focused != null) {
            focused.setFocused(true);
        }

        this.focused = focused;
    }

    @Override
    public @Nullable Element getFocused() {
        return this.focused;
    }

    public boolean blocksInteraction() {
        return false;
    }

    public void addWidget(WidgetBase widget) {
        this.widgets.add(widget);
    }

    public void removeWidget(WidgetBase widget) {
        this.widgets.remove(widget);
    }
}
