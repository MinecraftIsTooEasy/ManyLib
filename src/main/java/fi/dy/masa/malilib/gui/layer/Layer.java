package fi.dy.masa.malilib.gui.layer;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.Drawable;
import fi.dy.masa.malilib.gui.Element;
import fi.dy.masa.malilib.gui.ParentElement;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.GuiScreen;
import net.minecraft.ScaledResolution;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Layer implements ParentElement, Drawable {
    protected final GuiScreen screen;
    private final List<WidgetBase> widgets = new ArrayList<>();
    private @Nullable WidgetBase hovered;
    /**
     * This is not used now
     */
    private @Nullable Element focused;

    public Layer(GuiScreen screen) {
        this.screen = screen;
    }

    /**
     * Lay out elements on new screen or window resizing
     */
    public void initGui() {
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
