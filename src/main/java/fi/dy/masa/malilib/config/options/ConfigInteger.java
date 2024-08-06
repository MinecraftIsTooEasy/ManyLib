package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigInteger;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.MathHelper;

public class ConfigInteger extends ConfigBase<ConfigInteger> implements IConfigInteger {
    final int minValue;
    final int maxValue;
    final int defaultValue;
    int value;
    boolean useSlider;

    public ConfigInteger(String name, int defaultValue) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE, null);
    }

    public ConfigInteger(String name, int defaultValue, String comment) {
        this(name, defaultValue, Integer.MIN_VALUE, Integer.MAX_VALUE, comment);
    }

    public ConfigInteger(String name, int defaultValue, int minValue, int maxValue) {
        this(name, defaultValue, minValue, maxValue, null);
    }

    public ConfigInteger(String name, int defaultValue, int minValue, int maxValue, String comment) {
        this(name, defaultValue, minValue, maxValue, true, comment);
    }

    public ConfigInteger(String name, int defaultValue, int minValue, int maxValue, boolean useSlider, String comment) {
        super(ConfigType.INTEGER, name, comment);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.useSlider = useSlider;
    }

    @Override
    public String getDisplayText() {
        return String.valueOf(this.getIntegerValue());
    }

    @Override
    public double getRatio() {
        if (this.minValue == 0) return ((float) this.value) / ((float) this.maxValue);
        double p = (double) this.value / (double) this.minValue;
        double q = (double) this.maxValue / (double) this.minValue;
        return (p - 1.0d) / (q - 1.0d);
    }

    @Override
    public void setValueByRatio(double ratio) {
        this.setIntegerValue((int) Math.round((this.minValue * (1.0d - ratio) + this.maxValue * ratio)));
    }

    @Override
    public int getIntegerValue() {
        return this.value;
    }

    @Override
    public int getDefaultIntegerValue() {
        return this.defaultValue;
    }

    @Override
    public void setIntegerValue(int value) {
        int oldValue = this.value;
        this.value = this.getClampedValue(value);

        if (oldValue != this.value) {
            this.onValueChanged();
        }
    }

    @Override
    public int getMinIntegerValue() {
        return this.minValue;
    }

    @Override
    public int getMaxIntegerValue() {
        return this.maxValue;
    }

    @Override
    public boolean shouldUseSlider() {
        return this.useSlider;
    }

    @Override
    public void toggleUseSlider() {
        this.useSlider = !this.useSlider;
    }

    protected int getClampedValue(int value) {
        return value > this.maxValue ? this.maxValue : (Math.max(value, this.minValue));
    }

    @Override
    public boolean isModified() {
        return this.value != this.defaultValue;
    }

    @Override
    public boolean isModified(String newValue) {
        try {
            return Integer.parseInt(newValue) != this.defaultValue;
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public void resetToDefault() {
        this.setIntegerValue(this.defaultValue);
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
        this.setIntegerValue(MathHelper.parseIntWithDefault(value, this.defaultValue));
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasInteger(obj, "value")) {
                this.setIntegerValue(obj.get("value").getAsInt());
            } else {
                ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
            if (JsonUtils.hasBoolean(obj, "useSlider")) {
                this.useSlider = obj.get("useSlider").getAsBoolean();
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
        obj.add("useSlider", new JsonPrimitive(this.useSlider));
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }
}
