package fi.dy.masa.malilib.gui.widgets;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
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
    protected final List<String> hoverStrings = new ArrayList<>();
    protected boolean hoverInfoRequiresShift;
    protected final ImmutableList<String> hoverHelp;

    public WidgetBase(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.mc = Minecraft.getMinecraft();
        this.fontRenderer = this.mc.fontRenderer;
        this.fontHeight = this.fontRenderer.FONT_HEIGHT;
        this.hoverHelp = ImmutableList.of(StringUtils.translate("manyLib.gui.button.hover.hold_shift_for_info"));
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

    public boolean isMouseOver(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX < this.x + this.width &&
                mouseY >= this.y && mouseY < this.y + this.height;
    }

    public void tick() {
    }

    public boolean hasHoverText() {
        return this.hoverStrings.isEmpty() == false;
    }

    public void setHoverInfoRequiresShift(boolean requireShift) {
        this.hoverInfoRequiresShift = requireShift;
    }

    public void setHoverStrings(String... hoverStrings) {
        this.setHoverStrings(Arrays.asList(hoverStrings));
    }

    public void setHoverStrings(List<String> hoverStrings) {
        this.hoverStrings.clear();

        for (String str : hoverStrings) {
            str = StringUtils.translate(str);
            if (str == null) continue;
            String[] parts = str.split("\\\\n");

            for (String part : parts) {
                this.hoverStrings.add(StringUtils.translate(part));
            }
        }
    }

    public List<String> getHoverStrings() {
        if (this.hoverInfoRequiresShift && GuiBase.isShiftDown() == false) {
            return this.hoverHelp;
        }

        return this.hoverStrings;
    }

    public void clearHoverStrings() {
        this.hoverStrings.clear();
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

    public void onMouseDragged(int mouseX, int mouseY) {
        this.onMouseDraggedImpl(mouseX, mouseY);
    }

    protected void onMouseDraggedImpl(int mouseX, int mouseY) {
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
    }
}
