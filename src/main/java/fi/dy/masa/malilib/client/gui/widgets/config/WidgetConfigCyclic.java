package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.button.CycleButton;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.IConfigCyclic;
import fi.dy.masa.malilib.config.interfaces.IConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigBase;

import java.util.List;

public class WidgetConfigCyclic<T extends ConfigBase<T> & IConfigCyclic> extends WidgetConfig<T> {
    final CycleButton<T> cycleButton;

    public WidgetConfigCyclic(T config) {
        super(config);
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
        if (this.cycleButton.isMouseOver() && this.config.getType() == ConfigTypes.ENUM) {
            List<String> tooltip = ConfigDisplayApi.getEnumTooltip((IConfigEnum<?>) this.config);
            RenderUtils.renderTooltip(mouseX, mouseY, tooltip, drawContext);
        }
    }

    @Override
    public void onResetClicked() {
        this.cycleButton.updateString();
    }
}
