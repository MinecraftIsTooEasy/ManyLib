package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigStringList;
import fi.dy.masa.malilib.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ConfigStringList extends ConfigBase<ConfigStringList> implements IConfigStringList {
    final List<String> defaultValue;
    final List<String> value;

    public ConfigStringList(String name, List<String> defaultValue) {
        this(name, defaultValue, null);
    }

    public ConfigStringList(String name, List<String> defaultValue, String comment) {
        super(ConfigType.STRINGLIST, name, comment);
        this.defaultValue = defaultValue;
        this.value = new ArrayList<>();
        this.value.addAll(defaultValue);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasArray(obj, "value")) {
                this.value.clear();
                obj.get("value").getAsJsonArray().forEach(x -> this.value.add(x.getAsString()));
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
        JsonArray jsonArray = new JsonArray();
        this.value.forEach(x -> jsonArray.add(new JsonPrimitive(x)));
        obj.add("value", jsonArray);
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public boolean isModified() {
        if (this.value.size() != this.defaultValue.size()) return true;
        for (int index = 0; index < this.value.size(); index++) {
            if (!this.value.get(index).equals(this.defaultValue.get(index))) return true;
        }
        return false;
    }

    @Override
    public void resetToDefault() {
        this.value.clear();
        this.value.addAll(this.defaultValue);
    }

    @Override
    public List<String> getStringListValue() {
        return this.value;
    }

    @Override
    public List<String> getDefaultStringListValue() {
        return this.defaultValue;
    }
}
