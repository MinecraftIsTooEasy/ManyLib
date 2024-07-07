package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.SimpleConfigs;

/**
 * @deprecated Use {@link ConfigManager} instead
 **/
@Deprecated(since = "1.0.4")
public class ModsScreen {
    private static final ModsScreen Instance = new ModsScreen();

    public void addConfig(SimpleConfigs configs) {
        ConfigManager.getInstance().registerConfig(configs);
    }

    public static ModsScreen getInstance() {
        return Instance;
    }
}
