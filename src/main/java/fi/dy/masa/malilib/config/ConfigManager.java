package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigHotkey;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();
    private final Map<String, IConfigHandler> configInstances = new HashMap<>();

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
        if (configs.getValues() != null || configs.getHotkeys() != null) {
            this.configInstances.put(modId, configs);
        }
    }

    public Map<String, IConfigHandler> getConfigs() {
        return configInstances;
    }

    public Stream<ConfigHotkey> getAllHotKeys() {
        return this.configInstances.values().stream().map(IConfigHandler::getHotkeys).filter(Objects::nonNull).flatMap(Collection::stream);
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
