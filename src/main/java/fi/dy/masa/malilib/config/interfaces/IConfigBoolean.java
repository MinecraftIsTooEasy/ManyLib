package fi.dy.masa.malilib.config.interfaces;

public interface IConfigBoolean extends IConfigValue, IConfigPeriodic {
    @Deprecated(since = "1.1.1")
    boolean get();

    boolean getBooleanValue();

    boolean getDefaultBooleanValue();

    void setBooleanValue(boolean value);

    default void toggleBooleanValue() {
        this.setBooleanValue(!this.getBooleanValue());
    }
}
