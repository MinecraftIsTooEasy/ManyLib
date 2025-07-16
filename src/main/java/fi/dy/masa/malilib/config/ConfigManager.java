package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final SortedMap<String, IConfigHandler> configInstances = new TreeMap<>();

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public void registerConfig(SimpleConfigs configs) {
        this.registerConfig((IConfigHandler) configs);
    }

    public void registerConfig(IConfigHandler configs) {
        this.registerConfig(configs.getName(), configs);
    }

    public void registerConfig(String modId, IConfigHandler configs) {
        List<ConfigHotkey> hotkeys = configs.getHotkeys();
        if (configs.getValues() != null || hotkeys != null) {
            this.configInstances.put(modId, configs);
        }
        if (hotkeys != null) {
            InputEventHandler.getKeybindManager().registerKeybindProvider(new IKeybindProvider() {
                @Override
                public void addKeysToMap(IKeybindManager manager) {
                    hotkeys.forEach(hotkey -> manager.addKeybindToMap(hotkey.getKeybind()));
                }

                @Override
                public void addHotkeys(IKeybindManager manager) {
                    manager.addHotkeysForCategory(modId, modId + ".hotkeys.category.generic_hotkeys", hotkeys);
                }
            });
        }
    }

    public Map<String, IConfigHandler> getConfigMap() {
        return this.configInstances;
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void loadAllConfigs() {
        this.configInstances.values().forEach(IConfigHandler::load);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void saveAllConfigs() {
        this.configInstances.values().forEach(IConfigHandler::save);
    }
}
