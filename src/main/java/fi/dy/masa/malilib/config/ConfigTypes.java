package fi.dy.masa.malilib.config;

import fi.dy.masa.malilib.ManyLib;
import net.minecraft.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ConfigTypes {
    private static final Map<ResourceLocation, ConfigType> REGISTRY = new HashMap<>();

    public static final ConfigType BOOLEAN = register(ManyLib.id("boolean"));
    public static final ConfigType INTEGER = register(ManyLib.id("integer"));
    public static final ConfigType DOUBLE = register(ManyLib.id("double"));
    public static final ConfigType HOTKEY = register(ManyLib.id("hotkey"));
    public static final ConfigType STRING = register(ManyLib.id("string"));
    public static final ConfigType STRING_LIST = register(ManyLib.id("string_list"));
    public static final ConfigType ENUM = register(ManyLib.id("enum"));
    public static final ConfigType COLOR = register(ManyLib.id("color"));
    public static final ConfigType TOGGLE = register(ManyLib.id("toggle"));

    public static ConfigType register(ResourceLocation id) {
        ConfigType configType = new ConfigType();
        REGISTRY.put(id, configType);
        return configType;
    }

    @Nullable
    public static ConfigType get(ResourceLocation id) {
        return REGISTRY.get(id);
    }
}
