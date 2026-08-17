package fi.dy.masa.malilib.client.config.interfaces;

import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IClientConfigHandler extends IConfigHandler {
    @NotNull
    List<ConfigHotkey> getHotkeys();

    @Override
    default Side getSide() {
        return Side.CLIENT;
    }
}
