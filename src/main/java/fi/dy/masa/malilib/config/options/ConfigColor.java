package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigColor;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.StringUtils;

public class ConfigColor extends ConfigInteger implements IConfigColor {
    private Color4f color;
    private Color4f defaultColor;

    public ConfigColor(String name, String defaultValue) {
        this(name, defaultValue, null);
    }

    public ConfigColor(String name, String defaultValue, String comment) {
        super(name, StringUtils.getColor(defaultValue, 0), comment);
        this.defaultColor = Color4f.fromColor(this.getIntegerValue());
        this.color = this.defaultColor;
    }

    @Override
    public ConfigType getType() {
        return ConfigType.COLOR;
    }

    @Override
    public Color4f getColor() {
        return this.color;
    }

    @Override
    public Color4f getDefaultColor() {
        return this.defaultColor;
    }

    @Override
    public String getStringValue() {
        return String.format("#%08X", this.getIntegerValue());
    }

    @Override
    public String getDefaultStringValue() {
        return String.format("#%08X", this.getDefaultIntegerValue());
    }

    @Override
    public void setValueFromString(String value) {
        this.setIntegerValue(StringUtils.getColor(value, 0));
    }

    @Override
    public void setIntegerValue(int value) {
        this.color = Color4f.fromColor(value);
        super.setIntegerValue(value); // This also calls the callback, if set
    }

    public void setColor(Color4f color) {
        this.color = color;
        super.setIntegerValue(color.intValue);
    }

    @Override
    public boolean isModified(String newValue) {
        try {
            return StringUtils.getColor(newValue, 0) != this.getDefaultIntegerValue();
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasString(obj, "color")) {
                this.value = this.getClampedValue(StringUtils.getColor(obj.get("color").getAsString(), 0));
                this.color = Color4f.fromColor(this.value);
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
        obj.add("color", new JsonPrimitive(this.getStringValue()));
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public int getColorInteger() {
        return this.color.intValue;
    }

    @Override
    public String getColorString() {
        return this.getStringValue();
    }
}
