package fi.dy.masa.malilib.config.interfaces;

public interface IConfigDouble extends IConfigValue, IConfigDisplay, IConfigSlideable {
    @Deprecated(since = "1.1.1")
    default double get() {
        return this.getDoubleValue();
    }

    double getDoubleValue();

    double getDefaultDoubleValue();

    void setDoubleValue(double value);

    double getMinDoubleValue();

    double getMaxDoubleValue();
}
