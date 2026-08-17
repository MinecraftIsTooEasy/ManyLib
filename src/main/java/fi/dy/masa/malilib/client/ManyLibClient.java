package fi.dy.masa.malilib.client;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.internal.ManyLibClientInitHandler;
import net.fabricmc.api.ClientModInitializer;
import net.xiaoyu233.fml.ModResourceManager;

public class ManyLibClient implements ClientModInitializer {
    public static final String RESOURCE_DOMAIN = ManyLib.MOD_ID;

    public static final int OPTIONS_BUTTON_ID = 28251197;

    @Override
    public void onInitializeClient() {
        ModResourceManager.addResourcePackDomain(ManyLibClient.RESOURCE_DOMAIN);
        ManyLibApi.registerInitializationHandler(new ManyLibClientInitHandler());
    }
}
