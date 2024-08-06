package fi.dy.masa.malilib.config.interfaces;

public interface IConfigInteger extends IConfigValue, IConfigDisplay, IConfigSlideable {
    @Deprecated(since = "1.1.1")
    default int get() {
        return this.getIntegerValue();
    }

    int getIntegerValue();

    int getDefaultIntegerValue();

    void setIntegerValue(int value);

    int getMinIntegerValue();

    int getMaxIntegerValue();
}
