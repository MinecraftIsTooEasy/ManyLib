package fi.dy.masa.malilib.compat;

import fi.dy.masa.malilib.ManyLibConfig;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;

public class ModMenuImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> ManyLibConfig.getInstance().getConfigScreen(parent);
    }
}
