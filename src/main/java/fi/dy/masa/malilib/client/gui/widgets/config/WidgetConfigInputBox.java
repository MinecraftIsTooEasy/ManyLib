package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.text.TextField;
import fi.dy.masa.malilib.client.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;

public class WidgetConfigInputBox<T extends ConfigBase<?> & IStringRepresentable> extends WidgetConfig<T> {
    final WidgetTextField<? extends TextField> widgetTextField;

    public WidgetConfigInputBox(T config) {
        super(config);
        this.widgetTextField = ScreenConstants.getTextFieldWrapper(config);
        this.widgetTextField.setText(this.config.getStringValue());
        this.addWidget(this.widgetTextField);
    }

    @Override
    public void init() {
        super.init();
        ScreenConstants.placeTextFieldWrapper(this, this.widgetTextField);
    }

    @Override
    public void onResetClicked() {
        this.widgetTextField.getTextField().setText(this.config.getStringValue());
    }
}
