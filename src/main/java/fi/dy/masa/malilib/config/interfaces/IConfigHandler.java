package fi.dy.masa.malilib.config.interfaces;

import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import net.minecraft.GuiScreen;

import java.util.List;

public interface IConfigHandler {
    void load();

    void save();

    List<ConfigTab> getConfigTabs();

    List<ConfigBase<?>> getValues();

    List<ConfigHotkey> getHotkeys();

    String getName();

    String getValuesComment();

    String getHotKeysComment();

    GuiScreen getValueScreen(GuiScreen parentScreen);

    GuiScreen getHotKeyScreen(GuiScreen parentScreen);
}
