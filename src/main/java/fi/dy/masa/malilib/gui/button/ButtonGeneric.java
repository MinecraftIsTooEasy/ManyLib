package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonUpdateListener;
import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;

import javax.annotation.Nullable;
import java.util.List;

public class ButtonGeneric extends ButtonBase {
    @Nullable
    protected IGuiIcon icon;
    //    protected LeftRight alignment = LeftRight.LEFT;
    protected boolean textCentered = true;
    protected boolean renderDefaultBackground = true;

    protected ButtonGeneric(int x, int y, int width, int height, String message, IButtonActionListener actionListener) {
        super(x, y, width, height, message, actionListener);
    }

    public static Builder builder(String message, IButtonActionListener action) {
        return new Builder(message, action);
    }

    public static Builder builder(IGuiIcon icon, IButtonActionListener action) {
        return new Builder(icon, action);
    }

    @Override
    public ButtonGeneric setActionListener(@Nullable IButtonActionListener actionListener) {
        this.actionListener = actionListener;
        return this;
    }

    public ButtonGeneric setTextCentered(boolean centered) {
        this.textCentered = centered;
        return this;
    }

    @Override
    public ButtonGeneric setOnUpdate(@Nullable IButtonUpdateListener updateListener) {
        this.updateListener = updateListener;
        return this;
    }

//    /**
//     * Set the icon aligment.<br>
//     * Note: Only LEFT and RIGHT alignments work properly.
//     *
//     * @param alignment
//     * @return
//     */
//    public ButtonGeneric setIconAlignment(LeftRight alignment) {
//        this.alignment = alignment;
//        return this;
//    }

    public ButtonGeneric setRenderDefaultBackground(boolean render) {
        this.renderDefaultBackground = render;
        return this;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.visible) {
            if (this.updateListener != null) this.updateListener.onUpdate(this);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;

            int buttonStyle = this.getTextureOffset(this.hovered);

            RenderUtils.color(1f, 1f, 1f, 1f);

            if (this.renderDefaultBackground) {
                this.bindTexture(BUTTON_TEXTURES);
                RenderUtils.drawTexturedRect(this.x, this.y, 0, 46 + buttonStyle * 20, this.width / 2, this.height);
                RenderUtils.drawTexturedRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + buttonStyle * 20, this.width / 2, this.height);
            }

            this.onMouseDragged(mouseX, mouseY);

            if (this.icon != null) {
//                int offset = this.renderDefaultBackground ? 4 : 0;
//                int x = this.alignment == LeftRight.LEFT ? this.x + offset : this.x + this.width - this.icon.getWidth() - offset;
//                int y = this.y + (this.height - this.icon.getHeight()) / 2;
//                int u = this.icon.getU() + buttonStyle * this.icon.getWidth();

                this.bindTexture(this.icon.getTexture());

                this.icon.renderAt(x, y, 0, this.enabled, this.enabled && this.hovered);

//                RenderUtils.drawTexturedRect(x, y, u, this.icon.getV(), this.icon.getWidth(), this.icon.getHeight());
            }

            if (this.displayString.isBlank() == false) {
                int y = this.y + (this.height - 8) / 2;
                int color = 0xE0E0E0;

                if (this.enabled == false) {
                    color = 0xA0A0A0;
                } else if (this.hovered) {
                    color = 0xFFFFA0;
                }

                if (this.textCentered) {
                    this.drawCenteredStringWithShadow(this.x + this.width / 2, y, color, this.displayString, drawContext);
                } else {
                    int x = this.x + 6;

//                    if (this.icon != null && this.alignment == LeftRight.LEFT) {
//                        x += this.icon.getWidth() + 2;
//                    }

                    this.drawStringWithShadow(x, y, color, this.displayString, drawContext);
                }
            }
        }
    }


    public static class Builder {
        private String message;
        private final IButtonActionListener action;
        private String[] hoverStrings = new String[0];
        private int x;
        private int y;
        private int width = 150;
        private int height = 20;
        private IGuiIcon icon;
        private boolean renderDefaultBackground = true;
        private IButtonUpdateListener updateListener;

        private Builder(String message, IButtonActionListener listener) {
            this.message = message;
            this.action = listener;
        }

        private Builder(IGuiIcon icon, IButtonActionListener listener) {
            this.message = "";
            this.icon = icon;
            this.renderDefaultBackground = false;
            this.action = listener;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
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

        public Builder hoverStrings(String... hoverStrings) {
            this.hoverStrings = hoverStrings;
            return this;
        }

        public Builder renderDefaultBackground(boolean render) {
            this.renderDefaultBackground = render;
            return this;
        }

        public Builder onUpdate(IButtonUpdateListener updateListener) {
            this.updateListener = updateListener;
            return this;
        }

        public ButtonGeneric build() {
            ButtonGeneric buttonGeneric = new ButtonGeneric(this.x, this.y, this.width, this.height, this.message, this.action);
            buttonGeneric.icon = this.icon;
            buttonGeneric.renderDefaultBackground = this.renderDefaultBackground;
            buttonGeneric.setOnUpdate(this.updateListener);
            buttonGeneric.setHoverStrings(this.hoverStrings);
            return buttonGeneric;
        }

        public void addToList(List<ButtonGeneric> list) {
            list.add(this.build());
        }
    }
}
