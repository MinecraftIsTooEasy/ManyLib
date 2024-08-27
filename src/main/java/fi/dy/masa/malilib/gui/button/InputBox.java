package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import fi.dy.masa.malilib.gui.button.interfaces.ISuppressibleElement;
import fi.dy.masa.malilib.gui.button.interfaces.ITooltipElement;
import net.minecraft.FontRenderer;
import net.minecraft.GuiTextField;
import org.jetbrains.annotations.Nullable;

public class InputBox<T extends ConfigBase<?> & IStringRepresentable> implements ITooltipElement, IInteractiveElement, ISuppressibleElement {
    protected final T config;
    protected final int x;
    protected final int y;
    protected final GuiTextField guiTextField;
    protected boolean mouseOver;

    public InputBox(T config, FontRenderer fontRenderer, int x, int y, int width, int height) {
        this.config = config;
        this.x = x;
        this.y = y;
        this.guiTextField = new GuiTextField(fontRenderer, x, y, width, height);
        this.setTextByValue();
    }

    @Override
    public void keyTyped(char c, int i) {
        if (this.guiTextField.isFocused()) {
            this.guiTextField.textboxKeyTyped(c, i);
            if (i == 1 || i == 28 || i == 156) {// enter key or esc
                this.setValueByText();
                this.guiTextField.setFocused(false);
            }
        }
    }

    @Override
    public void updateScreen() {
        this.guiTextField.updateCursorCounter();
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        GuiTextField guiTextField = this.guiTextField;
        if (guiTextField.isFocused()) {
            guiTextField.mouseClicked(par1, par2, par3);
            if (!guiTextField.isFocused()) {
                this.setValueByText();
            }
        } else {
            guiTextField.mouseClicked(par1, par2, par3);
        }
    }

    public boolean tryActivateIM(int par1, int par2, int par3) {
        if (this.guiTextField.isFocused()) {
            this.guiTextField.mouseClicked(par1, par2, par3);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void setVisible(boolean visible) {
        this.guiTextField.setVisible(visible);
        this.guiTextField.setEnabled(visible);
    }

    public void setTextByValue() {
        String text = this.config.getStringValue();
        if (this.config.getType() == ConfigType.DOUBLE && text.length() > 11) {
            double doubleValue = ((ConfigDouble) this.config).getDoubleValue();
            text = String.format("%e", doubleValue);
        }
        this.guiTextField.setText(text);
    }

    public void drawBox(int i, int j) {
        if (this.guiTextField.getVisible()) {
            this.guiTextField.drawTextBox();
        }
        int xPos = this.guiTextField.xPos;
        int yPos = this.guiTextField.yPos;
        this.mouseOver = i >= xPos && i < xPos + guiTextField.width && j >= yPos && j < yPos + guiTextField.height;
    }

    @Override
    public void setTooltip(String tooltip) {
        ManyLib.logger.warn("InputBox: do not set comment for me");
    }

    @Nullable
    @Override
    public String getTooltip() {
        return this.config.getConfigGuiDisplayComment();
    }

    @Override
    public boolean shouldDrawTooltip() {
        return this.mouseOver && this.guiTextField.getVisible();
    }

    public void setValueByText() {
        String text = this.guiTextField.getText();
        this.config.setValueFromString(text);
        this.setTextByValue();
    }
}
