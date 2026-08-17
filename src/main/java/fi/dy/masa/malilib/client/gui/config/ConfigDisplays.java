package fi.dy.masa.malilib.client.gui.config;

import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.client.config.options.ConfigToggle;
import fi.dy.masa.malilib.client.gui.widgets.config.*;
import fi.dy.masa.malilib.client.input.IHotkey;
import fi.dy.masa.malilib.client.util.StringUtils;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.*;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.localization.ScreenText;
import fi.dy.masa.malilib.util.ComponentUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigDisplays {
    private static final Map<ConfigType, ConfigDisplay> MAP = new HashMap<>();

    public static void register(ConfigType configType, ConfigDisplay definition) {
        MAP.put(configType, definition);
    }

    @Nullable
    public static ConfigDisplay get(ConfigType configType) {
        return MAP.get(configType);
    }

    static {
        register(ConfigTypes.BOOLEAN, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigCyclic<>((ConfigBoolean) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return ComponentUtils.ofBoolean(config);
            }
        });

        register(ConfigTypes.INTEGER, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigSlideable<>((ConfigInteger) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return String.valueOf(((IConfigInteger) config).getIntegerValue());
            }
        });

        register(ConfigTypes.DOUBLE, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigSlideable<>((ConfigDouble) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return (int) (((IConfigDouble) config).getRatio() * 100.0f) + "%";
            }
        });

        register(ConfigTypes.HOTKEY, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigHotkey((ConfigHotkey) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return ((IHotkey) config).getKeybind().getKeysDisplayString();
            }
        });

        register(ConfigTypes.STRING, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigInputBox<>((ConfigString) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return ((IConfigString) config).getStringValue();
            }
        });

        register(ConfigTypes.STRING_LIST, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigStringList((ConfigStringList) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                List<String> list = ((IConfigStringList) config).getStringListValue();
                if (list.isEmpty()) return "<" + ScreenText.LIST_EMPTY.translate() + ">";
                return list.toString();
            }
        });

        register(ConfigTypes.ENUM, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigCyclic<>((ConfigEnum<?>) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                String entry = ((IConfigEnum<?>) config).getStringValue();
                return StringUtils.getTranslatedOrFallback("config.enum." + config.getName() + "." + entry, entry);
            }
        });

        register(ConfigTypes.COLOR, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigColor((ConfigColor) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return String.valueOf(((IConfigInteger) config).getIntegerValue());
            }
        });

        register(ConfigTypes.TOGGLE, new ConfigDisplay() {
            @Override
            public WidgetConfig<?> createWidget(ConfigBase<?> config) {
                return new WidgetConfigToggle((ConfigToggle) config);
            }

            @Override
            public String getButtonText(ConfigBase<?> config) {
                return ComponentUtils.ofBoolean(config);
            }
        });
    }
}
