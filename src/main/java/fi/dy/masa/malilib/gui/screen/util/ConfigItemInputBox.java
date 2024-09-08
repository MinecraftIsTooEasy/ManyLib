package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.widgets.InputBox;
import net.minecraft.GuiScreen;

class ConfigItemInputBox<T extends ConfigBase<?> & IStringRepresentable & IConfigDisplay> extends ConfigItem<T> implements AboutInputMethod {
    InputBox<T> inputBox;

    public ConfigItemInputBox(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        this.inputBox = ScreenConstants.getInputBox(index, config, screen);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        this.inputBox.render(mouseX, mouseY, selected, drawContext);
    }

    protected void defaultRender(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public void update() {
        super.update();
        if (this.inputBox != null) this.inputBox.update();
    }

    @Override
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        return this.inputBox.onCharTyped(charIn, modifiers);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        return this.inputBox.onMouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void resetButtonClicked() {
        this.inputBox.setTextByValue();
    }

    @Override
    public void customSetVisible(boolean visible) {
        this.inputBox.setVisible(visible);
    }

    @Override
    public boolean tryActivateIM(int mouseX, int mouseY, int click) {
        return this.inputBox.tryActivateIM(mouseX, mouseY, click);
    }
}
