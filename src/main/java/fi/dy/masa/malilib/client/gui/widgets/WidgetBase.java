package fi.dy.masa.malilib.client.gui.widgets;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.client.util.StringUtils;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.FontRenderer;
import net.minecraft.Gui;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class WidgetBase extends Gui {
    protected final Minecraft mc;
    protected final FontRenderer fontRenderer;
    protected final int fontHeight;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int zLevel;
    private boolean focused;
    protected final List<String> tooltip = new ArrayList<>();
    protected boolean tooltipRequiresShift;
    protected final ImmutableList<String> tooltipHelp;

    public WidgetBase(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = this.mc.fontRenderer;
        this.fontHeight = this.fontRenderer.FONT_HEIGHT;
        this.tooltipHelp = ImmutableList.of(ScreenText.HOLD_SHIFT_FOR_INFO.translate());
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setDimensions(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setZLevel(int zLevel) {
        this.zLevel = zLevel;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void translateTo(WidgetBase parent) {
        this.x += parent.x;
        this.y += parent.y;
    }

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width && mouseY >= this.y && mouseY < this.y + this.height;
    }

    public void tick() {
    }

    /**
     * Called when the widget is added
     */
    public void init() {
    }

    public boolean isFocused() {
        return this.focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public boolean hasTooltip() {
        return this.tooltip.isEmpty() == false;
    }

    public void setTooltipRequiresShift(boolean requireShift) {
        this.tooltipRequiresShift = requireShift;
    }

    public void setTooltip(String... tooltip) {
        this.setTooltip(Arrays.asList(tooltip));
    }

    public void setTooltip(List<String> hoverStrings) {
        this.tooltip.clear();

        for (String str : hoverStrings) {
            str = StringUtils.translate(str);
            if (str == null) continue;
            String[] parts = str.split("\\\\n");

            for (String part : parts) {
                this.tooltip.add(StringUtils.translate(part));
            }
        }
    }

    public List<String> getTooltip() {
        if (this.tooltipRequiresShift && !GuiBase.isShiftDown() && !this.tooltip.isEmpty()) {
            return this.tooltipHelp;
        }
        return this.tooltip;
    }

    public void clearHoverStrings() {
        this.tooltip.clear();
    }

    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            return this.onMouseClickedImpl(mouseX, mouseY, mouseButton);
        }

        return false;
    }

    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        this.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
    }

    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
    }

    public boolean onMouseScrolled(int mouseX, int mouseY, double mouseWheelDelta) {
        if (this.isMouseOver(mouseX, mouseY)) {
            return this.onMouseScrolledImpl(mouseX, mouseY, mouseWheelDelta);
        }

        return false;
    }

    public boolean onMouseScrolledImpl(int mouseX, int mouseY, double mouseWheelDelta) {
        return false;
    }

    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers) {
        return this.onKeyTypedImpl(keyCode, scanCode, modifiers);
    }

    protected boolean onKeyTypedImpl(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    public boolean onCharTyped(char charIn, int modifiers) {
        return this.onCharTypedImpl(charIn, modifiers);
    }

    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        return false;
    }

    /**
     * Returns true if this widget can be selected by clicking at the given point
     */
    public boolean canSelectAt(int mouseX, int mouseY, int mouseButton) {
        return this.isMouseOver(mouseX, mouseY);
    }

    public void bindTexture(ResourceLocation texture) {
        RenderUtils.bindTexture(texture);
    }

    public int getStringWidth(String text) {
        return this.fontRenderer.getStringWidth(text);
    }

    public void drawString(int x, int y, int color, String text, DrawContext drawContext) {
        drawContext.drawText(this.fontRenderer, text, x, y, color, false);
    }

    public void drawCenteredString(int x, int y, int color, String text, DrawContext drawContext) {
        drawContext.drawText(this.fontRenderer, text, x - this.getStringWidth(text) / 2, y, color, false);
    }

    public void drawStringWithShadow(int x, int y, int color, String text, DrawContext drawContext) {
        drawContext.drawTextWithShadow(this.fontRenderer, text, x, y, color);
    }

    public void drawCenteredStringWithShadow(int x, int y, int color, String text, DrawContext drawContext) {
        drawContext.drawCenteredTextWithShadow(this.fontRenderer, text, x, y, color);
    }

    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
    }

    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (drawContext.isTopLayer() && this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.renderTooltip(mouseX, mouseY, this.getTooltip(), drawContext);
        }
    }
}
