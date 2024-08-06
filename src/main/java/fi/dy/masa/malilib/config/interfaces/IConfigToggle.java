package fi.dy.masa.malilib.config.interfaces;

public interface IConfigToggle extends IConfigValue, IConfigPeriodic {
    boolean isOn();

    boolean getDefaultStatus();

    void setIsOn(boolean status);

    default void toggle() {
        this.setIsOn(!this.isOn());
    }
}
