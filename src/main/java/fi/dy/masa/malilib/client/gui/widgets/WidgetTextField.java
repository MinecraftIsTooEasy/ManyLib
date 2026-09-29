package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.client.gui.widgets.text.TextField;
import org.jspecify.annotations.Nullable;
import org.lwjgl.input.Keyboard;

public class WidgetTextField<T extends TextField> extends WidgetBase {
    private final T textField;
    private final ITextFieldListener<T> listener;

    public WidgetTextField(T textField, @Nullable ITextFieldListener<T> listener) {
        super(textField.xPos, textField.yPos, textField.width, textField.height);
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
        this.onFocus();
    }

    protected void onFocus() {
        Keyboard.enableRepeatEvents(true);
    }

    protected void onLoseFocus() {
        Keyboard.enableRepeatEvents(false);
        if (this.listener != null) {
            this.listener.onFinish(this.textField);
        }
    }

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
        this.textField.setPosition(x, y);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.render(mouseX, mouseY, drawContext);
    }

    public void render(int mouseX, int mouseY, DrawContext drawContext) {
        this.textField.render(drawContext, mouseX, mouseY, 0f);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        return this.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean wasFocused = this.isFocused();
        boolean focused = this.textField.mouseClicked((double) mouseX, (double) mouseY, mouseButton);
        if (wasFocused && !focused) {
            this.onLoseFocus();
        }
        if (!wasFocused && focused) {
            this.onFocus();
        }
        return focused;
    }

    @Override
    public void tick() {
        this.textField.updateCursorCounter();
    }

    public void setVisible(boolean visible) {
        this.textField.setVisible(visible);
        this.textField.setEnabled(visible);
    }

    @Override
    public boolean onCharTypedImpl(char charIn, int modifiers) {
        String textPre = this.textField.getText();

        if (this.isFocused()) {
            if (this.textField.charTyped(charIn, modifiers)) {
                if (this.listener != null && this.textField.getText().equals(textPre) == false) {
                    this.listener.onTextChange(this.textField);
                }
            }
            if (modifiers == 1 || modifiers == 28 || modifiers == 156) {
                this.setFocused(false);
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
