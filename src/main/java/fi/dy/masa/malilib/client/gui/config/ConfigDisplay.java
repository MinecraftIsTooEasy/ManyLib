package fi.dy.masa.malilib.client.gui.config;

import fi.dy.masa.malilib.client.gui.widgets.config.WidgetConfig;
import fi.dy.masa.malilib.config.interfaces.IConfigBase;

public interface ConfigDisplay {
    WidgetConfig<?> createWidget(IConfigBase config);

    // can be empty
    String getButtonText(IConfigBase config);
}
