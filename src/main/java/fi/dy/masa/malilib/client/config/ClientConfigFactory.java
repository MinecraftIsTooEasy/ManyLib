package fi.dy.masa.malilib.client.config;

import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.client.config.options.ConfigToggle;
import fi.dy.masa.malilib.client.input.IKeybind;
import fi.dy.masa.malilib.client.input.KeyCodes;
import fi.dy.masa.malilib.client.input.KeybindMulti;
import fi.dy.masa.malilib.client.input.KeybindSettings;

public class ClientConfigFactory {
    public static ConfigToggle ofToggle(String name) {
        return ofToggle(name, "", false, null);
    }

    public static ConfigToggle ofToggle(String name, int defaultKey, boolean defaultBooleanValue, String comment) {
        return ofToggle(name, KeyCodes.getNameForKey(defaultKey), defaultBooleanValue, comment);
    }

    public static ConfigToggle ofToggle(String name, String defaultStorageString, boolean defaultBooleanValue, String comment) {
        return new ConfigToggle(name, defaultStorageString, defaultBooleanValue, comment);
    }

    public static ConfigHotkey ofHotkey(String name) {
        return ofHotkey(name, KeybindSettings.DEFAULT, null);
    }

    public static ConfigHotkey ofHotkey(String name, int defaultKey) {
        return ofHotkey(name, KeyCodes.getNameForKey(defaultKey));
    }

    public static ConfigHotkey ofHotkey(String name, int defaultKey, String comment) {
        return ofHotkey(name, KeyCodes.getNameForKey(defaultKey), comment);
    }

    public static ConfigHotkey ofHotkey(String name, String storageString) {
        return ofHotkey(name, storageString, null);
    }

    public static ConfigHotkey ofHotkey(String name, String storageString, String comment) {
        return ofHotkey(name, storageString, KeybindSettings.DEFAULT, comment);
    }

    public static ConfigHotkey ofHotkey(String name, KeybindSettings settings, String comment) {
        return ofHotkey(name, "", settings, comment);
    }

    public static ConfigHotkey ofHotkey(String name, String storageString, KeybindSettings settings, String comment) {
        return ofHotkey(name, KeybindMulti.fromStorageString(storageString, settings), comment);
    }

    public static ConfigHotkey ofHotkey(String name, IKeybind keybind, String comment) {
        return new ConfigHotkey(name, keybind, comment);
    }
}
