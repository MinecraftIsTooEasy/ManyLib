package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigString;
import fi.dy.masa.malilib.util.JsonUtils;

public class ConfigString extends ConfigBase<ConfigString> implements IConfigString {
    private final String defaultValue;

    private String value;

    public ConfigString(String name, String defaultValue) {
        this(name, defaultValue, null);
    }

    public ConfigString(String name, String defaultValue, String comment) {
        super(ConfigType.STRING, name, comment);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasString(obj, "value")) {
                this.value = obj.get("value").getAsString();
            } else {
                ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        } catch (Exception e) {
            ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }

    @Override
    public JsonElement getAsJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("value", new JsonPrimitive(this.value));
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public boolean isModified() {
        return !this.value.equals(this.defaultValue);
    }

    @Override
    public void resetToDefault() {
        this.value = this.defaultValue;
    }

    @Override
    public String getStringValue() {
        return this.value;
    }

    @Override
    public String getDefaultStringValue() {
        return this.defaultValue;
    }

    @Override
    public void setValueFromString(String value) {
        this.value = value;
    }

    @Override
    public boolean isModified(String newValue) {
        return newValue.equals(this.value);
    }
}
