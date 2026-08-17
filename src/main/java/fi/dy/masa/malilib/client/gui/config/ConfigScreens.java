package fi.dy.masa.malilib.client.gui.config;

import fi.dy.masa.malilib.client.api.ConfigScreenFactory;
import fi.dy.masa.malilib.client.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import net.minecraft.GuiScreen;

import java.util.HashMap;
import java.util.Map;

public class ConfigScreens {
    private static final Map<IConfigHandler, ConfigScreenFactory<?>> FACTORY_MAP = new HashMap<>();

    public static void setConfigScreenFactory(IConfigHandler configHandler, ConfigScreenFactory<?> factory) {
        FACTORY_MAP.put(configHandler, factory);
    }

    public static GuiScreen createConfigScreen(IConfigHandler configHandler, GuiScreen parent) {
        ConfigScreenFactory<?> factory = FACTORY_MAP.get(configHandler);
        if (factory != null) return factory.create(parent);
        return new DefaultConfigScreen(parent, configHandler);
    }
}
