package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.screen.interfaces.IMenu;
import fi.dy.masa.malilib.gui.screen.interfaces.ScreenPaged;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

import java.util.List;

public class FakeModMenu extends ScreenPaged {
    private final List<IConfigHandler> configs;

    public FakeModMenu(GuiScreen parentScreen) {
        super(parentScreen, 6, 2);
        this.configs = ConfigManager.getInstance().getConfigMap().values().stream().sorted((x, y) -> x.getName().compareToIgnoreCase(y.getName())).toList();
        this.updatePageCount(this.configs.size());
    }

    @Override
    protected void initContainer() {
        this.container = new FakeModMenuContainer(this);
    }

    @Override
    public void setVisibilities() {
        ((FakeModMenuContainer) this.container).setVisibilities();
    }

    static class FakeModMenuContainer extends WidgetContainer implements IMenu {

        final FakeModMenu menu;

        public FakeModMenuContainer(FakeModMenu menu) {
            super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
            this.menu = menu;
        }

        @Override
        public void initWidgets() {
            this.subWidgets.clear();
            // first some widgets must be them, for set visibilities
            for (int i = 0; i < this.menu.configs.size(); i++) {
                IConfigHandler configHandler = this.menu.configs.get(i);
                int finalI = i;
                String name = configHandler.getName();
                this.addWidget(this.getButton(this.menu.getButtonPosX(i), this.menu.getButtonPosY(i), StringUtils.getTranslatedOrFallback("config.menu.name." + name, name), configHandler.getMenuComment(), button -> {
                    IConfigHandler simpleConfigs = this.menu.configs.get(finalI);
                    this.mc.displayGuiScreen(simpleConfigs.getConfigScreen(this.menu));
                }));
            }
            this.addWidget(WidgetText.of(ManyLibConfig.TitleFormat.getEnumValue() + StringUtils.translate("manyLib.gui.title.options")).position(this.width / 2, 20).centered());
            this.setVisibilities();
            this.addWidget(ButtonGeneric.builder(StringUtils.translate("gui.done"), button -> this.menu.leaveThisScreen())
                    .dimensions(this.menu.width / 2 - 100, this.menu.height / 6 + 168, 200, 20)
                    .build());
            if (this.menu.pageCount > 1) {
                this.addWidget(ButtonGeneric.builder(ManyLibIcons.PageUpButton, button -> this.menu.scroll(false))
                        .dimensions(this.menu.width / 2 + 132, this.menu.height / 6 + 168, 20, 20)
                        .hoverStrings(StringUtils.translate("manyLib.gui.button.pageUp"))
                        .build());
                this.addWidget(ButtonGeneric.builder(ManyLibIcons.PageDownButton, button -> this.menu.scroll(true))
                        .dimensions(this.menu.width / 2 + 154, this.menu.height / 6 + 168, 20, 20)
                        .hoverStrings(StringUtils.translate("manyLib.gui.button.pageDown"))
                        .build());
            }
        }

        public void setVisibilities() {
            for (int i = 0; i < this.menu.configs.size(); ++i) {
                ((ButtonGeneric) (this.subWidgets.get(i))).setVisible(this.menu.isVisible(i));
            }
        }

    }
}
