package fi.dy.masa.malilib.client.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.client.input.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;
import fi.dy.masa.malilib.util.JsonUtils;

public class ConfigToggle extends ConfigHotkey implements IConfigBoolean {
    private boolean booleanValue;
    private final boolean defaultBooleanValue;

    public ConfigToggle(String name) {
        this(name, false);
    }

    public ConfigToggle(String name, boolean defaultBooleanValue) {
        this(name, "", defaultBooleanValue, null);
    }

    public ConfigToggle(String name, String defaultStorageString, boolean defaultBooleanValue, String comment) {
        super(name, defaultStorageString, comment);
        this.booleanValue = defaultBooleanValue;
        this.defaultBooleanValue = defaultBooleanValue;
        this.getKeybind().setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
    }

    @Override
    public ConfigType getType() {
        return ConfigTypes.TOGGLE;
    }

    @Override
    public boolean isModified() {
        return super.isModified() || this.booleanValue != this.defaultBooleanValue;
    }

    @Override
    public void resetToDefault() {
        super.resetToDefault();
        this.booleanValue = this.defaultBooleanValue;
    }

    @Override
    public JsonElement getAsJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("enabled", new JsonPrimitive(this.booleanValue));
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
                this.booleanValue = obj.get("enabled").getAsBoolean();
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
    public boolean getBooleanValue() {
        return this.booleanValue;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultBooleanValue;
    }

    @Override
    public void setBooleanValue(boolean value) {
        boolean oldValue = this.booleanValue;
        this.booleanValue = value;

        if (oldValue != this.booleanValue) {
            this.onValueChanged();
        }
    }

    @Override
    public void cycle(boolean forward) {
        this.toggleBooleanValue();
    }
}
