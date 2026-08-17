package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigInteger;

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

    public static <T extends Enum<T>> ConfigEnum<T> ofEnum(String name, T defaultValue) {
        return ofEnum(name, defaultValue, null);
    }

    public static <T extends Enum<T>> ConfigEnum<T> ofEnum(String name, T defaultValue, String comment) {
        return new ConfigEnum<>(name, defaultValue, comment);
    }
}
