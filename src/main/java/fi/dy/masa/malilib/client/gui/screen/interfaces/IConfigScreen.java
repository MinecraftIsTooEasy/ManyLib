package fi.dy.masa.malilib.client.gui.screen.interfaces;

import fi.dy.masa.malilib.client.config.interfaces.IClientConfigHandler;
import fi.dy.masa.malilib.client.gui.tab.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;

import java.util.List;

public interface IConfigScreen {
    IConfigHandler getConfigHandler();

    default List<ConfigTab> createConfigTabs() {
        IConfigHandler configHandler = this.getConfigHandler();
        if (configHandler instanceof IClientConfigHandler clientConfigHandler) {
            return List.of(
                    new ConfigTab("value", configHandler.getValues()),
                    new ConfigTab("hotkey", clientConfigHandler.getHotkeys())
            );
        }
        return List.of(new ConfigTab("value", configHandler.getValues()));
    }
}
