package fi.dy.masa.malilib;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ModInitializer;
import net.xiaoyu233.fml.ModResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ManyLib implements ModInitializer {
    public static final String MOD_ID = "ManyLib";
    public static final Logger logger = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        ManyLibConfig.getInstance().load();
        InitializationHandler.getInstance().registerInitializationHandler(new ManyLibInitHandler());
        ModResourceManager.addResourcePackDomain("manyLib");
    }
}
