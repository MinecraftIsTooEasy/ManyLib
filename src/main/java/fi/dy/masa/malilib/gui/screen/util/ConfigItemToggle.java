package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigToggle;
import fi.dy.masa.malilib.gui.GuiBase;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.I18n;

class ConfigItemToggle extends ConfigItemHotkey {
    final GuiButton toggleButton;

    public ConfigItemToggle(int index, ConfigToggle config, GuiScreen screen) {
        super(index, config, screen);
        this.toggleButton = ScreenConstants.getConfigToggleButton(index, config, screen);
        this.updateStringByToggleStatus();
        this.buttons.add(this.toggleButton);
    }

    @Override
    public void customActionPerformed(GuiButton guiButton) {
        super.customActionPerformed(guiButton);
        if (guiButton == this.toggleButton) {
            ((ConfigToggle) this.config).toggle();
            this.updateStringByToggleStatus();
        }
    }

    @Override
    public void resetButtonClicked() {
        super.resetButtonClicked();
        this.updateStringByToggleStatus();
    }

    private void updateStringByToggleStatus() {
        if (((ConfigToggle) this.config).isOn()) {
            this.toggleButton.displayString = GuiBase.TXT_GREEN + I18n.getString("boolean.true");
        } else {
            this.toggleButton.displayString = GuiBase.TXT_RED + I18n.getString("boolean.false");
        }
    }
}
