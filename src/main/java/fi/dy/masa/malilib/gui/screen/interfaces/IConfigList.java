package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.config.options.ConfigBase;

public interface IConfigList {
    int size();

    ConfigBase<?> get(int index);
}
