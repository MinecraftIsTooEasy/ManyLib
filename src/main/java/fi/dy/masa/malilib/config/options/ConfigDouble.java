package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigDouble;
import fi.dy.masa.malilib.util.JsonUtils;
import net.minecraft.MathHelper;

public class ConfigDouble extends ConfigBase<ConfigDouble> implements IConfigDouble {
    private final double minValue;
    private final double maxValue;
    private final double defaultValue;
    private double value;
    private boolean useSlider;

    public ConfigDouble(String name, double defaultValue) {
        this(name, defaultValue, -Double.MAX_VALUE, Double.MAX_VALUE, null);
    }

    public ConfigDouble(String name, double defaultValue, String comment) {
        this(name, defaultValue, -Double.MAX_VALUE, Double.MAX_VALUE, comment);
    }

    public ConfigDouble(String name, double defaultValue, double minValue, double maxValue) {
        this(name, defaultValue, minValue, maxValue, null);
    }

    public ConfigDouble(String name, double defaultValue, double minValue, double maxValue, String comment) {
        this(name, defaultValue, minValue, maxValue, true, comment);
    }

    public ConfigDouble(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment) {
        super(ConfigType.DOUBLE, name, comment);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.useSlider = useSlider;
    }

    @Override
    public String getDisplayText() {
        int percent = (int) (this.getRatio() * 100.0f);
        return percent + "%";
    }

    @Override
    public boolean shouldUseSlider() {
        return this.useSlider;
    }

    @Override
    public void toggleUseSlider() {
        this.useSlider = !this.useSlider;
    }

    @Override
    public double getDoubleValue() {
        return this.value;
    }

    @Override
    public double getDefaultDoubleValue() {
        return this.defaultValue;
    }

    @Override
    public double getRatio() {
        if (this.minValue == 0.0d) return this.value / this.maxValue;
        double p = value / minValue;
        double q = maxValue / minValue;
        return (p - 1.0d) / (q - 1.0d);
    }

    @Override
    public void setValueByRatio(double ratio) {
        this.setDoubleValue((1.0d - ratio) * this.minValue + ratio * this.maxValue);
    }

    @Override
    public void setDoubleValue(double value) {
        double oldValue = this.value;
        this.value = this.getClampedValue(value);

        if (oldValue != this.value) {
            this.onValueChanged();
        }
    }

    @Override
    public double getMinDoubleValue() {
        return this.minValue;
    }

    @Override
    public double getMaxDoubleValue() {
        return this.maxValue;
    }

    protected double getClampedValue(double value) {
        return value > this.maxValue ? this.maxValue : (Math.max(value, this.minValue));
    }

    @Override
    public boolean isModified() {
        return this.value != this.defaultValue;
    }

    @Override
    public boolean isModified(String newValue) {
        try {
            return Double.parseDouble(newValue) != this.defaultValue;
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public void resetToDefault() {
        this.setDoubleValue(this.defaultValue);
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
        this.setDoubleValue(MathHelper.parseDoubleWithDefault(value, this.defaultValue));
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasDouble(obj, "value")) {
                this.setDoubleValue(obj.get("value").getAsDouble());
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
