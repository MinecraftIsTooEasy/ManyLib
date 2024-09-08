package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.gui.DrawContext;

public class InputBox<T extends ConfigBase<?> & IStringRepresentable> extends WidgetBase {
    protected final T config;
    protected final WidgetTextField textField;

    public InputBox(T config, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.config = config;
        this.textField = new WidgetTextField(x, y, width, height);
        this.setTextByValue();
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (this.textField.isFocused()) {
            if (this.textField.onCharTyped(charIn, modifiers)) {
                return true;
            }
            if (modifiers == 1 || modifiers == 28 || modifiers == 156) {// enter key or esc
                this.setValueByText();
                this.textField.setFocused(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public void update() {
        super.update();
        this.textField.updateCursorCounter();
    }

    @Override
    public boolean isMouseOver(int mouseX, int mouseY) {// for always listening
        return super.isMouseOver(mouseX, mouseY) || this.textField.isMouseOver(mouseX, mouseY);
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        if (this.textField.isFocused() && !this.textField.isMouseOver(mouseX, mouseY)) {
            this.textField.setFocused(false);
            this.setValueByText();
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        return this.textField.onMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        this.textField.render(mouseX, mouseY, selected, drawContext);
    }

    public boolean tryActivateIM(int par1, int par2, int par3) {
        if (this.textField.isFocused()) {
            this.textField.onMouseClicked(par1, par2, par3);
            return true;
        } else {
            return false;
        }
    }

    public void setVisible(boolean visible) {
        this.textField.setVisible(visible);
        this.textField.setEnabled(visible);
    }

    public void setTextByValue() {
        String text = this.config.getStringValue();
        if (this.config.getType() == ConfigType.DOUBLE && text.length() > 11) {
            double doubleValue = ((ConfigDouble) this.config).getDoubleValue();
            text = String.format("%e", doubleValue);
        }
        this.textField.setText(text);
    }

    public void setValueByText() {
        String text = this.textField.getText();
        this.config.setValueFromString(text);
        this.setTextByValue();
    }
}
