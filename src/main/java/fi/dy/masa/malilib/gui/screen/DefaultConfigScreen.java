package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.feat.ProgressSaving;
import fi.dy.masa.malilib.feat.SortCategory;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.ModLinkButton;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.layer.Layer;
import fi.dy.masa.malilib.gui.layer.ModLinkLayer;
import fi.dy.masa.malilib.gui.screen.interfaces.IConfigList;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.screen.util.WidthAdder;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigListView;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import net.minecraft.GuiYesNoMITE;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class DefaultConfigScreen extends LayeredScreen implements Searchable, IConfigList {
    public final IConfigHandler configInstance;
    public ConfigTab currentTab;
    public boolean tabDirty;
    private boolean firstSeen = true;
    private final List<ConfigTab> configTabs;
    private ModLinkButton modLinkButton;
    private WidgetConfigListView widgetListView;

    public DefaultConfigScreen(GuiScreen parent, IConfigHandler configInstance) {
        super();
        if (parent instanceof DefaultConfigScreen defaultConfigScreen) {
            parent = defaultConfigScreen.getParent();
        }// for redirecting to other configs
        this.setParent(parent);
        this.configInstance = configInstance;
        this.configTabs = configInstance.getConfigTabs();
        this.currentTab = this.configTabs.get(0);
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);
        this.initElements(layer);
        if (this.firstSeen) {
            this.firstSeen = false;

            this.setCurrentTab(this.configTabs.get(ProgressSaving.getPage(this.configInstance.getName())));
            this.tabDirty = false;

            this.widgetListView.onContentChange();
            this.widgetListView.setStatus(ProgressSaving.getStatus(this.configInstance.getName()));
        }
        Keyboard.enableRepeatEvents(true);
    }

    protected void initElements(Layer layer) {
        layer.addWidget(ScreenConstants.getTitle(ManyLibConfig.TitleFormat.getEnumValue() + this.configInstance.getName() + " Configs"));

        WidthAdder widthAdder = new WidthAdder(20);

        this.addTabButtons(layer, widthAdder);

        String configInstanceName = this.configInstance.getName();
        layer.addWidget(ScreenConstants.getResetAllButton(widthAdder, () -> this.currentTab.getAllConfigs().stream().anyMatch(IConfigResettable::isModified), button -> {
            String question = StringUtils.translate("manyLib.gui.reset_tab_question"), yes = StringUtils.translate("gui.yes"), no = StringUtils.translate("gui.no");
            GuiYesNoMITE var3 = new GuiYesNoMITE
                    (this, question, configInstanceName + ": " + this.currentTab.getGuiDisplayName(), yes, no, ScreenConstants.confirmFlag);
            this.mc.displayGuiScreen(var3);
        }));
        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        layer.addWidget(ScreenConstants.getSortButton(this, widthAdder, 30, sortCategoryConfigEnum, button -> {
            ((IButtonPeriodic) button).next();
            this.sort(sortCategoryConfigEnum.getEnumValue());
        }));

        ModLinkButton modLinkButton = ScreenConstants.getModLinkButton(this, this.configInstance);
        modLinkButton.setActionListener(button -> this.toggleModLink());
        this.modLinkButton = modLinkButton;
        layer.addWidget(modLinkButton);

        WidgetConfigListView widgetListView = new WidgetConfigListView(this, this);
        this.widgetListView = widgetListView;
        layer.addWidget(widgetListView);

        layer.addWidget(ScreenConstants.getSearchButton(this, this.widgetListView));
    }

    private void toggleModLink() {
        this.toggleLayer(
                layer -> layer instanceof ModLinkLayer,
                () -> new ModLinkLayer(x -> x == this.configInstance,
                        () -> this.modLinkButton,
                        x -> x.getConfigScreen(this)
                )
        );
    }

    void addTabButtons(Layer layer, WidthAdder widthAdder) {
        for (ConfigTab configTab : this.configTabs) {
            String name = configTab.getGuiDisplayName();
            int stringWidth = this.fontRenderer.getStringWidth(name);
            layer.addWidget(ButtonGeneric.builder(name, button -> this.setCurrentTab(configTab))
                    .onUpdate(button -> button.setEnabled(this.currentTab != configTab))
                    .dimensions(widthAdder.getWidth(), 30, stringWidth + 10, 20)
                    .hoverStrings(configTab.getTooltip()).build());
            widthAdder.addWidth(stringWidth + 14);
        }
    }

    void setCurrentTab(ConfigTab tab) {
        this.tabDirty = true;
        this.currentTab = tab;
    }

    private int findTabIndex() {
        return this.configTabs.indexOf(this.currentTab);
    }

    /**
     * i.e. the widgets marked the tab dirty.
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) {
            this.handleTabDirty();
            return true;
        }
        return false;
    }

    private void handleTabDirty() {
        if (this.tabDirty) {
            this.tabDirty = false;
            this.reload();
        }
    }

    private long lastShift = 0L;

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (super.charTyped(chr, keyCode)) {
            return true;
        }
        if (keyCode == Keyboard.KEY_LSHIFT) {
            long time = System.currentTimeMillis();
            if (time - this.lastShift < 200L) {
                this.mc.displayGuiScreen(new GlobalSearchScreen(this));
                return true;
            } else {
                this.lastShift = time;
            }
        }
        return false;
    }

    @Override
    public void confirmClicked(boolean result, int flag) {
        if (result && flag == ScreenConstants.confirmFlag)
            this.currentTab.getAllConfigs().forEach(IConfigResettable::resetToDefault);
        this.mc.displayGuiScreen(this);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.configInstance.save();
        InputEventHandler.getKeybindManager().updateUsedKeys();
        ProgressSaving.saveProgress(this.configInstance.getName(), this.findTabIndex(), this.widgetListView.getStatus());
        this.firstSeen = true;
    }

    void sort(SortCategory sortCategory) {
        this.currentTab.sort(sortCategory);
        this.widgetListView.resetStatus();
        this.widgetListView.markDirty();
    }

    @Override
    public void updateSearchResult(String input) {
        this.currentTab.updateSearchableConfigs(input);
        this.widgetListView.onContentChange();
    }

    @Override
    public int size() {
        return this.currentTab.getSearchableConfigSize();
    }

    @Override
    public ConfigBase<?> get(int index) {
        return this.currentTab.getSearchableConfig(index);
    }
}
