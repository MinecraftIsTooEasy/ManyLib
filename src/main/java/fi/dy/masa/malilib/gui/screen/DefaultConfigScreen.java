package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.feat.ProgressSaving;
import fi.dy.masa.malilib.feat.SortCategory;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.PullDownButton;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.screen.util.ConfigItem;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.screen.util.WidthAdder;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import net.minecraft.GuiYesNoMITE;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class DefaultConfigScreen extends ListScreen<ConfigItem<?>> implements Searchable {
    public final IConfigHandler configInstance;
    public ConfigTab currentTab;
    public int currentTabIndex;
    public boolean needSyncTab;
    private boolean firstSeen = true;
    private final List<ConfigTab> configTabs;

    public DefaultConfigScreen(GuiScreen parentScreen, IConfigHandler configInstance) {
        super();
        this.setParent(parentScreen);
        this.useTitleHierarchy = false;
        this.configInstance = configInstance;
        this.configTabs = configInstance.getConfigTabs();
        this.setTitle(this.createTitle());
    }

    @Override
    public void initGui() {
        super.initGui();
        this.initElements();
        this.setCurrentTab(this.firstSeen ? ProgressSaving.getPage(this.configInstance.getName()) : this.currentTabIndex);
        this.needSyncTab = false;// first time special
        this.onContentChange();
        if (this.firstSeen) {
            this.firstSeen = false;
            this.setStatus(ProgressSaving.getStatus(this.configInstance.getName()));
        }
        this.onStatusChange();
        Keyboard.enableRepeatEvents(true);
    }

    protected void initElements() {
        WidthAdder widthAdder = new WidthAdder(20);

        this.addTabButtons(widthAdder);

        String configInstanceName = this.configInstance.getName();
        this.addButton(ScreenConstants.getResetAllButton(widthAdder, () -> this.currentTab.getAllConfigs().stream().anyMatch(IConfigResettable::isModified), button -> {
            String question = StringUtils.translate("manyLib.gui.reset_tab_question"), yes = StringUtils.translate("gui.yes"), no = StringUtils.translate("gui.no");
            GuiYesNoMITE var3 = new GuiYesNoMITE
                    (this, question, configInstanceName + ": " + this.currentTab.getGuiDisplayName(), yes, no, ScreenConstants.confirmFlag);
            this.mc.displayGuiScreen(var3);
        }));
        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        this.addButton(ScreenConstants.getSortButton(this, widthAdder, 30, sortCategoryConfigEnum, button -> {
            ((IButtonPeriodic) button).next();
            this.sort(sortCategoryConfigEnum.getEnumValue());
        }));
        this.addWidget(ScreenConstants.getSearchButton(this));

        PullDownButton<?> pullDownButton = ScreenConstants.getPullDownButton(this, this.configInstance);
        this.addButton(pullDownButton);
        pullDownButton.initDropDownEntries(this.configInstance, this.getParent());
        pullDownButton.addToList(this::addButton);
    }

    void addTabButtons(WidthAdder widthAdder) {
        for (int index = 0; index < this.configTabs.size(); index++) {
            ConfigTab configTab = this.configTabs.get(index);
            String name = configTab.getGuiDisplayName();
            int stringWidth = this.fontRenderer.getStringWidth(name);
            int finalIndex = index;
            this.addButton(ButtonGeneric.builder(name, button -> this.setCurrentTab(finalIndex))
                    .onUpdate(button -> button.setEnabled(this.currentTabIndex != finalIndex))
                    .dimensions(widthAdder.getWidth(), 30, stringWidth + 10, 20)
                    .hoverStrings(configTab.getTooltip()).build());
            widthAdder.addWidth(stringWidth + 14);
        }
    }

    void setCurrentTab(int index) {
        this.needSyncTab = true;
        this.currentTab = this.configTabs.get(index);
        this.currentTabIndex = index;
    }

    @Override
    protected ConfigItem<?> createEntry(int realIndex, int relativeIndex) {
        return ConfigItem.getConfigItem(relativeIndex, this.currentTab.getSearchableConfig(realIndex), this);
    }

    @Override
    public int getContentSize() {
        return this.currentTab.getSearchableConfigSize();
    }

    @Override
    public int getMaxCapacity() {
        return 7;
    }

    //  Second block: only for compatibility with Modern Mite's IMBlocker, this block enables the input method.
    @Override
    protected boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClicked(mouseX, mouseY, mouseButton)) {
            if (this.needSyncTab) {
                this.needSyncTab = false;
                this.initGui();
            }
            return true;
        }
        if (this.entries.stream()
                .filter(configItem -> configItem instanceof AboutInputMethod)
                .map(configItem -> (AboutInputMethod) configItem)
                .anyMatch(aboutInputMethod -> aboutInputMethod.tryActivateIM(mouseX, mouseY, mouseButton))) return true;
        return false;
    }

    private long lastShift = 0L;

    @Override
    protected boolean onCharTyped(char charIn, int modifiers) {
        if (super.onCharTyped(charIn, modifiers)) {
            return true;
        }
        if (modifiers == Keyboard.KEY_LSHIFT) {
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
        ProgressSaving.saveProgress(this.configInstance.getName(), this.currentTabIndex, this.getStatus());
        this.firstSeen = true;
    }

    void sort(SortCategory sortCategory) {
        this.currentTab.sort(sortCategory);
        this.resetStatus();
        this.markShouldUpdateEntries();
    }

    @Override
    public void updateSearchResult(String input) {
        this.currentTab.updateSearchableConfigs(input);
        this.onContentChange();
    }

    public String createTitle() {
        return this.configInstance.getName() + " Configs";
    }
}