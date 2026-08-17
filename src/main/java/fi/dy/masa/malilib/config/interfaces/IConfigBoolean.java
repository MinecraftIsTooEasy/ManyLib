package fi.dy.masa.malilib.config.interfaces;

public interface IConfigBoolean extends IConfigValue, IConfigCyclic {
    boolean getBooleanValue();

    boolean getDefaultBooleanValue();

    void setBooleanValue(boolean value);

    default void toggleBooleanValue() {
        this.setBooleanValue(!this.getBooleanValue());
    }
}
