package fi.dy.masa.malilib.internal;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.ConfigFactory;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.core.Side;

import java.util.List;

public class ManyLibServerConfig extends SimpleConfigs {
    private static final ManyLibServerConfig Instance;

    private static final ConfigBoolean ServerBoolean = ConfigFactory.ofBoolean("server_boolean");

    public ManyLibServerConfig(String modId, List<ConfigBase<?>> values) {
        super(modId, values);
    }

    public static ManyLibServerConfig getInstance() {
        return Instance;
    }

    @Override
    public Side getSide() {
        return Side.SERVER;
    }

    static {
        Instance = new ManyLibServerConfig(ManyLib.MOD_ID, List.of(ServerBoolean));
    }
}
