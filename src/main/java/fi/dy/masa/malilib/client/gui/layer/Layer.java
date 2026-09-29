package fi.dy.masa.malilib.client.gui.layer;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.event.GuiEventListener;
import fi.dy.masa.malilib.client.gui.interfaces.Renderable;
import fi.dy.masa.malilib.client.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.client.util.GuiUtils;
import fi.dy.masa.malilib.client.util.RenderUtils;
import net.minecraft.GuiScreen;
import net.minecraft.ScaledResolution;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Layer implements GuiEventListener, Renderable {
    protected final GuiScreen screen;
    private final List<WidgetBase> widgets = new ArrayList<>();
    private @Nullable WidgetBase hovered;
    private boolean focused = false;

    public Layer(GuiScreen screen) {
        this.screen = screen;
    }

    /**
     * Lay out elements on new screen or window resizing
     */
    public void reload() {
        this.widgets.clear();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderWidgets(context, mouseX, mouseY, delta);
        this.renderTooltips(context, mouseX, mouseY, delta);
    }

    private void renderWidgets(DrawContext context, int mouseX, int mouseY, float delta) {
        this.hovered = null;
        for (WidgetBase widget : this.widgets) {
            widget.render(mouseX, mouseY, false, context);
            if (widget.isMouseOver(mouseX, mouseY)) {
                this.hovered = widget;
            }
        }
    }

    private void renderTooltips(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.hovered != null) {
            this.hovered.postRenderHovered(mouseX, mouseY, true, context);
        }
    }

    public void tick() {
        for (WidgetBase widget : this.widgets) {
            widget.tick();
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
    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    @Override
    public boolean isFocused() {
        return this.focused;
    }

    /**
     * Clicking the blank to remove the layer
     */
    public boolean autoExit() {
        return false;
    }

    /**
     * Blocks interaction from bottom layers
     */
    public boolean blocksInteraction() {
        return false;
    }

    public void addWidget(WidgetBase widget) {
        widget.init();
        this.widgets.add(widget);
    }

    public void removeWidget(WidgetBase widget) {
        this.widgets.remove(widget);
    }

    protected void drawOverlay() {
        ScaledResolution resolution = GuiUtils.getScaledResolution();
        RenderUtils.drawRect(
                0, 0, resolution.getScaledWidth(), resolution.getScaledHeight(), 2004318071
        );
    }

    protected void drawOutlinedBox(int x_offset, int y_offset) {
        RenderUtils.drawOutlinedBox(this.screen.width / 2 - x_offset,
                this.screen.height / 2 - y_offset,
                x_offset * 2,
                y_offset * 2,
                0xFF000000,
                0xFFA0A0A0,
                0
        );
    }

    public void removed() {
    }
}
