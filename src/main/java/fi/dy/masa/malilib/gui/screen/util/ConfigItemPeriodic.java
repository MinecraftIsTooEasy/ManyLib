package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.I18n;

import java.util.ArrayList;
import java.util.List;

class ConfigItemPeriodic<T extends ConfigBase<T> & IConfigPeriodic & IConfigDisplay> extends ConfigItem<T> {
    final IButtonPeriodic periodicButton;
    List<String> strings = new ArrayList<>();// ordinal 0 is title
    boolean drawComment = false;

    public ConfigItemPeriodic(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        if (config.getType() == ConfigType.ENUM) {
            this.drawComment = true;
            this.strings.add(I18n.getString("manyLib.gui.comment.available_values") + ":");
            for (Enum<?> allEnumValue : (((ConfigEnum<?>) config).getAllEnumValues())) {
                this.strings.add(StringUtils.getTranslatedOrFallback("config.enum." + config.getName() + "." + allEnumValue.name(), allEnumValue.name()));
            }
        }
        this.periodicButton = ScreenConstants.getPeriodicButton(index, config, screen);
        this.buttons.add((GuiButton) this.periodicButton);
    }

    @Override
    public void tryDrawComment(GuiScreen guiScreen, int x, int y) {
        super.tryDrawComment(guiScreen, x, y);
        if (this.drawComment && ((GuiButton) this.periodicButton).func_82252_a()) {// hovering
            int ordinal = ((ConfigEnum<?>) this.config).getOrdinal() + 1;
            String s = this.strings.get(ordinal);
            this.strings.set(ordinal, GuiBase.TXT_GREEN + s);
            RenderUtils.drawTextList(guiScreen, this.strings, x, y);
            this.strings.set(ordinal, s);
        }
    }

    @Override
    public void customDraw(GuiScreen guiScreen, int x, int y) {
    }

    @Override
    public void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click) {
    }

    @Override
    public void customActionPerformed(GuiButton guiButton) {
        if (guiButton == this.periodicButton) {
            this.periodicButton.next();
        }
    }

    @Override
    public void customSetVisible(boolean visible) {
    }

    @Override
    public void resetButtonClicked() {
        this.periodicButton.updateString();
    }
}
