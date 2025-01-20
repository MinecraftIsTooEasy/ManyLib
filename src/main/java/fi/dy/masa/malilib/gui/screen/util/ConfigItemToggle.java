package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigToggle;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

class ConfigItemToggle extends ConfigItemHotkey {
    final ButtonBase toggleButton;

    public ConfigItemToggle(int index, ConfigToggle config, GuiScreen screen) {
        super(index, config, screen);
        this.toggleButton = ScreenConstants.getConfigToggleButton(index, screen, button -> ((ConfigToggle) this.config).toggle());
        this.toggleButton.setOnUpdate(button -> {
            if (((ConfigToggle) this.config).isOn()) {
                this.toggleButton.setDisplayString(GuiBase.TXT_GREEN + StringUtils.translate("boolean.true"));
            } else {
                this.toggleButton.setDisplayString(GuiBase.TXT_RED + StringUtils.translate("boolean.false"));
            }
        });
        this.buttons.add(this.toggleButton);
    }
}
