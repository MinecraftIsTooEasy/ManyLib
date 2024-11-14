package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.KeyCodes;

import java.util.List;

public class ConfigHotkey extends ConfigBase<ConfigHotkey> implements IHotkey, IConfigDisplay {
    protected final IKeybind keybind;

    public ConfigHotkey(String name) {
        this(name, "", null);
    }

    @Deprecated(since = "2.2.0")
    public ConfigHotkey(String name, String comment) {
        this(name, "", comment);
    }

    public ConfigHotkey(String name, String defaultStorageString, String comment) {
        this(name, KeybindMulti.fromStorageString(defaultStorageString, KeybindSettings.DEFAULT), comment);
    }

    public ConfigHotkey(String name, int hotkey) {
        this(name, hotkey, null);
    }

    public ConfigHotkey(String name, int defaultKey, String comment) {
        this(name, KeyCodes.getNameForKey(defaultKey), comment);
    }

    public ConfigHotkey(String name, IKeybind keybind, String comment) {
        super(ConfigType.HOTKEY, name, comment);
        this.keybind = keybind;
    }

    @Override
    public IKeybind getKeybind() {
        return this.keybind;
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
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
    public JsonElement getAsJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("hotkey", this.keybind.getAsJsonElement());
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public String getDisplayText() {
        return this.keybind.getKeysDisplayString();
    }

    @Deprecated(since = "2.1.0", forRemoval = true)
    public int getKeyCode() {
        List<Integer> keys = this.keybind.getKeys();
        if (keys.isEmpty()) return KeyCodes.KEY_NONE;
        return keys.get(keys.size() - 1);
    }

    @Deprecated
    public boolean isPressed() {
        return this.keybind.isPressed();
    }
}
