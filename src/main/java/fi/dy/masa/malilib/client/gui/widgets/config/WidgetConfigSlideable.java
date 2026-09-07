package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.button.SlideableToggleButton;
import fi.dy.masa.malilib.client.gui.button.SliderButton;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.client.gui.widgets.text.TextField;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.IConfigSlideable;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;

public class WidgetConfigSlideable<T extends ConfigBase<T> & IConfigSlideable & IStringRepresentable> extends WidgetConfig<T> {
    boolean useSlider;
    final WidgetTextField<? extends TextField> widgetTextField;
    final SlideableToggleButton slideableToggleButton;
    final SliderButton<T> sliderButton;

    public WidgetConfigSlideable(T config) {
        super(config);
        this.widgetTextField = ScreenConstants.getWrapperForSlideable(config, this::getConfigString, this);
        this.widgetTextField.setText(this.config.getStringValue());
        this.useSlider = config.shouldUseSlider();
        this.slideableToggleButton = new SlideableToggleButton(0, 0, this.useSlider, button -> this.toggle());
        this.addWidget(this.slideableToggleButton);
        this.sliderButton = ScreenConstants.getSliderButton(config);
    }

    @Override
    public void init() {
        super.init();

        ScreenConstants.placeTextFieldWrapper(this, this.widgetTextField);
        ScreenConstants.placeSlideableToggleButton(this, this.slideableToggleButton);
        ScreenConstants.placeCommonButton(this, this.sliderButton);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        if (this.useSlider) {
            this.sliderButton.render(mouseX, mouseY, this.sliderButton.isMouseOver(), drawContext);
        } else {
            this.widgetTextField.render(mouseX, mouseY, drawContext);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.useSlider) {
            this.widgetTextField.tick();
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.sliderButton.postRenderHovered(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (this.useSlider) {
            if (mouseButton == 0 && this.sliderButton.onMouseClicked(mouseX, mouseY, mouseButton)) {
                return true;
            }
        } else {
            this.widgetTextField.mouseClicked(mouseX, mouseY, mouseButton);
        }
        return super.onMouseClickedImpl(mouseX, mouseY, mouseButton);
    }

    @Override
    public void onResetClicked() {
        if (this.useSlider) {
            this.sliderButton.updateString();
            this.sliderButton.updateSliderRatioByConfig();
        } else {
            this.widgetTextField.getTextField().setText(this.config.getStringValue());
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
    protected boolean onCharTypedImpl(char charIn, int modifiers) {
        if (!this.useSlider && this.widgetTextField.onCharTyped(charIn, modifiers)) return true;
        return super.onCharTypedImpl(charIn, modifiers);
    }

    private void toggle() {
        this.slideableToggleButton.toggle();
        this.config.toggleUseSlider();
        if (this.useSlider) {// now is slider; update the text field before using
            String cast = this.getConfigString();
            this.widgetTextField.setText(cast);
            this.config.setValueFromString(cast);
            this.widgetTextField.setVisible(true);
            this.sliderButton.setVisible(false);
        } else {// now is text field; update the slider before using
            this.config.setValueFromString(this.widgetTextField.getText());
            this.sliderButton.updateString();
            this.sliderButton.updateSliderRatioByConfig();
            this.widgetTextField.setVisible(false);
            this.sliderButton.setVisible(true);
        }
        this.useSlider = !this.useSlider;
    }

    private String getConfigString() {
        String text = this.config.getStringValue();
        if (this.config.getType() == ConfigTypes.DOUBLE && text.length() > 11) {
            double doubleValue = ((ConfigDouble) this.config).getDoubleValue();
            text = String.format("%.8f", doubleValue);
        }
        return text;
    }
}
