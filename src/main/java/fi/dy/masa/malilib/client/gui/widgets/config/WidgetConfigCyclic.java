package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.button.CycleButton;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.client.util.StringUtils;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.IConfigCyclic;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.localization.TooltipText;

import java.util.ArrayList;
import java.util.List;

public class WidgetConfigCyclic<T extends ConfigBase<T> & IConfigCyclic> extends WidgetConfig<T> {
    final CycleButton<T> cycleButton;
    final List<String> enumTooltip = new ArrayList<>();// ordinal 0 is title

    public WidgetConfigCyclic(T config) {
        super(config);
        if (config.getType() == ConfigTypes.ENUM) {
            this.enumTooltip.add(TooltipText.AVAILABLE_VALUES.translate() + ":");
            for (Enum<?> allEnumValue : (((ConfigEnum<?>) config).getAllEnumValues())) {
                this.enumTooltip.add(StringUtils.getTranslatedOrFallback("config.enum." + config.getName() + "." + allEnumValue.name(), allEnumValue.name()));
            }
        }
        this.cycleButton = ScreenConstants.getCycleButton(config);
        this.addWidget(this.cycleButton);
    }

    @Override
    public void init() {
        super.init();

        ScreenConstants.placeCommonButton(this, this.cycleButton);
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.cycleButton.isMouseOver() && !this.enumTooltip.isEmpty()) {
            int ordinal = ((ConfigEnum<?>) this.config).getOrdinal() + 1;
            String s = this.enumTooltip.get(ordinal);
            this.enumTooltip.set(ordinal, GuiBase.TXT_GREEN + s);
            RenderUtils.renderTooltip(mouseX, mouseY, this.enumTooltip, drawContext);
            this.enumTooltip.set(ordinal, s);
        }
    }

    @Override
    public void onResetClicked() {
        this.cycleButton.updateString();
    }
}
