package fi.dy.masa.malilib.config.interfaces;

public interface IConfigDouble extends IConfigValue, IConfigSlideable {
    double getDoubleValue();

    double getDefaultDoubleValue();

    void setDoubleValue(double value);

    double getMinDoubleValue();

    double getMaxDoubleValue();
}
