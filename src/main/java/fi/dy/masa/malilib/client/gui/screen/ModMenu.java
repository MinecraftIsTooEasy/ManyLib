package fi.dy.masa.malilib.client.gui.screen;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.client.gui.ManyLibIcons;
import fi.dy.masa.malilib.client.gui.button.ButtonBase;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.client.gui.screen.interfaces.ScreenPaged;
import fi.dy.masa.malilib.client.gui.widgets.WidgetText;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class ModMenu extends ScreenPaged {
    private final List<String> configs;

    private ButtonGeneric pageUp;
    private ButtonGeneric pageDown;

    private final List<ButtonBase> buttons = new ArrayList<>();

    public ModMenu(GuiScreen parent) {
        super(parent, 6, 2);
        this.configs = ManyLibApi.streamModIds().toList();
        this.updatePageCount(this.configs.size());
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);
        this.buttons.clear();

        // first some widgets must be them, for set visibilities
        for (int i = 0; i < this.configs.size(); i++) {
            String id = this.configs.get(i);
            int x = this.getButtonPosX(i);
            int y = this.getButtonPosY(i);
            String name = ConfigDisplayApi.getModName(id);
            String tooltip = ConfigDisplayApi.getModComment(id);
            ButtonBase button = ButtonGeneric.builder(name, button_ -> this.mc.displayGuiScreen(ManyLibClientApi.createConfigScreen(id, this)))
                    .position(x, y)
                    .hoverStrings(tooltip)
                    .build();
            layer.addWidget(button);
            this.buttons.add(button);
        }
        layer.addWidget(WidgetText.of(ManyLibClientConfig.TitleFormat.getEnumValue() + ScreenText.TITLE_OPTIONS.translate()).position(this.width / 2, 20).centered());

        layer.addWidget(ButtonGeneric.builder(ScreenText.DONE.translate(), button -> this.mc.displayGuiScreen(this.getParent()))
                .dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20)
                .build());

        ButtonGeneric pageUp = ButtonGeneric.builder(ManyLibIcons.PageUpButton, button -> this.scroll(false))
                .dimensions(this.width / 2 + 132, this.height / 6 + 168, 20, 20)
                .hoverStrings(ScreenText.PAGE_UP.translate())
                .build();
        this.pageUp = pageUp;
        layer.addWidget(pageUp);

        ButtonGeneric pageDown = ButtonGeneric.builder(ManyLibIcons.PageDownButton, button -> this.scroll(true))
                .dimensions(this.width / 2 + 154, this.height / 6 + 168, 20, 20)
                .hoverStrings(ScreenText.PAGE_DOWN.translate())
                .build();
        this.pageDown = pageDown;
        layer.addWidget(pageDown);

        this.setVisibilities();
    }

    @Override
    public void setVisibilities() {
        for (int i = 0; i < this.configs.size(); ++i) {
            this.buttons.get(i).setVisible(this.isVisible(i));
        }
        this.pageUp.setEnabled(this.canPageUp());
        this.pageDown.setEnabled(this.canPageDown());
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
