package fi.dy.masa.malilib.api;

import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import fi.dy.masa.malilib.event.InitializationHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.stream.Stream;

public interface ManyLibApi {
    /**
     * Call this on your entrypoint
     */
    static void registerInitializationHandler(IInitializationHandler initializationHandler) {
        InitializationHandler.getInstance().registerInitializationHandler(initializationHandler);
    }

    static void registerConfigHandler(IConfigHandler configHandler) {
        ConfigManager.getInstance().registerConfigHandler(configHandler);
    }

    /**
     * Register display behavior on {@link ManyLibClientApi#registerConfigDisplay}
     */
    static ConfigType registerConfigType(ResourceLocation id) {
        return ConfigTypes.register(id);
    }

    static Stream<String> streamIds() {
        return ConfigManager.getInstance().streamIds();
    }

    static Stream<IConfigHandler> streamConfigHandlers() {
        return ConfigManager.getInstance().streamConfigHandlers();
    }

    @Nullable
    static Map<Side, IConfigHandler> getSideMap(String id) {
        return ConfigManager.getInstance().getSideMap(id);
    }

    @Nullable
    static IConfigHandler getConfig(String id, Side side) {
        return ConfigManager.getInstance().getConfig(id, side);
    }
}
