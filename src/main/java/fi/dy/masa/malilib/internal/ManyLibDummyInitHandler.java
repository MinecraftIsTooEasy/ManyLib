package fi.dy.masa.malilib.internal;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;

public class ManyLibDummyInitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ManyLibApi.registerConfigHandler(ManyLibCommonConfig.getInstance());
        ManyLibApi.registerConfigHandler(ManyLibServerConfig.getInstance());
        ManyLibApi.registerConfigHandler(new DummyConfig("ommc"));
        ManyLibApi.registerConfigHandler(new DummyConfig("modernmite"));
        ManyLibApi.registerConfigHandler(new DummyConfig("neodymium"));
        ManyLibApi.registerConfigHandler(new DummyConfig("extragui"));
        ManyLibApi.registerConfigHandler(new DummyConfig("waila"));
        ManyLibApi.registerConfigHandler(new DummyConfig("torohealth"));
        ManyLibApi.registerConfigHandler(new DummyConfig("battletowers"));
        ManyLibApi.registerConfigHandler(new DummyConfig("itfreborn"));
        ManyLibApi.registerConfigHandler(new DummyConfig("ite"));
    }
}
