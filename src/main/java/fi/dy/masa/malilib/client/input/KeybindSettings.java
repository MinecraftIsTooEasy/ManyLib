package fi.dy.masa.malilib.client.input;

import com.google.gson.JsonObject;
import fi.dy.masa.malilib.localization.KeybindSettingsText;
import fi.dy.masa.malilib.util.JsonUtils;

import java.util.ArrayList;
import java.util.List;

public class KeybindSettings {
    public static final KeybindSettings DEFAULT = new KeybindSettings(Context.INGAME, KeyAction.PRESS, false, true, false, true);
    public static final KeybindSettings EXCLUSIVE = new KeybindSettings(Context.INGAME, KeyAction.PRESS, false, true, true, true);
    public static final KeybindSettings RELEASE = new KeybindSettings(Context.INGAME, KeyAction.RELEASE, false, true, false, false);
    public static final KeybindSettings RELEASE_ALLOW_EXTRA = new KeybindSettings(Context.INGAME, KeyAction.RELEASE, true, true, false, false);
    public static final KeybindSettings RELEASE_EXCLUSIVE = new KeybindSettings(Context.INGAME, KeyAction.RELEASE, false, true, true, true);
    public static final KeybindSettings NOCANCEL = new KeybindSettings(Context.INGAME, KeyAction.PRESS, false, true, false, false);
    public static final KeybindSettings PRESS_ALLOWEXTRA = new KeybindSettings(Context.INGAME, KeyAction.PRESS, true, true, false, true);
    public static final KeybindSettings PRESS_ALLOWEXTRA_EMPTY = new KeybindSettings(Context.INGAME, KeyAction.PRESS, true, true, false, true, true);
    public static final KeybindSettings PRESS_NON_ORDER_SENSITIVE = new KeybindSettings(Context.INGAME, KeyAction.PRESS, false, false, false, true);
    public static final KeybindSettings INGAME_BOTH = new KeybindSettings(Context.INGAME, KeyAction.BOTH, false, true, false, true);
    public static final KeybindSettings MODIFIER_INGAME = new KeybindSettings(Context.INGAME, KeyAction.PRESS, true, false, false, false);
    public static final KeybindSettings MODIFIER_INGAME_EMPTY = new KeybindSettings(Context.INGAME, KeyAction.PRESS, true, false, false, false, true);
    public static final KeybindSettings MODIFIER_GUI = new KeybindSettings(Context.GUI, KeyAction.PRESS, true, false, false, false);
    public static final KeybindSettings GUI = new KeybindSettings(Context.GUI, KeyAction.PRESS, false, true, false, true);

    private final Context context;
    private final KeyAction activateOn;
    private final boolean allowExtraKeys;
    private final boolean orderSensitive;
    private final boolean exclusive;
    private final boolean cancel;
    private final boolean allowEmpty;

    private KeybindSettings(Context context, KeyAction activateOn, boolean allowExtraKeys, boolean orderSensitive, boolean exclusive, boolean cancel) {
        this(context, activateOn, allowExtraKeys, orderSensitive, exclusive, cancel, false);
    }

    private KeybindSettings(Context context, KeyAction activateOn, boolean allowExtraKeys, boolean orderSensitive, boolean exclusive, boolean cancel, boolean allowEmpty) {
        this.context = context;
        this.activateOn = activateOn;
        this.allowExtraKeys = allowExtraKeys;
        this.orderSensitive = orderSensitive;
        this.exclusive = exclusive;
        this.cancel = cancel;
        this.allowEmpty = allowEmpty;
    }

    public static KeybindSettings create(Context context, KeyAction activateOn, boolean allowExtraKeys, boolean orderSensitive, boolean exclusive, boolean cancel) {
        return create(context, activateOn, allowExtraKeys, orderSensitive, exclusive, cancel, false);
    }

    public static KeybindSettings create(Context context, KeyAction activateOn, boolean allowExtraKeys, boolean orderSensitive, boolean exclusive, boolean cancel, boolean allowEmpty) {
        return new KeybindSettings(context, activateOn, allowExtraKeys, orderSensitive, exclusive, cancel, allowEmpty);
    }

