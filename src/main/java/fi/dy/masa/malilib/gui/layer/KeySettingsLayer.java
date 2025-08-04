package fi.dy.masa.malilib.gui.layer;

import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.PeriodicButton;
import fi.dy.masa.malilib.gui.button.ResetButton;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

public class KeySettingsLayer extends Layer {
    private final static int X_OFFSET = 100;
    private final static int Y_OFFSET = 80;
    private final GuiScreen screen;
    IKeybind keybind;

    private final ConfigEnum<KeybindSettings.Context> context;
    private final ConfigEnum<KeyAction> activateOn;
    private final ConfigBoolean allowExtraKeys;
    private final ConfigBoolean orderSensitive;
    private final ConfigBoolean exclusive;
    private final ConfigBoolean cancel;
    private final ConfigBoolean allowEmpty;

    public KeySettingsLayer(GuiScreen screen, IKeybind keybind) {
        this.screen = screen;
        this.keybind = keybind;

        KeybindSettings settings = keybind.getDefaultSettings();
        this.context = new ConfigEnum<>("manyLib.keybind.settings.context", settings.getContext());
        this.activateOn = new ConfigEnum<>("manyLib.keybind.settings.activate_on", settings.getActivateOn());
        this.allowExtraKeys = new ConfigBoolean("manyLib.keybind.settings.allow_extra_keys", settings.getAllowExtraKeys());
        this.orderSensitive = new ConfigBoolean("manyLib.keybind.settings.order_sensitive", settings.isOrderSensitive());
        this.exclusive = new ConfigBoolean("manyLib.keybind.settings.exclusive", settings.isExclusive());
        this.cancel = new ConfigBoolean("manyLib.keybind.settings.cancel", settings.shouldCancel());
        this.allowEmpty = new ConfigBoolean("manyLib.keybind.settings.allow_empty", settings.getAllowEmpty());
        settings = keybind.getSettings();

        this.context.setEnumValue(settings.getContext());
        this.activateOn.setEnumValue(settings.getActivateOn());
        this.allowExtraKeys.setBooleanValue(settings.getAllowExtraKeys());
        this.orderSensitive.setBooleanValue(settings.isOrderSensitive());
        this.exclusive.setBooleanValue(settings.isExclusive());
        this.cancel.setBooleanValue(settings.shouldCancel());
        this.allowEmpty.setBooleanValue(settings.getAllowEmpty());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawOverlay();
        this.drawOutlinedBox(this.screen, X_OFFSET, Y_OFFSET);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.addWidgetIndex(0, this.context);
        this.addWidgetIndex(1, this.activateOn);
        this.addWidgetIndex(2, this.allowExtraKeys);
        this.addWidgetIndex(3, this.orderSensitive);
        this.addWidgetIndex(4, this.exclusive);
        this.addWidgetIndex(5, this.cancel);
        this.addWidgetIndex(6, this.allowEmpty);
    }

    @Override
    public void removed() {
        super.removed();
        this.keybind.setSettings(this.create());
    }

    private KeybindSettings create() {
        return KeybindSettings.create(context.getEnumValue(), activateOn.getEnumValue(), allowExtraKeys.getBooleanValue(), orderSensitive.getBooleanValue(), exclusive.getBooleanValue(), cancel.getBooleanValue(), allowEmpty.getBooleanValue());
    }

    <T extends ConfigBase<T> & IConfigPeriodic> void addWidgetIndex(int index, T config) {
        int topY = this.screen.height / 2 - Y_OFFSET;
        int leftX = this.screen.width / 2 - X_OFFSET;

        int y = topY + index * 22 + 4;

        this.addWidget(new WidgetText(leftX + 10,
                y + ScreenConstants.commentedTextShift,
                StringUtils.translate(config.getName()),
                config.getConfigGuiDisplayComment(),
                config.getDisplayColor()));

        PeriodicButton periodicButton = new PeriodicButton(leftX + X_OFFSET,
                y,
                60,
                20,
                config);
        this.addWidget(periodicButton);

        this.addWidget(new ResetButton(leftX + X_OFFSET + 70,
                y,
                config::isModified,
                button -> {
                    config.resetToDefault();
                    periodicButton.updateString();
                }));
    }

    @Override
    public boolean autoExit() {
        return true;
    }

    @Override
    public boolean blocksInteraction() {
        return true;
    }
}
