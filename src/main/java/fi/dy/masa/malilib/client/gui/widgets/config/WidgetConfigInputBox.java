package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.client.gui.wrappers.TextFieldWrapper;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;

public class WidgetConfigInputBox<T extends ConfigBase<?> & IStringRepresentable> extends WidgetConfig<T> {
    final TextFieldWrapper<? extends WidgetTextField> textFieldWrapper;

    public WidgetConfigInputBox(T config) {
        super(config);
        this.textFieldWrapper = ScreenConstants.getTextFieldWrapper(config);
        this.textFieldWrapper.setText(this.config.getStringValue());
        this.addWidget(this.textFieldWrapper);
    }

    @Override
    public void init() {
        super.init();
        ScreenConstants.placeTextFieldWrapper(this, this.textFieldWrapper);
    }

    @Override
    public void onResetClicked() {
        this.textFieldWrapper.getTextField().setText(this.config.getStringValue());
    }
}
