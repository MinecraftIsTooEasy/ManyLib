package fi.dy.masa.malilib.client.gui.screen;

import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.client.gui.widgets.WidgetText;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.core.Side;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.GuiScreen;

import java.util.Map;

public class ConfigSideMenu extends LayeredScreen {
    private final String id;
    private final Map<Side, IConfigHandler> map;

    public ConfigSideMenu(GuiScreen parent, String id, Map<Side, IConfigHandler> map) {
        super();
        this.id = id;
        this.map = map;
        this.setParent(parent);
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);
        layer.addWidget(WidgetText.of(ManyLibClientConfig.TitleFormat.getEnumValue() + ConfigDisplayApi.getModName(this.id)).position(this.width / 2, 20).centered());
        int x = this.width / 2 - 100;
        int y = this.height / 6 - 6;
        for (Map.Entry<Side, IConfigHandler> entry : this.map.entrySet()) {
            IConfigHandler iConfigHandler = entry.getValue();
            ButtonGeneric button = ButtonGeneric.builder(
                            iConfigHandler.getSide().toString(),
                            button_ -> this.mc.displayGuiScreen(ManyLibClientApi.createConfigScreen(iConfigHandler, this))
                    ).dimensions(x, y, 200, 20)
                    .build();
            layer.addWidget(button);
            y += 24;
        }

        layer.addWidget(ButtonGeneric.builder(ScreenText.DONE.translate(), button -> this.mc.displayGuiScreen(this.getParent()))
                .dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20)
                .build());
    }
}
