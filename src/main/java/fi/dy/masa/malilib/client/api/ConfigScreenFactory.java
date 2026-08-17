package fi.dy.masa.malilib.client.api;

import net.minecraft.GuiScreen;

public interface ConfigScreenFactory<S extends GuiScreen> {
    S create(GuiScreen parent);
}
