package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import fi.dy.masa.malilib.util.Platform;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Stream;

public class ConfigManager {
    private static final ConfigManager INSTANCE = new ConfigManager();

    private final Map<String, EnumMap<Side, IConfigHandler>> configMap = new TreeMap<>(Comparator.comparing(Function.identity()));

    public static ConfigManager getInstance() {
        return INSTANCE;
    }

    public Stream<IConfigHandler> streamConfigHandlers() {
        return this.configMap.values().stream().flatMap(x -> x.values().stream());
    }

    @Nullable
    public EnumMap<Side, IConfigHandler> getSideMap(String id) {
        return this.configMap.get(id);
    }

    @Nullable
    public IConfigHandler getConfig(String id, Side side) {
        EnumMap<Side, IConfigHandler> map = this.configMap.get(id);
        if (map == null) return null;
        return map.get(side);
    }

    public Stream<String> streamIds() {
        return this.configMap.keySet().stream();
    }

    public void registerConfigHandler(IConfigHandler configHandler) {
        if (configHandler.getSide() == Side.CLIENT && Platform.isServer()) throw new AssertionError();
        EnumMap<Side, IConfigHandler> inner = this.configMap.computeIfAbsent(configHandler.getId(), k -> new EnumMap<>(Side.class));
        inner.put(configHandler.getSide(), configHandler);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void loadAllConfigs() {
        this.streamConfigHandlers().forEach(IConfigHandler::load);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void saveAllConfigs() {
        this.streamConfigHandlers().forEach(IConfigHandler::save);
    }
}
