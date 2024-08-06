package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.button.InputBox;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;

class ConfigItemInputBox<T extends ConfigBase<?> & IStringRepresentable & IConfigDisplay> extends ConfigItem<T> implements AboutInputMethod {
    InputBox<T> inputBox;

    public ConfigItemInputBox(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        this.inputBox = ScreenConstants.getInputBox(index, config, screen);
    }

    @Override
    public void customDraw(GuiScreen guiScreen, int x, int y) {
        this.inputBox.drawBox(x, y);
    }

    @Override
    public void keyTyped(char c, int i) {
        this.inputBox.keyTyped(c, i);
    }

    @Override
    public void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click) {
        this.inputBox.mouseClicked(mouseX, mouseY, click);
    }

    @Override
    public void resetButtonClicked() {
        this.inputBox.setTextByValue();
    }

    @Override
    public void customActionPerformed(GuiButton guiButton) {
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
