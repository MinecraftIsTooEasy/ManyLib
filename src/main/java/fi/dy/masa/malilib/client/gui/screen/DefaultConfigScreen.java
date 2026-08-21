package fi.dy.masa.malilib.client.gui.screen;

import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.client.event.InputEventHandler;
import fi.dy.masa.malilib.client.feature.ProgressSaving;
import fi.dy.masa.malilib.client.feature.SortCategory;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.button.CycleButton;
import fi.dy.masa.malilib.client.gui.button.ModLinkButton;
import fi.dy.masa.malilib.client.gui.button.interfaces.ICycleButton;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.client.gui.layer.ModLinkLayer;
import fi.dy.masa.malilib.client.gui.screen.interfaces.ElementList;
import fi.dy.masa.malilib.client.gui.screen.interfaces.IConfigScreen;
import fi.dy.masa.malilib.client.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.tab.ConfigTab;
import fi.dy.masa.malilib.client.gui.widgets.WidgetConfigListView;
import fi.dy.masa.malilib.client.gui.widgets.WidgetSearchField;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.GuiScreen;
import net.minecraft.GuiYesNoMITE;
import org.apache.commons.lang3.mutable.MutableInt;
import org.lwjgl.input.Keyboard;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class DefaultConfigScreen extends LayeredScreen implements IConfigScreen, ElementList<ConfigBase<?>>, Searchable {
    public final IConfigHandler configHandler;

    public ConfigTab currentTab;
    public boolean tabDirty;
    private boolean firstSeen = true;
    private final List<ConfigTab> configTabs;

    private ButtonGeneric resetAllButton;
    private CycleButton<?> sortButton;
    private ModLinkButton modLinkButton;
    private WidgetConfigListView widgetListView;
    private WidgetSearchField searchField;

    public DefaultConfigScreen(GuiScreen parent, IConfigHandler configHandler) {
        super();
        if (parent instanceof DefaultConfigScreen defaultConfigScreen) {
            parent = defaultConfigScreen.getParent();
        }// for redirecting to other configs
        this.setParent(parent);
        this.configHandler = configHandler;
        this.configTabs = this.createConfigTabs();
        this.currentTab = this.configTabs.get(0);
    }

    @Override
    public IConfigHandler getConfigHandler() {
        return this.configHandler;
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);
        this.initElements(layer);
        if (this.firstSeen) {
            this.firstSeen = false;

            int page = ProgressSaving.getPage(this.configHandler);
            if (page < this.configTabs.size()) {
                this.setCurrentTab(this.configTabs.get(page));
            }
            this.tabDirty = false;

            this.widgetListView.onContentChange();
            this.widgetListView.setStatus(ProgressSaving.getStatus(this.configHandler));
        }
    }

    protected void initElements(Layer layer) {
        String id = this.configHandler.getModId();
        String name = ConfigDisplayApi.getModName(id);
        layer.addWidget(
                ScreenConstants.getTitle(
                        ManyLibClientConfig.TitleFormat.getEnumValue()
                                + name
                                + " "
                                + this.configHandler.getSide().toString()
                                + " configs"
                )
        );

        MutableInt widthAdder = new MutableInt(20);

        this.addTabButtons(layer, widthAdder);

        ButtonGeneric resetAllButton = ScreenConstants.getResetAllButton(widthAdder, () -> this.currentTab.getAllConfigs().stream().anyMatch(IConfigResettable::isModified), button -> {
            String question = ScreenText.RESET_TAB_QUESTION.translate(), yes = ScreenText.YES.translate(), no = ScreenText.NO.translate();
            GuiYesNoMITE var3 = new GuiYesNoMITE
                    (this, question, name + ": " + this.currentTab.getGuiDisplayName(), yes, no, ScreenConstants.confirmFlag);
            this.mc.displayGuiScreen(var3);
        });
        this.resetAllButton = resetAllButton;
        layer.addWidget(resetAllButton);

        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        CycleButton<?> sortButton = ScreenConstants.getSortButton(this, widthAdder, 30, sortCategoryConfigEnum, button -> {
            ((ICycleButton) button).next();
            this.sort(sortCategoryConfigEnum.getEnumValue());
        });
        this.sortButton = sortButton;
        layer.addWidget(sortButton);

        ModLinkButton modLinkButton = ScreenConstants.getModLinkButton(this, this.configHandler);
        modLinkButton.setActionListener(button -> this.toggleModLink());
        this.modLinkButton = modLinkButton;
        layer.addWidget(modLinkButton);

        WidgetConfigListView widgetListView = new WidgetConfigListView(this);
        ScreenConstants.setWidgetListViewDimensions(this, widgetListView);
        this.widgetListView = widgetListView;
        layer.addWidget(widgetListView);

        WidgetSearchField searchField = ScreenConstants.getSearchField(this, widgetListView);
        this.searchField = searchField;
        layer.addWidget(searchField);
    }

    private void toggleModLink() {
        this.toggleLayer(
                layer -> layer instanceof ModLinkLayer,
                () -> new ModLinkLayer(
                        this,
                        x -> this.configHandler.getModId().equals(x),
                        () -> this.modLinkButton,
                        x -> ManyLibClientApi.createConfigScreen(x, this)
                )
        );
    }

    void addTabButtons(Layer layer, MutableInt widthAdder) {
        for (ConfigTab configTab : this.configTabs) {
            String name = configTab.getGuiDisplayName();
            int stringWidth = this.fontRenderer.getStringWidth(name);
            layer.addWidget(ButtonGeneric.builder(name, button -> this.setCurrentTab(configTab))
                    .onUpdate(button -> button.setEnabled(this.currentTab != configTab))
                    .dimensions(widthAdder.getValue(), 30, stringWidth + 10, 20)
                    .hoverStrings(configTab.getTooltip()).build());
            widthAdder.add(stringWidth + 14);
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
            if (time - this.lastShift < 100L) {
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
        this.configHandler.save();
        InputEventHandler.getKeybindManager().updateUsedKeys();
        ProgressSaving.saveProgress(this.configHandler, this.findTabIndex(), this.widgetListView.getStatus());
        this.firstSeen = true;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
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
