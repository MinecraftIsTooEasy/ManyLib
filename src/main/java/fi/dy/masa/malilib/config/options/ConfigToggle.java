package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;
import fi.dy.masa.malilib.config.interfaces.IConfigToggle;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.KeyCodes;

public class ConfigToggle extends ConfigHotkey implements IConfigToggle, IConfigBoolean {
    private boolean status;
    private final boolean defaultStatus;

    public ConfigToggle(String name) {
        this(name, null);
    }

    public ConfigToggle(String name, String comment) {
        this(name, "", false, comment);
    }

    public ConfigToggle(String name, boolean defaultStatus) {
        this(name, "", defaultStatus, null);
    }

    public ConfigToggle(String name, int defaultKey, boolean defaultStatus, String comment) {
        this(name, KeyCodes.getNameForKey(defaultKey), defaultStatus, comment);
    }

    public ConfigToggle(String name, String defaultStorageString, boolean defaultStatus, String comment) {
        super(name, defaultStorageString, comment);
        this.status = defaultStatus;
        this.defaultStatus = defaultStatus;
        this.getKeybind().setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }

    @Override
    public ConfigType getType() {
        return ConfigType.TOGGLE;
    }

    @Override
    public boolean isModified() {
        return super.isModified() || this.status != this.defaultStatus;
    }

    @Override
    public void resetToDefault() {
        super.resetToDefault();
        this.status = this.defaultStatus;
    }

    @Override
    public JsonElement getAsJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("enabled", new JsonPrimitive(this.status));
        obj.add("hotkey", this.keybind.getAsJsonElement());
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasBoolean(obj, "enabled")) {
                this.status = obj.get("enabled").getAsBoolean();
            } else {
                ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
            if (JsonUtils.hasObject(obj, "hotkey")) {
                this.keybind.setValueFromJsonElement(obj.get("hotkey").getAsJsonObject());
            } else {
                ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        } catch (Exception e) {
            ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }

    @Override
    public boolean isOn() {
        return this.status;
    }

    @Override
    public boolean getDefaultStatus() {
        return this.defaultStatus;
    }

    @Override
    public void setIsOn(boolean status) {
        boolean oldValue = this.status;
        this.status = status;

        if (oldValue != this.status) {
            this.onValueChanged();
        }
    }

    @Override
    public void next() {
        this.toggle();
    }

    @Override
    public boolean getBooleanValue() {
        return this.status;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultStatus;
    }

    @Override
    public void setBooleanValue(boolean value) {
        this.setIsOn(value);
    }
}
