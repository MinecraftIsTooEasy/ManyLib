package fi.dy.masa.malilib.client.input;

public enum KeyAction {
    PRESS("press", "malilib.label.key_action.press"),
    RELEASE("release", "malilib.label.key_action.release"),
    BOTH("both", "malilib.label.key_action.both");

    private final String configString;
    private final String translationKey;

    KeyAction(String configString, String translationKey) {
        this.configString = configString;
        this.translationKey = translationKey;
    }
}
