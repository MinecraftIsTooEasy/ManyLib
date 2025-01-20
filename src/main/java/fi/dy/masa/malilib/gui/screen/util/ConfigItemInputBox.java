package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import net.minecraft.GuiScreen;

class ConfigItemInputBox<T extends ConfigBase<?> & IStringRepresentable & IConfigDisplay> extends ConfigItem<T> implements AboutInputMethod {
    TextFieldWrapper<? extends WidgetTextField> textFieldWrapper;

    public ConfigItemInputBox(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        this.textFieldWrapper = ScreenConstants.getTextFieldWrapper(index, config, screen);
        this.textFieldWrapper.setText(this.config.getStringValue());
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        this.textFieldWrapper.render(mouseX, mouseY, drawContext);
    }

    protected void defaultRender(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public void tickScreen() {
        super.tickScreen();
        this.textFieldWrapper.tickScreen();
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        return this.textFieldWrapper.onCharTyped(charIn, modifiers);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        return this.textFieldWrapper.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        this.textFieldWrapper.onMouseReleased(mouseX, mouseY, mouseButton);
    }

    @Override
    public void resetButtonClicked() {
        this.textFieldWrapper.getTextField().setText(this.config.getStringValue());
    }

    @Override
    public void customSetVisible(boolean visible) {
        this.textFieldWrapper.setVisible(visible);
    }

    @Override
    public boolean tryActivateIM(int mouseX, int mouseY, int click) {
        return this.textFieldWrapper.tryActivateIM(mouseX, mouseY, click);
    }
}
