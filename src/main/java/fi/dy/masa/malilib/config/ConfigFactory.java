package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.KeyCodes;

public class ConfigFactory {
    public static ConfigBoolean ofBoolean(String name) {
        return ofBoolean(name, null);
    }

    public static ConfigBoolean ofBoolean(String name, String comment) {
        return new ConfigBoolean(name, comment);
    }

    public static ConfigInteger ofInteger(String name, int defaultValue, int minValue, int maxValue) {
        return ofInteger(name, defaultValue, minValue, maxValue, null);
    }

    public static ConfigInteger ofInteger(String name, int defaultValue, int minValue, int maxValue, String comment) {
        return new ConfigInteger(name, defaultValue, minValue, maxValue, comment);
    }

    public static ConfigDouble ofDouble(String name, double defaultValue, double minValue, double maxValue) {
        return ofDouble(name, defaultValue, minValue, maxValue, null);
    }

    public static ConfigDouble ofDouble(String name, double defaultValue, double minValue, double maxValue, String comment) {
        return ofDouble(name, defaultValue, minValue, maxValue, true, comment);
    }

    public static ConfigDouble ofDouble(String name, double defaultValue, double minValue, double maxValue, boolean useSlider, String comment) {
        return new ConfigDouble(name, defaultValue, minValue, maxValue, useSlider, comment);
    }

    public static ConfigToggle ofToggle(String name) {
        return ofToggle(name, null);
    }

    public static ConfigToggle ofToggle(String name, String comment) {
        return new ConfigToggle(name, comment);
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

    public static <T extends Enum<T>> ConfigEnum<T> ofEnum(String name, T defaultValue) {
        return ofEnum(name, defaultValue, null);
    }

    public static <T extends Enum<T>> ConfigEnum<T> ofEnum(String name, T defaultValue, String comment) {
        return new ConfigEnum<>(name, defaultValue, comment);
    }
}