    public Context getContext() {
        return this.context;
    }

    public KeyAction getActivateOn() {
        return this.activateOn;
    }

    public boolean getAllowEmpty() {
        return this.allowEmpty;
    }

    public boolean getAllowExtraKeys() {
        return this.allowExtraKeys;
    }

    public boolean isOrderSensitive() {
        return this.orderSensitive;
    }

    public boolean isExclusive() {
        return this.exclusive;
    }

    public boolean shouldCancel() {
        return this.cancel;
    }

    public JsonObject toJson() {
        JsonObject obj = new JsonObject();

        obj.addProperty("context", this.context.name());
        obj.addProperty("activate_on", this.activateOn.name());
        obj.addProperty("allow_extra_keys", this.allowExtraKeys);
        obj.addProperty("order_sensitive", this.orderSensitive);
        obj.addProperty("exclusive", this.exclusive);
        obj.addProperty("cancel", this.cancel);
        obj.addProperty("allow_empty", this.allowEmpty);

        return obj;
    }

    public static KeybindSettings fromJson(JsonObject obj) {
        Context context = Context.INGAME;
        KeyAction activateOn = KeyAction.PRESS;
        String contextStr = JsonUtils.getString(obj, "context");
        String activateStr = JsonUtils.getString(obj, "activate_on");

        if (contextStr != null) {
            for (Context ctx : Context.values()) {
                if (ctx.name().equalsIgnoreCase(contextStr)) {
                    context = ctx;
                    break;
                }
            }
        }

        if (activateStr != null) {
            for (KeyAction act : KeyAction.values()) {
                if (act.name().equalsIgnoreCase(activateStr)) {
                    activateOn = act;
                    break;
                }
            }
        }

        boolean allowExtraKeys = JsonUtils.getBoolean(obj, "allow_extra_keys");
        boolean orderSensitive = JsonUtils.getBooleanOrDefault(obj, "order_sensitive", true);
        boolean exclusive = JsonUtils.getBooleanOrDefault(obj, "exclusive", true);
        boolean cancel = JsonUtils.getBooleanOrDefault(obj, "cancel", true);
        boolean allowEmpty = JsonUtils.getBoolean(obj, "allow_empty");

        return create(context, activateOn, allowExtraKeys, orderSensitive, exclusive, cancel, allowEmpty);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeybindSettings other = (KeybindSettings) obj;
        if (activateOn != other.activateOn)
            return false;
        if (context != other.context)
            return false;
        if (allowEmpty != other.allowEmpty)
            return false;
        if (allowExtraKeys != other.allowExtraKeys)
            return false;
        if (cancel != other.cancel)
            return false;
        if (exclusive != other.exclusive)
            return false;
        if (orderSensitive != other.orderSensitive)
            return false;
        return true;
    }

    public List<String> toStringList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(KeybindSettingsText.CONTEXT.translate() + ": " + this.context.name());
        list.add(KeybindSettingsText.ACTIVATE_ON.translate() + ": " + this.activateOn.name());
        list.add(KeybindSettingsText.ALLOW_EXTRA_KEYS.translate() + ": " + this.allowExtraKeys);
        list.add(KeybindSettingsText.ORDER_SENSITIVE.translate() + ": " + this.orderSensitive);
        list.add(KeybindSettingsText.EXCLUSIVE.translate() + ": " + this.exclusive);
        list.add(KeybindSettingsText.CANCEL.translate() + ": " + this.cancel);
        list.add(KeybindSettingsText.ALLOW_EMPTY.translate() + ": " + this.allowEmpty);
        return list;
    }

    public enum Context {
        INGAME("ingame", "malilib.label.key_context.ingame"),
        GUI("gui", "malilib.label.key_context.gui"),
        ANY("any", "malilib.label.key_context.any");

        private final String configString;
        private final String translationKey;

        Context(String configString, String translationKey) {
            this.configString = configString;
            this.translationKey = translationKey;
        }
    }
}
