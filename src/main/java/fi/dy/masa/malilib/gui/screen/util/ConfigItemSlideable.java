package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IConfigSlideable;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.gui.button.interfaces.ISliderButton;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;

class ConfigItemSlideable<T extends ConfigBase<T> & IConfigSlideable & IConfigDisplay & IStringRepresentable> extends ConfigItemInputBox<T> {
    boolean useSlider;
    final SlideableToggleButton slideableToggleButton;
    final ISliderButton sliderButton;

    public ConfigItemSlideable(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        this.inputBox = ScreenConstants.getInputBoxForSlideable(index, config, screen);
        this.useSlider = config.shouldUseSlider();
        this.slideableToggleButton = ScreenConstants.getSlieableToggleButton(index, this.useSlider, screen);
        this.buttons.add(this.slideableToggleButton);
        this.sliderButton = ScreenConstants.getSliderButton(index, config, screen);
    }

    @Override
    public void customDraw(GuiScreen guiScreen, int x, int y) {
        if (this.useSlider) {
            ((GuiButton) this.sliderButton).drawButton(guiScreen.mc, x, y);
        } else {
            super.customDraw(guiScreen, x, y);
        }
    }

    @Override
    public void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click) {
        if (this.useSlider) {
            if (click == 0) {
                this.buttonListen((GuiButton) this.sliderButton, guiScreen, mouseX, mouseY);
            }
        } else {
            super.customMouseClicked(guiScreen, mouseX, mouseY, click);
        }
    }

    @Override
    public void customActionPerformed(GuiButton guiButton) {
        if (guiButton == this.slideableToggleButton) {
            this.toggle();
        }
    }

    @Override
    public void resetButtonClicked() {
        if (this.useSlider) {
            this.sliderButton.updateString();
            this.sliderButton.updateSliderRatioByConfig();
        } else {
            super.resetButtonClicked();
        }
    }

    @Override
    public void customSetVisible(boolean visible) {
        super.customSetVisible(visible);
        ((GuiButton) this.sliderButton).drawButton = visible;
    }

    @Override
    public boolean tryActivateIM(int mouseX, int mouseY, int click) {
        return !this.useSlider && super.tryActivateIM(mouseX, mouseY, click);
    }

    private void toggle() {
        this.slideableToggleButton.toggle();
        this.config.toggleUseSlider();
        if (this.useSlider) {
            this.inputBox.setTextByValue();
            this.inputBox.setValueByText();
            this.inputBox.setTextByValue();// translating?
            this.setInputBoxStatus(true);
            this.setSliderStatus(false);
        } else {
            this.inputBox.setValueByText();
            this.sliderButton.updateString();
            this.sliderButton.updateSliderRatioByConfig();
            this.setInputBoxStatus(false);
            this.setSliderStatus(true);
        }
        this.useSlider = !this.useSlider;
    }

    private void setInputBoxStatus(boolean active) {
        super.customSetVisible(active);
    }

    private void setSliderStatus(boolean active) {
        ((GuiButton) this.sliderButton).drawButton = active;
    }
}
