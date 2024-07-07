package fi.dy.masa.malilib.config.interfaces;

public interface IConfigToggle extends IConfigValue {
    boolean isOn();

    boolean getDefaultStatus();

    void setIsOn(boolean status);

    default void toggle() {
        this.setIsOn(!this.isOn());
    }
}
