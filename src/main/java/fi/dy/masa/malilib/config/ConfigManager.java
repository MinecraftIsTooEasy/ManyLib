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
    private final Map<String, IConfigHandler> nonNullValueConfigs = new HashMap<>();
    private final Map<String, IConfigHandler> nonNullHotKeyConfigs = new HashMap<>();

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
        this.configInstances.put(modId, configs);
        if (configs.getValues() != null) {
            this.nonNullValueConfigs.put(modId, configs);
        }
        if (configs.getHotkeys() != null) {
            this.nonNullHotKeyConfigs.put(modId, configs);
        }
    }

    public Map<String, IConfigHandler> getConfigs() {
        return configInstances;
    }

    public Map<String, IConfigHandler> getNonNullValueConfigs() {
        return nonNullValueConfigs;
    }

    public Map<String, IConfigHandler> getNonNullHotKeyConfigs() {
        return nonNullHotKeyConfigs;
    }

    public Stream<ConfigHotkey> getAllHotKeys() {
        return this.nonNullHotKeyConfigs.values().stream().map(IConfigHandler::getHotkeys).filter(Objects::nonNull).flatMap(Collection::stream);
    }
}
