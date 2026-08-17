package fi.dy.masa.malilib.localization;

public enum KeybindSettingsText implements ITranslatable{
    SETTINGS("manyLib.keybind.settings"),
    CONTEXT("manyLib.keybind.settings.context"),
    ACTIVATE_ON("manyLib.keybind.settings.activate_on"),
    ALLOW_EXTRA_KEYS("manyLib.keybind.settings.allow_extra_keys"),
    ORDER_SENSITIVE("manyLib.keybind.settings.order_sensitive"),
    EXCLUSIVE("manyLib.keybind.settings.exclusive"),
    CANCEL("manyLib.keybind.settings.cancel"),
    ALLOW_EMPTY("manyLib.keybind.settings.allow_empty"),
    ;

    private final String key;

    KeybindSettingsText(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
