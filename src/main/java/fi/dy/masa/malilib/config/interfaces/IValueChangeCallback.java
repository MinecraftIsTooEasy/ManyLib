package fi.dy.masa.malilib.config.interfaces;

@FunctionalInterface
public interface IValueChangeCallback<T extends IConfigBase> {
    void onValueChanged(T config);
}
