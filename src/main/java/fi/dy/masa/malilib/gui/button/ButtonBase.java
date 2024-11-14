package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonUpdateListener;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import net.minecraft.ResourceLocation;

import javax.annotation.Nullable;

public abstract class ButtonBase extends WidgetBase {
    protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
    protected IButtonActionListener actionListener;
    protected IButtonUpdateListener updateListener;
    protected String displayString;
    protected boolean enabled = true;
    protected boolean visible = true;
    protected boolean hovered;

    protected ButtonBase(int x, int y, int width, int height, String message, IButtonActionListener actionListener) {
        super(x, y, width, height);// 0 is dummy
        this.displayString = message;
        this.actionListener = actionListener;
    }

    public ButtonBase setActionListener(@Nullable IButtonActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public ButtonBase setOnUpdate(@Nullable IButtonUpdateListener updateListener) {
        this.updateListener = updateListener;
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

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {// left click
            this.mc.sndManager.playSoundFX("random.click", 1.0f, 1.0f);

            if (this.actionListener != null) {
                this.actionListener.actionPerformedWithButton(this);
            }
            return true;
        }

        return false;
    }

    protected int getTextureOffset(boolean isMouseOver) {
        return (this.enabled == false) ? 0 : (isMouseOver ? 2 : 1);
    }
}
