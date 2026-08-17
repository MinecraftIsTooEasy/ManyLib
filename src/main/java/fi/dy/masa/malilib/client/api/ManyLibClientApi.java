package fi.dy.masa.malilib.client.api;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplay;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplays;
import fi.dy.masa.malilib.client.gui.config.ConfigScreens;
import fi.dy.masa.malilib.client.gui.screen.ConfigSideMenu;
import fi.dy.masa.malilib.client.gui.screen.ModMenuV1;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import fi.dy.masa.malilib.util.CollectionUtils;
import net.minecraft.GuiScreen;

import java.util.Map;

public interface ManyLibClientApi {
    // set custom screens here
    static void setConfigScreenFactory(IConfigHandler configHandler, ConfigScreenFactory<?> factory) {
        ConfigScreens.setConfigScreenFactory(configHandler, factory);
    }

    static void registerConfigDisplay(ConfigType configType, ConfigDisplay display) {
        ConfigDisplays.register(configType, display);
    }

    // only for manylib mods
    static GuiScreen createModMenu(GuiScreen parent) {
        return new ModMenuV1(parent);
    }

    static GuiScreen createConfigScreen(IConfigHandler configHandler, GuiScreen parent) {
        return ConfigScreens.createConfigScreen(configHandler, parent);
    }

    // If only one side is provided then directly enter
    static GuiScreen createConfigScreen(String id, GuiScreen parent) {
        Map<Side, IConfigHandler> map = ManyLibApi.getSideMap(id);
        assert map != null;
        if (map.size() == 1) return createConfigScreen(CollectionUtils.getFirstValue(map), parent);
        return new ConfigSideMenu(parent, id, map);
    }

    static GuiScreen createConfigSideMenu(String id, GuiScreen parent) {
        return new ConfigSideMenu(parent, id, ManyLibApi.getSideMap(id));
    }

    static void setOverlayMessage(String string) {
        RenderUtils.setOverlayMessage(string);
    }
}
