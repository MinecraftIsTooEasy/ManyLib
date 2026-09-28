package fi.dy.masa.malilib.localization;

public enum KeybindText implements ITranslatable{
    EMPTY("keybind.empty"),
    ;

    private final String key;

    KeybindText(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
