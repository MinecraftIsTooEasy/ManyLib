package fi.dy.masa.malilib;

import fi.dy.masa.malilib.config.ConfigManager;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManyLib implements ModInitializer {
    public static final String MOD_ID = "ManyLib";
    public static final Logger logger = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ManyLibConfig.getInstance().load();
        ConfigManager.getInstance().registerConfig(ManyLibConfig.getInstance());
    }
}
