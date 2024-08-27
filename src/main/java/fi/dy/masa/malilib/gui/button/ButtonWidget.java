package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.ITooltipElement;
import net.minecraft.GuiButton;

import javax.annotation.Nullable;

public class ButtonWidget extends GuiButton implements ITooltipElement {
    protected final PressAction onPress;

    protected String tooltip;

    protected ButtonWidget(int x, int y, int width, int height, String message, PressAction onPress) {
        super(0, x, y, width, height, message);// 0 is dummy
        this.onPress = onPress;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public static Builder builder(String message, PressAction action) {
        return new Builder(message, action);
    }

    public void onPress() {
        if (this.onPress != null) {// if some stupid...
            this.onPress.onPress(this);
        }
    }

    @Override
    public @Nullable String getTooltip() {
        return this.tooltip;
    }

    @Override
    public boolean shouldDrawTooltip() {
        return this.drawButton && this.enabled && this.field_82253_i;
    }

    public static class Builder {
        private final String message;
        private final PressAction action;
        @Nullable
        private String tooltip;

        private int x;
        private int y;
        private int width = 150;
        private int height = 20;

        public Builder(String message, PressAction onPress) {
            this.message = message;
            this.action = onPress;
        }

        public Builder position(int x, int y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder dimensions(int x, int y, int width, int height) {
            return this.position(x, y).size(width, height);
        }

        public Builder tooltip(@Nullable String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public ButtonWidget build() {
            ButtonWidget buttonWidget = new ButtonWidget(this.x, this.y, this.width, this.height, this.message, this.action);
            buttonWidget.setTooltip(this.tooltip);
            return buttonWidget;
        }
    }

    public interface PressAction {
        void onPress(ButtonWidget button);
    }
}
