package fi.dy.masa.malilib;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.internal.ManyLibDummyInitHandler;
import fi.dy.masa.malilib.util.Platform;
import net.fabricmc.api.ModInitializer;
import net.minecraft.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManyLib implements ModInitializer {
    public static final String MOD_ID = "manylib";
    public static final String MOD_NAME = "ManyLib";
    public static final Logger logger = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        if (Platform.isDev()) {
            ManyLibApi.registerInitializationHandler(new ManyLibDummyInitHandler());
        }
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
