package fi.dy.masa.malilib.config.options;

import fi.dy.masa.malilib.config.interfaces.*;
import org.jetbrains.annotations.Nullable;

public abstract class ConfigBase<T extends IConfigBase> implements IConfigBase, IConfigResettable, IConfigNotifiable<T> {
    private final ConfigType type;
    private final String name;
    private final String comment;
    private IValueChangeCallback<T> callback;

    public ConfigBase(ConfigType type, String name, String comment) {
        this.type = type;
        this.name = name;
        this.comment = comment;
    }

    @Override
    public ConfigType getType() {
        return this.type;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @Nullable
    public String getComment() {
        return this.comment;
    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<T> callback) {
        this.callback = callback;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onValueChanged() {
        if (this.callback != null) {
            this.callback.onValueChanged((T) this);
        }
    }
}
