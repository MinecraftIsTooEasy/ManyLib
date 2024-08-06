package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.I18n;

public class ConfigBoolean extends ConfigBase<ConfigBoolean> implements IConfigBoolean {
    private final boolean defaultValue;
    private boolean value;

    public ConfigBoolean(String name) {
        this(name, false, null);
    }

    public ConfigBoolean(String name, String comment) {
        this(name, false, comment);
    }

    public ConfigBoolean(String name, boolean defaultValue) {
        this(name, defaultValue, null);
    }

    public ConfigBoolean(String name, boolean defaultValue, String comment) {
        super(ConfigType.BOOLEAN, name, comment);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    @Override
    public String getDisplayText() {
        if (this.value) {
            return GuiBase.TXT_GREEN + I18n.getString("boolean.true");
        } else {
            return GuiBase.TXT_RED + I18n.getString("boolean.false");
        }
    }

    @Override
    public boolean getBooleanValue() {
        return this.value;
    }

    @Override
    public boolean getDefaultBooleanValue() {
        return this.defaultValue;
    }

    @Override
    public void setBooleanValue(boolean value) {
        boolean oldValue = this.value;
        this.value = value;

        if (oldValue != this.value) {
            this.onValueChanged();
        }
    }

    @Override
    public boolean isModified() {
        return this.value != this.defaultValue;
    }

    @Override
    public boolean isModified(String newValue) {
        return Boolean.parseBoolean(newValue) != this.defaultValue;
    }

    @Override
    public void resetToDefault() {
        this.setBooleanValue(this.defaultValue);
    }

    @Override
    public String getStringValue() {
        return String.valueOf(this.value);
    }

    @Override
    public String getDefaultStringValue() {
        return String.valueOf(this.defaultValue);
    }

    @Override
    public void setValueFromString(String value) {
        this.value = Boolean.parseBoolean(value);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasBoolean(obj, "value")) {
                this.value = obj.get("value").getAsBoolean();
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
    public void next() {
        this.toggleBooleanValue();
    }
}
