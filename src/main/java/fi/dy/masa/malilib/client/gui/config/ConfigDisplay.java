package fi.dy.masa.malilib.client.gui.config;

import fi.dy.masa.malilib.client.gui.widgets.config.WidgetConfig;
import fi.dy.masa.malilib.config.options.ConfigBase;

public interface ConfigDisplay {
    WidgetConfig<?> createWidget(ConfigBase<?> config);

    // can be empty
    String getButtonText(ConfigBase<?> config);
}
