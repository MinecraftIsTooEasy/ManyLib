package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigEnum;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;

public class ConfigEnum<E extends Enum<E>> extends ConfigBase<ConfigEnum<E>> implements IConfigEnum<E> {
    E value;
    final E defaultValue;
    final Class<E> enumClass;
    final E[] status;
    final int capacity;

    public ConfigEnum(String name, E defaultValue) {
        this(name, defaultValue, null);
    }

    public ConfigEnum(String name, E defaultValue, String comment) {
        super(ConfigType.ENUM, name, comment);
        this.value = defaultValue;
        this.defaultValue = defaultValue;
        this.enumClass = defaultValue.getDeclaringClass();
        this.status = this.enumClass.getEnumConstants();
        this.capacity = this.status.length;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasString(obj, "value")) {
                this.value = E.valueOf(this.enumClass, obj.get("value").getAsString());
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
        obj.add("value", new JsonPrimitive(this.getStringValue()));
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public String getDisplayTextFull() {
        return this.getConfigGuiDisplayName() + ": " + StringUtils.getTranslatedOrFallback("config.enum." + this.getName() + "." + this.getStringValue(), this.getStringValue());
    }

    @Override
    public String getDisplayText() {
        return StringUtils.getTranslatedOrFallback("config.enum." + this.getName() + "." + this.getStringValue(), this.getStringValue());
    }

    @Override
    public E getEnumValue() {
        return this.value;
    }

    @Override
    public E getDefaultEnumValue() {
        return this.defaultValue;
    }

    @Override
    public void setEnumValue(E value) {
        this.value = value;
    }

    @Override
    public E getNext() {
        return this.status[(this.value.ordinal() + 1) % this.capacity];
    }

    @Override
    public int getOrdinal() {
        return this.value.ordinal();
    }

    @Override
    public E[] getAllEnumValues() {
        return this.status;
    }

    @Override
    public boolean isModified() {
        return this.value != this.defaultValue;
    }

    @Override
    public void resetToDefault() {
        this.value = this.defaultValue;
    }

    @Override
    public String getDefaultStringValue() {
        return this.defaultValue.name();
    }

    @Override
    public void setValueFromString(String value) {
        this.value = E.valueOf(this.enumClass, value);
    }

    @Override
    public boolean isModified(String newValue) {
        return this.value != E.valueOf(this.enumClass, newValue);
    }

    @Override
    public String getStringValue() {
        return this.value.name();
    }
}
