package fi.dy.masa.malilib.config.options;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigToggle;
import fi.dy.masa.malilib.util.JsonUtils;
import fi.dy.masa.malilib.util.KeyCodes;
import fi.dy.masa.malilib.util.RenderUtils;
import net.minecraft.EnumChatFormatting;
import net.minecraft.I18n;

public class ConfigToggle extends ConfigHotkey implements IConfigToggle {
    private boolean status;

    private final boolean defaultStatus;

    public ConfigToggle(String name) {
        this(name, null);
    }

    public ConfigToggle(String name, String comment) {
        this(name, KeyCodes.KEY_NONE, false, comment);
    }

    public ConfigToggle(String name, boolean defaultStatus) {
        this(name, KeyCodes.KEY_NONE, defaultStatus, null);
    }

    public ConfigToggle(String name, int keycode, boolean defaultStatus, String comment) {
        super(name, keycode, comment);
        this.status = defaultStatus;
        this.defaultStatus = defaultStatus;
        this.hotKeyPressCallBack = minecraft -> {
            this.toggle();
            String status;
            if (this.isOn()) {
                status = EnumChatFormatting.GREEN + I18n.getString("toggle.on");
            } else {
                status = EnumChatFormatting.RED + I18n.getString("toggle.off");
            }
            String message = I18n.getStringParams("manyLib.configToggle.toggle", this.getConfigGuiDisplayName(), status);
            RenderUtils.setGuiIngameInfo(message);
        };
    }

    @Override
    public ConfigType getType() {
        return ConfigType.TOGGLE;
    }

    @Override
    public boolean isModified() {
        return super.isModified() || this.status != this.defaultStatus;
    }

    @Override
    public void resetToDefault() {
        super.resetToDefault();
        this.status = this.defaultStatus;
    }

    @Override
    public JsonElement getAsJsonElement() {
        JsonObject obj = new JsonObject();
        obj.add("enabled", new JsonPrimitive(this.status));
        obj.add("hotkey", new JsonPrimitive(this.getKeyName()));
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
                this.status = obj.get("enabled").getAsBoolean();
            } else {
                ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
            if (JsonUtils.hasString(obj, "hotkey")) {
                this.setHotKeyAndHash(KeyCodes.getKeyCodeFromName(obj.get("hotkey").getAsString()));
            } else {
                ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        } catch (Exception e) {
            ManyLib.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }

    @Override
    public boolean isOn() {
        return this.status;
    }

    @Override
    public boolean getDefaultStatus() {
        return this.defaultStatus;
    }

    @Override
    public void setIsOn(boolean status) {
        this.status = status;
    }
}
