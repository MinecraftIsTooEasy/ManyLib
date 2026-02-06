package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.layer.Layer;
import fi.dy.masa.malilib.gui.screen.interfaces.IMenu;
import fi.dy.masa.malilib.gui.screen.interfaces.ScreenPaged;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public class FakeModMenu extends ScreenPaged implements IMenu {
    private final List<IConfigHandler> configs;

    private ButtonGeneric pageUp;
    private ButtonGeneric pageDown;

    private final List<ButtonBase> buttons = new ArrayList<>();

    public FakeModMenu(GuiScreen parent) {
        super(parent, 6, 2);
        this.configs = ConfigManager.getInstance().getConfigMap().values().stream().toList();
        this.updatePageCount(this.configs.size());
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);
        this.buttons.clear();

        // first some widgets must be them, for set visibilities
        for (int i = 0; i < this.configs.size(); i++) {
            IConfigHandler configHandler = this.configs.get(i);
            int finalI = i;
            String name = configHandler.getName();
            ButtonBase button = this.getButton(this.getButtonPosX(i),
                    this.getButtonPosY(i),
                    StringUtils.getTranslatedOrFallback("config.menu.name." + name, name),
                    configHandler.getMenuComment(),
                    x -> {
                        IConfigHandler simpleConfigs = this.configs.get(finalI);
                        this.mc.displayGuiScreen(simpleConfigs.getConfigScreen(this));
                    });
            layer.addWidget(button);
            this.buttons.add(button);
        }
        layer.addWidget(WidgetText.of(ManyLibConfig.TitleFormat.getEnumValue() + StringUtils.translate("manyLib.gui.title.options")).position(this.width / 2, 20).centered());

        layer.addWidget(ButtonGeneric.builder(StringUtils.translate("gui.done"), button -> this.mc.displayGuiScreen(this.getParent()))
                .dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20)
                .build());

        ButtonGeneric pageUp = ButtonGeneric.builder(ManyLibIcons.PageUpButton, button -> this.scroll(false))
                .dimensions(this.width / 2 + 132, this.height / 6 + 168, 20, 20)
                .hoverStrings(StringUtils.translate("manyLib.gui.button.pageUp"))
                .build();
        this.pageUp = pageUp;
        layer.addWidget(pageUp);

        ButtonGeneric pageDown = ButtonGeneric.builder(ManyLibIcons.PageDownButton, button -> this.scroll(true))
                .dimensions(this.width / 2 + 154, this.height / 6 + 168, 20, 20)
                .hoverStrings(StringUtils.translate("manyLib.gui.button.pageDown"))
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
