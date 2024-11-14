package fi.dy.masa.malilib.gui.wrappers;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.widgets.WidgetTextField;

public class TextFieldWrapper<T extends WidgetTextField> {
    private final T textField;
    private final ITextFieldListener<T> listener;

    public TextFieldWrapper(T textField, ITextFieldListener<T> listener) {
        this.textField = textField;
        this.listener = listener;
    }

    public T getTextField() {
        return this.textField;
    }

    public ITextFieldListener<T> getListener() {
        return this.listener;
    }

    public boolean isFocused() {
        return this.textField.isFocused();
    }

    public void setFocused(boolean isFocused) {
        this.textField.setFocused(isFocused);
    }

    public void onGuiClosed() {
        if (this.listener != null) {
            this.listener.onGuiClosed(this.textField);
        }
    }

    public void render(int mouseX, int mouseY, DrawContext drawContext) {
        this.textField.render(drawContext, mouseX, mouseY, 0f);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.textField.onMouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        }

        return false;
    }

    public void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (!this.textField.isMouseOver(mouseX, mouseY)) {
            this.textField.setFocused(false);
            if (this.listener != null) {
                this.listener.onFinish(this.textField);
            }
        }
    }

    public void tickScreen() {
        this.textField.updateCursorCounter();
    }

    public void setVisible(boolean visible) {
        this.textField.setVisible(visible);
        this.textField.setEnabled(visible);
    }

    public boolean tryActivateIM(int mouseX, int mouseY, int mouseButton) {
        if (this.textField.isFocused()) {
            this.textField.onMouseClicked(mouseX, mouseY, mouseButton);
            return true;
        } else {
            return false;
        }
    }

//    public boolean onKeyTyped(int keyCode, int scanCode, int modifiers) {
//        String textPre = this.textField.getText();
//
//        if (this.textField.isFocused() && this.textField.keyPressed(keyCode, scanCode, modifiers)) {
//            if (this.listener != null &&
//                    (keyCode == KeyCodes.KEY_ENTER || keyCode == KeyCodes.KEY_TAB ||
//                            this.textField.getText().equals(textPre) == false)) {
//                this.listener.onTextChange(this.textField);
//            }
//
//            return true;
//        }
//
//        return false;
//    }

    public boolean onCharTyped(char charIn, int modifiers) {
        String textPre = this.textField.getText();

        if (this.textField.isFocused()) {
            if (this.textField.charTyped(charIn, modifiers)) {
                if (this.listener != null && this.textField.getText().equals(textPre) == false) {
                    this.listener.onTextChange(this.textField);
                }
            }
            if (modifiers == 1 || modifiers == 28 || modifiers == 156) {
                this.textField.setFocused(false);
                if (this.listener != null) {
                    this.listener.onFinish(this.textField);
                }
            }
            return true;
        }

        return false;
    }

    public void setText(String text) {
        this.textField.setText(text);
    }

    public String getText() {
        return this.textField.getText();
    }
}
