package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.config.options.ConfigToggle;
import fi.dy.masa.malilib.client.gui.button.ButtonBase;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.util.ComponentUtils;

public class WidgetConfigToggle extends WidgetConfigHotkey {
    final ButtonBase toggleButton;

    public WidgetConfigToggle(ConfigToggle config) {
        super(config);
        this.toggleButton = ScreenConstants.getConfigToggleButton(button -> ((ConfigToggle) this.config).next());
        this.toggleButton.setOnUpdate(button -> this.toggleButton.setDisplayString(ComponentUtils.ofBoolean(this.config)));
        this.addWidget(this.toggleButton);
    }

    @Override
    public void init() {
        super.init();

        ScreenConstants.placeConfigToggleButton(this, this.toggleButton);
    }
}
