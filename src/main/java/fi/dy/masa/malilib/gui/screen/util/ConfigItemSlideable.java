package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IConfigSlideable;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.SliderButton;
import net.minecraft.GuiScreen;

class ConfigItemSlideable<T extends ConfigBase<T> & IConfigSlideable & IConfigDisplay & IStringRepresentable> extends ConfigItemInputBox<T> {
    boolean useSlider;
    final SlideableToggleButton slideableToggleButton;
    final SliderButton<T> sliderButton;

    public ConfigItemSlideable(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        this.textFieldWrapper = ScreenConstants.getWrapperForSlideable(index, config, this::getConfigString, screen);
        this.textFieldWrapper.setText(this.config.getStringValue());
        this.useSlider = config.shouldUseSlider();
        this.slideableToggleButton = ScreenConstants.getSlideableToggleButton(index, this.useSlider, screen, button -> this.toggle());
        this.buttons.add(this.slideableToggleButton);
        this.sliderButton = ScreenConstants.getSliderButton(index, config, screen);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.defaultRender(mouseX, mouseY, selected, drawContext);
        if (this.useSlider) {
            this.sliderButton.render(mouseX, mouseY, this.sliderButton.isMouseOver(), drawContext);
        } else {
            this.textFieldWrapper.render(mouseX, mouseY, drawContext);
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.sliderButton.postRenderHovered(mouseX, mouseY, selected, drawContext);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (this.useSlider && mouseButton == 0 && this.sliderButton.onMouseClicked(mouseX, mouseY, mouseButton)) {
            return true;
        } else {
            return super.onMouseClickedImpl(mouseX, mouseY, mouseButton);
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
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        if (this.useSlider) {
            this.sliderButton.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void customSetVisible(boolean visible) {
        super.customSetVisible(visible);
        this.sliderButton.setVisible(visible);
    }

    @Override
    public boolean tryActivateIM(int mouseX, int mouseY, int click) {
        return !this.useSlider && super.tryActivateIM(mouseX, mouseY, click);
    }

    private void toggle() {
        this.slideableToggleButton.toggle();
        this.config.toggleUseSlider();
        if (this.useSlider) {// now is slider; update the text field before using
            String cast = this.getConfigString();
            this.textFieldWrapper.setText(cast);
            this.config.setValueFromString(cast);
            this.textFieldWrapper.setVisible(true);
            this.sliderButton.setVisible(false);
        } else {// now is text field; update the slider before using
            this.config.setValueFromString(this.textFieldWrapper.getText());
            this.sliderButton.updateString();
            this.sliderButton.updateSliderRatioByConfig();
            this.textFieldWrapper.setVisible(false);
            this.sliderButton.setVisible(true);
        }
        this.useSlider = !this.useSlider;
    }

    private String getConfigString() {
        String text = this.config.getStringValue();
        if (this.config.getType() == ConfigType.DOUBLE && text.length() > 11) {
            double doubleValue = ((ConfigDouble) this.config).getDoubleValue();
            text = String.format("%.8f", doubleValue);
        }
        return text;
    }
}
