package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.PeriodicButton;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

class ConfigItemPeriodic<T extends ConfigBase<T> & IConfigPeriodic & IConfigDisplay> extends ConfigItem<T> {
    final PeriodicButton<T> periodicButton;
    List<String> strings = new ArrayList<>();// ordinal 0 is title
    boolean drawComment = false;

    public ConfigItemPeriodic(int index, T config, GuiScreen screen) {
        super(index, config, screen);
        if (config.getType() == ConfigType.ENUM) {
            this.drawComment = true;
            this.strings.add(StringUtils.translate("manyLib.gui.comment.available_values") + ":");
            for (Enum<?> allEnumValue : (((ConfigEnum<?>) config).getAllEnumValues())) {
                this.strings.add(StringUtils.getTranslatedOrFallback("config.enum." + config.getName() + "." + allEnumValue.name(), allEnumValue.name()));
            }
        }
        this.periodicButton = ScreenConstants.getPeriodicButton(index, config, screen);
        this.buttons.add(this.periodicButton);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.periodicButton.isMouseOver())
            if (this.drawComment) {
                int ordinal = ((ConfigEnum<?>) this.config).getOrdinal() + 1;
                String s = this.strings.get(ordinal);
                this.strings.set(ordinal, GuiBase.TXT_GREEN + s);
                RenderUtils.drawTextList(this.strings, mouseX, mouseY, drawContext);
                this.strings.set(ordinal, s);
            }
    }

    @Override
    public void resetButtonClicked() {
        this.periodicButton.updateString();
    }
}
