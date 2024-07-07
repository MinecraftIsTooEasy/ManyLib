package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigHotkey;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import net.minecraft.KeyBinding;
import net.minecraft.Minecraft;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ConfigHotkey extends ConfigBase<ConfigHotkey> implements IConfigHotkey {
    protected final int defaultKey;

    private final KeyBinding keyBinding;

    @Nullable
    protected Consumer<Minecraft> hotKeyPressCallBack;

    public ConfigHotkey(String name) {
        this(name, KeyCodes.KEY_NONE, name);
    }

    public ConfigHotkey(String name, String comment) {
        this(name, KeyCodes.KEY_NONE, comment);
    }

    public ConfigHotkey(String name, int hotkey) {
        this(name, hotkey, name);
    }

    public ConfigHotkey(String name, int defaultKey, String comment) {
        super(ConfigType.HOTKEY, name, comment);
        this.defaultKey = defaultKey;
        this.keyBinding = new KeyBinding(name, defaultKey);
    }

    public boolean isPressed() {
        return this.keyBinding.isPressed();
    }

    @Override
    public boolean isModified() {
        return this.getKeyCode() != this.defaultKey;
    }

    @Override
    public boolean isModified(String newValue) {
        try {
            return Integer.parseInt(newValue) != this.defaultKey;
        } catch (Exception ignored) {
        }
        return true;
    }

    @Override
    public void resetToDefault() {
        this.setHotKeyAndHash(this.defaultKey);
    }

    @Override
    public String getStringValue() {
        return String.valueOf(this.keyBinding.keyCode);
    }

    @Override
    public String getDefaultStringValue() {
        return String.valueOf(this.defaultKey);
    }

    @Override
    public void setValueFromString(String value) {
        try {
            this.setHotKeyAndHash(Integer.parseInt(value));
        } catch (Exception e) {
            ManyLib.logger.warn("Failed to set config value for {} from the string '{}'", this.getName(), value, e);
        }
    }

    @Override
    public void setValueFromJsonElement(JsonElement element) {
        try {
            JsonObject obj = element.getAsJsonObject();
            if (JsonUtils.hasString(obj, "hotkey")) {
                this.setHotKeyWithoutHash(KeyCodes.getKeyCodeFromName(obj.get("hotkey").getAsString()));
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
        obj.add("hotkey", new JsonPrimitive(this.getKeyName()));
        if (this.getComment() != null) {
            obj.add("comment", new JsonPrimitive(this.getComment()));
        }
        return obj;
    }

    @Override
    public int getKeyCode() {
        return this.keyBinding.keyCode;
    }

    @Override
    public String getKeyName() {
        return KeyCodes.getNameForKeyCode(this.getKeyCode());
    }

    @Override
    public void setHotKeyAndHash(int keyCode) {
        this.keyBinding.keyCode = keyCode;
        KeyBinding.resetKeyBindingArrayAndHash();
    }

    @Override
    public void setHotKeyWithoutHash(int keyCode) {
        this.keyBinding.keyCode = keyCode;
    }

    @Override
    public void setHotKeyPressCallBack(@Nullable Consumer<Minecraft> callBack) {
        this.hotKeyPressCallBack = callBack;
    }

    @Override
    public void onPressed(Minecraft minecraft) {
        if (this.hotKeyPressCallBack != null) {
            this.hotKeyPressCallBack.accept(minecraft);
        }
    }
}
