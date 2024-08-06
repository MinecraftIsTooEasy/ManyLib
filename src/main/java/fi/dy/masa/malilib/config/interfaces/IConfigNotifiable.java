package fi.dy.masa.malilib.config.interfaces;

public interface IConfigNotifiable<T extends IConfigBase> {
    void onValueChanged();

    void setValueChangeCallback(IValueChangeCallback<T> callback);
}
