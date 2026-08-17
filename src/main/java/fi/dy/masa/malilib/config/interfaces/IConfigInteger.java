package fi.dy.masa.malilib.config.interfaces;

public interface IConfigInteger extends IConfigValue, IConfigSlideable {
    int getIntegerValue();

    int getDefaultIntegerValue();

    void setIntegerValue(int value);

    int getMinIntegerValue();

    int getMaxIntegerValue();
}
