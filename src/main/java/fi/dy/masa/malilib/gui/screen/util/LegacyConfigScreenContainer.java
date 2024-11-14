package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.PullDownButton;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.screen.LegacyConfigScreen;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.util.Color4f;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiYesNoMITE;

import java.util.List;

public class LegacyConfigScreenContainer extends WidgetContainer {

    LegacyConfigScreen screen;

    public final List<ConfigTab> configTabs;

    public LegacyConfigScreenContainer(LegacyConfigScreen screen) {
        super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.screen = screen;
        this.configTabs = screen.configInstance.getConfigTabs();
    }

    @Override
    public void initWidgets() {
        this.subWidgets.clear();
        WidthAdder widthAdder = new WidthAdder(20);
        // first some widgets must be tab buttons, for set current tab method
        for (int index = 0; index < this.configTabs.size(); index++) {
            ConfigTab configTab = this.configTabs.get(index);
            String name = configTab.getGuiDisplayName();
            int stringWidth = this.fontRenderer.getStringWidth(name);
            int finalIndex = index;
            this.addWidget(ButtonGeneric.builder(name, button -> {
                        this.screen.needInit = true;
                        this.setCurrentTab(finalIndex);
                    })
                    .dimensions(widthAdder.getWidth(), 30, stringWidth + 10, 20)
                    .hoverStrings(configTab.getTooltip())
                    .build());
            widthAdder.addWidth(stringWidth + 14);
        }

        String configInstanceName = this.screen.configInstance.getName();

        this.addWidget(new WidgetText(40, 15, ManyLibConfig.TitleFormat.getEnumValue() + configInstanceName + " Configs", null, Color4f.fromColor(16777215)) {
            @Override
            public void tickScreen() {
                super.tickScreen();
                this.content(ManyLibConfig.TitleFormat.getEnumValue() + configInstanceName + " Configs");
            }
        });

        this.addWidget(ScreenConstants.getResetAllButton(widthAdder, () -> this.screen.currentTab.getAllConfigs().stream().anyMatch(IConfigResettable::isModified), button -> {
            String question = StringUtils.translate("manyLib.gui.reset_tab_question"), yes = StringUtils.translate("gui.yes"), no = StringUtils.translate("gui.no");
            GuiYesNoMITE var3 = new GuiYesNoMITE
                    (this.screen, question, configInstanceName, yes, no, ScreenConstants.confirmFlag);
            this.mc.displayGuiScreen(var3);
        }));
        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        this.addWidget(ScreenConstants.getSortButton(this.screen, widthAdder, 30, sortCategoryConfigEnum, button -> {
            ((IButtonPeriodic) button).next();
            this.screen.sort(sortCategoryConfigEnum.getEnumValue());
        }));
        this.addWidget(ScreenConstants.getSearchButton(this.screen));
        this.screen.scrollBar = ScreenConstants.getScrollBar(this.screen, this.screen.getMaxCapacity(), 0);// dummy
        this.addWidget(this.screen.scrollBar);
        PullDownButton<?> pullDownButton = ScreenConstants.getPullDownButton(this.screen, this.screen.configInstance);
        this.addWidget(pullDownButton);
        pullDownButton.initDropDownEntries(this.screen.configInstance, this.screen.getParentScreen());
        pullDownButton.addToList(this::addWidget);
    }

    public void setCurrentTab(int index) {
        ((ButtonGeneric) this.subWidgets.get(this.screen.currentTabIndex)).setEnabled(true);
        this.screen.currentTabIndex = index;
        this.screen.currentTab = this.configTabs.get(index);
        ((ButtonGeneric) this.subWidgets.get(index)).setEnabled(false);
    }


}
