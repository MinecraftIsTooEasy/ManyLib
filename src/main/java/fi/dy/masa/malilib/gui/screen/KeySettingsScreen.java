package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.PeriodicButton;
import fi.dy.masa.malilib.gui.screen.interfaces.ScreenParented;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

public class KeySettingsScreen extends ScreenParented {
    IKeybind keybind;
    String name;

    public KeySettingsScreen(GuiScreen parent, String name, IKeybind keybind) {
        super(parent);
        this.name = name;
        this.keybind = keybind;
        this.container = new KeySettingsContainer(this);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        this.keybind.setSettings(((KeySettingsContainer) this.container).create());
    }

    static class KeySettingsContainer extends WidgetContainer {
        KeySettingsScreen keySettingsScreen;

        private final ConfigEnum<KeybindSettings.Context> context;
        private final ConfigEnum<KeyAction> activateOn;
        private final ConfigBoolean allowExtraKeys;
        private final ConfigBoolean orderSensitive;
        private final ConfigBoolean exclusive;
        private final ConfigBoolean cancel;
        private final ConfigBoolean allowEmpty;

        public KeySettingsContainer(KeySettingsScreen keySettingsScreen) {
            super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
            this.keySettingsScreen = keySettingsScreen;
            KeybindSettings settings = keySettingsScreen.keybind.getSettings();
            this.context = new ConfigEnum<>("manyLib.keybind.settings.context", settings.getContext());
            this.activateOn = new ConfigEnum<>("manyLib.keybind.settings.activate_on", settings.getActivateOn());
            this.allowExtraKeys = new ConfigBoolean("manyLib.keybind.settings.allow_extra_keys", settings.getAllowExtraKeys());
            this.orderSensitive = new ConfigBoolean("manyLib.keybind.settings.order_sensitive", settings.isOrderSensitive());
            this.exclusive = new ConfigBoolean("manyLib.keybind.settings.exclusive", settings.isExclusive());
            this.cancel = new ConfigBoolean("manyLib.keybind.settings.cancel", settings.shouldCancel());
            this.allowEmpty = new ConfigBoolean("manyLib.keybind.settings.allow_empty", settings.getAllowEmpty());
        }

        @Override
        public void initWidgets() {
            this.subWidgets.clear();
            this.addWidget(WidgetText.of(StringUtils.translate("manyLib.gui.title.keySettings")).position(this.width / 2, 20).centered(true));
            this.addWidget(WidgetText.of(GuiBase.TXT_AQUA + StringUtils.translate("manyLib.gui.keySettings.configuring") + ": " + this.keySettingsScreen.name).position(this.width / 2, 40).centered(true));
            this.addWidgetIndex(0, this.context);
            this.addWidgetIndex(1, this.activateOn);
            this.addWidgetIndex(2, this.allowExtraKeys);
            this.addWidgetIndex(3, this.orderSensitive);
            this.addWidgetIndex(4, this.exclusive);
            this.addWidgetIndex(5, this.cancel);
            this.addWidgetIndex(6, this.allowEmpty);
        }

        <T extends ConfigBase<T> & IConfigPeriodic> void addWidgetIndex(int index, T config) {
            this.addWidget(new WidgetText(this.keySettingsScreen.width / 2 - 150, this.keySettingsScreen.height / 6 + index * 22 + 22 + ScreenConstants.commentedTextShift, StringUtils.translate(config.getName()), config.getConfigGuiDisplayComment(), config.getDisplayColor()));
            PeriodicButton<T> periodicButton = new PeriodicButton<>(this.keySettingsScreen.width / 2, this.keySettingsScreen.height / 6 + index * 22 + 22, 120, 20, config);
            this.addWidget(periodicButton);
            this.addWidget(ButtonGeneric.builder(ManyLibIcons.ResetButton, button -> {
                        config.resetToDefault();
                        periodicButton.updateString();
                    }).dimensions(this.keySettingsScreen.width / 2 + 125, this.keySettingsScreen.height / 6 + index * 22 + 22, 20, 20)
                    .build());
        }

        KeybindSettings create() {
            return KeybindSettings.create(context.getEnumValue(), activateOn.getEnumValue(), allowExtraKeys.getBooleanValue(), orderSensitive.getBooleanValue(), exclusive.getBooleanValue(), cancel.getBooleanValue(), allowEmpty.getBooleanValue());
        }

    }
}
