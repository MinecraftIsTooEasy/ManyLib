package fi.dy.masa.malilib.client.gui.config;

import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.widgets.config.WidgetConfig;
import fi.dy.masa.malilib.client.util.StringUtils;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.config.interfaces.IConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.core.Color4f;
import fi.dy.masa.malilib.localization.TooltipText;
import fi.dy.masa.malilib.util.Platform;
import org.jetbrains.annotations.ApiStatus;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@ApiStatus.Internal
public class ConfigDisplayApi {
    public static boolean isSupported(ConfigType configType) {
        return ConfigDisplays.get(configType) != null;
    }

    public static WidgetConfig<?> createWidget(IConfigBase config) {
        ConfigDisplay definition = ConfigDisplays.get(config.getType());
        if (definition == null) throw new AssertionError();
        return definition.createWidget(config);
    }

    public static String getButtonText(IConfigBase config) {
        ConfigDisplay definition = ConfigDisplays.get(config.getType());
        if (definition == null) return "";
        return definition.getButtonText(config);
    }

    public static String getModName(String id) {
        String translationKey = "config.menu.name." + id;
        String result = StringUtils.translate(translationKey);
        if (result.equals(translationKey)) result = Platform.getModName(id);
        if (result == null) return id;
        return result;
    }

    public static String getModComment(String id) {
        return StringUtils.getTranslatedOrFallback("config.menu.comment." + id, id);
    }

    public static String getConfigDisplayName(IConfigBase config) {
        return StringUtils.getTranslatedOrFallback("config.name." + config.getName(), config.getName());
    }

    @Nullable
    public static String getConfigDisplayComment(IConfigBase config) {
        return StringUtils.getTranslatedOrFallback("config.comment." + config.getName(), config.getComment());
    }

    public static Color4f getConfigDisplayColor(IConfigBase config) {
        return Color4f.fromColor(GuiBase.COLOR_WHITE);
    }

    public static String getEnumDisplayName(IConfigEnum<?> config) {
        return getEnumDisplayName(config, (Enum<?>) config.getEnumValue());
    }

    public static String getEnumDisplayName(IConfigEnum<?> config, Enum<?> value) {
        return StringUtils.getTranslatedOrFallback("config.enum." + config.getName() + "." + value.name(), value.name());
    }

    // ordinal 0 is title
    public static List<String> getEnumTooltip(IConfigEnum<?> config) {
        Enum<?> currentValue = (Enum<?>) config.getEnumValue();
        Enum<?> defaultValue = (Enum<?>) config.getDefaultEnumValue();

        List<String> tooltip = new ArrayList<>();

        tooltip.add(TooltipText.AVAILABLE_VALUES.translate() + ":");
        for (Enum<?> enumValue : (((ConfigEnum<?>) config).getAllEnumValues())) {
            String enumName = ConfigDisplayApi.getEnumDisplayName(config, enumValue);
            if (enumValue == defaultValue) {
                tooltip.add(GuiBase.TXT_AQUA + enumName + "<--" + TooltipText.DEFAULT_VALUE.translate());
            } else if (enumValue == currentValue) {
                tooltip.add(GuiBase.TXT_GREEN + enumName + "<--" + TooltipText.CURRENT_VALUE.translate());
            } else {
                tooltip.add(enumName);
            }
        }

        return tooltip;
    }
}
