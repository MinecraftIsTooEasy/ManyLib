package fi.dy.masa.malilib.internal;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.core.Side;

class DummyConfig extends SimpleConfigs {
    DummyConfig(String modId) {
        super(modId, ImmutableList.of());
    }

    @Override
    public Side getSide() {
        return Side.COMMON;
    }
}
