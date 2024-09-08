package fi.dy.masa.malilib.gui.button;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.button.interfaces.TooltipWidget;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class ButtonBase extends WidgetBase implements TooltipWidget<ButtonBase> {
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    protected IButtonActionListener actionListener;
    protected String displayString;
    protected String tooltip;
    protected final List<String> hoverStrings = new ArrayList<>();
    protected final ImmutableList<String> hoverHelp;

    protected boolean enabled = true;
    protected boolean visible = true;
    protected boolean hovered;
    protected boolean hoverInfoRequiresShift;

    protected ButtonBase(int x, int y, int width, int height, String message, IButtonActionListener actionListener) {
        super(x, y, width, height);// 0 is dummy
        this.displayString = message;
        this.actionListener = actionListener;
        this.hoverHelp = ImmutableList.of(StringUtils.translate("malilib.gui.button.hover.hold_shift_for_info"));// TODO key
    }

    public ButtonBase setActionListener(@Nullable IButtonActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setDisplayString(String text) {
        this.displayString = text;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {
        return this.enabled && this.visible && super.isMouseOver(mouseX, mouseY);
    }

    public ButtonBase tooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        this.mc.sndManager.playSoundFX("random.click", 1.0f, 1.0f);

        if (this.actionListener != null) {
            this.actionListener.actionPerformedWithButton(this);
        }

        return true;
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


    protected int getTextureOffset(boolean isMouseOver) {
        return (this.enabled == false) ? 0 : (isMouseOver ? 2 : 1);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.tryDrawTooltip(mouseX, mouseY, drawContext);
    }

    @Override
    public @Nullable String getTooltip() {
        return this.tooltip;
    }

    @Override
    public boolean shouldDrawTooltip() {
        return this.visible && this.enabled && this.hovered;
    }
}
