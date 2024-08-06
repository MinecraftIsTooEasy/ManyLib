package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.button.PullDownButton;
import fi.dy.masa.malilib.gui.button.ResetButton;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.button.SearchField;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.button.interfaces.ICommentedElement;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.screen.interfaces.GuiScreenParented;
import fi.dy.masa.malilib.gui.screen.interfaces.SearchableScreen;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusScreen;
import fi.dy.masa.malilib.gui.screen.util.*;
import net.minecraft.*;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DefaultConfigScreen extends GuiScreenParented implements SearchableScreen, StatusScreen {
    private final IConfigHandler configInstance;
    private final List<ConfigTab> configTabs;
    private ConfigTab currentTab;
    private int currentTabIndex;
    private final Map<Integer, GuiButton> buttonMap = new HashMap<>();
    private final List<ConfigItem<?>> configItems = new ArrayList<>();
    private ResetButton resetAllButton;
    private SearchField searchField;
    private ScrollBar scrollBar;
    private PullDownButton pullDownButton;
    private int status;
    private int maxStatus;
    private boolean singlePage;
    private boolean needInit;
    private boolean cancelKeyType;
    private final ConfigEnum<SortCategory> sortCategory = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
    private boolean firstSeen = true;

    public DefaultConfigScreen(GuiScreen parentScreen, String screenTitle, IConfigHandler configInstance) {
        super(parentScreen, screenTitle);
        this.configInstance = configInstance;
        this.configTabs = configInstance.getConfigTabs();
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void initGui() {
        this.putButtons();
        if (this.firstSeen) {
            this.currentTabIndex = ProgressSaving.getPage(this.configInstance.getName());
        }
        this.setCurrentTab(this.currentTabIndex);
        this.resetAllButton.enabled = this.currentTab.getAllConfigs().stream().anyMatch(IConfigResettable::isModified);
        this.updateConfigItemsAfterTabChange();
        if (this.firstSeen){
            this.firstSeen = false;
            this.setStatus(ProgressSaving.getStatus(this.configInstance.getName()));
        }
    }

    /**
     * 0~199: config tabs
     * 200~: look up the Constant class
     */
    private void putButtons() {
        this.buttonMap.clear();
        int buttonX = 20;
        for (int index = 0; index < this.configTabs.size(); index++) {
            ConfigTab configTab = this.configTabs.get(index);
            String name = configTab.getGuiDisplayName();
            int stringWidth = this.fontRenderer.getStringWidth(name);
            this.buttonMap.put(index, new GuiButton(index, buttonX, 30, stringWidth + 10, 20, name));
            buttonX += stringWidth + 14;
        }
        WidthAdder widthAdder = new WidthAdder(buttonX);
        this.resetAllButton = ScreenConstants.getResetAllButton(widthAdder);
        this.buttonMap.put(ScreenConstants.resetAllButtonID, this.resetAllButton);
        this.buttonMap.put(ScreenConstants.sortButtonID, ScreenConstants.getSortButton(this, widthAdder, this.sortCategory));
        this.searchField = ScreenConstants.getSearchButton(this);
        this.buttonMap.put(ScreenConstants.searchButtonID, this.searchField);
        this.scrollBar = ScreenConstants.getScrollBar(this, ScreenConstants.pageCapacity, 0);// dummy
        this.buttonMap.put(ScreenConstants.scrollBarID, this.scrollBar);
        this.pullDownButton = ScreenConstants.getPullDownButton(this);
        this.buttonMap.put(ScreenConstants.pullDownButtonID, this.pullDownButton);
        this.buttonMap.get(ScreenConstants.pullDownButtonID).drawButton = false;
    }

    private void updateConfigItems() {
        this.configItems.clear();
        for (int i = this.status; i < this.currentTab.getSearchableConfigSize() && i < this.status + ScreenConstants.pageCapacity; i++) {
            ConfigItem<?> configItem = ConfigItem.getConfigItem(i - this.status, this.currentTab.getSearchableConfig(i), this);
//            configItem.setVisible(true);
            this.configItems.add(configItem);
        }
    }

    private void updateConfigItemsAfterTabChange() {
        this.status = 0;
        this.maxStatus = this.currentTab.getMaxStatusForScreen(ScreenConstants.pageCapacity);
        this.singlePage = this.currentTab.getSearchableConfigSize() <= ScreenConstants.pageCapacity;
        this.scrollBar.drawButton = !this.singlePage;
        this.scrollBar.updateArguments(ScreenConstants.pageCapacity, this.currentTab.getSearchableConfigSize());
        this.updateConfigItems();
    }

    /**
     * Comments shall be drawn lastly, or there will be bugs
     */
    @Override
    public void drawScreen(int i, int j, float f) {
        this.drawDefaultBackground();
        this.drawString(this.fontRenderer, ManyLibConfig.titleFormat.getEnumValue() + this.screenTitle, 40, 15, 16777215);
        this.buttonMap.values().forEach(guiButton -> guiButton.drawButton(this.mc, i, j));
        this.getConfigItems().forEach(configItem -> configItem.draw(this, i, j));
        this.buttonMap.values().stream().filter(guiButton -> guiButton instanceof ICommentedElement)
                .map(guiButton -> (ICommentedElement) guiButton)
                .anyMatch(iCommentedElement -> iCommentedElement.tryDrawComment(this, i, j));
        this.getConfigItems().forEach(configItem -> configItem.tryDrawComment(this, i, j));
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (!this.singlePage) {
            this.wheelListener();
        }
        this.getConfigItems().forEach(ConfigItem::updateScreen);
        this.resetAllButton.enabled = this.currentTab.getAllConfigs().stream().anyMatch(IConfigResettable::isModified);
        this.searchField.updateScreen();
    }

    @Override
    public void setStatus(int status) {
        this.status = MathHelper.clamp_int(status, 0, this.maxStatus);
        this.updateConfigItems();
        if (!this.singlePage) {
            if (this.scrollBar == null) {
                ManyLib.logger.error("ValueScreen: multiPaged but no scroll bar?\n" + this);
            } else {
                this.scrollBar.updateRatioByScreen(this.status);
            }
        }
        this.updateScreen();
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public int getMaxStatus() {
        return this.maxStatus;
    }

    @Override
    public void scroll(boolean isScrollDown) {
        if (isScrollDown) {
            if (this.status + ScreenConstants.pageCapacity < this.currentTab.getSearchableConfigSize()) {
                this.addStatus(ScreenConstants.oneScroll);
            }
        } else {
            if (this.status > 0) {
                this.addStatus(-ScreenConstants.oneScroll);
            }
        }
    }

    /**
     * First block: main buttons listener <br/>
     * Second block: config buttons listener <br/>
     * Third block: only for compatibility with Modern Mite's IMBlocker, this block enables the input method.
     * Fourth block: in actionPerformed, the needInit may be true
     */

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int click) {
        this.searchField.mouseClicked(mouseX, mouseY, click);
        if (click == 0) {
            this.buttonMap.values().stream().filter(x -> x.mousePressed(this.mc, mouseX, mouseY))
                    .forEach(guiButton -> {
                        this.selectedButton = guiButton;
                        guiButton.playClickedSound(this.mc.sndManager);
                        this.actionPerformed(guiButton);
                    });
        }
        this.getConfigItems().forEach(configItem -> configItem.mouseClicked(mouseX, mouseY, click));
        this.pullDownButton.mouseClicked(mouseX, mouseY, click);
        this.getConfigItems()
                .filter(configItem -> configItem instanceof AboutInputMethod)
                .map(configItem -> (AboutInputMethod) configItem)
                .anyMatch(aboutInputMethod -> aboutInputMethod.tryActivateIM(mouseX, mouseY, click));
        if (this.needInit) {
            this.needInit = false;
            this.initGui();
        }
    }

    @Override
    protected void actionPerformed(GuiButton guiButton) {
        if (!guiButton.drawButton) return;
        int id = guiButton.id;
        switch (id) {
            case ScreenConstants.resetAllButtonID -> {
                String question = I18n.getString("manyLib.gui.reset_tab_question");
                String yes = I18n.getString("gui.yes");
                String no = I18n.getString("gui.no");
                GuiYesNoMITE var3 = new GuiYesNoMITE
                        (this, question, this.configInstance.getName(), yes, no, ScreenConstants.confirmFlag);
                this.mc.displayGuiScreen(var3);
            }
            case ScreenConstants.sortButtonID -> {
                ((IButtonPeriodic) this.buttonMap.get(ScreenConstants.sortButtonID)).next();
                this.sort();
            }
            case ScreenConstants.searchButtonID -> this.searchField.toggle();
            case ScreenConstants.pullDownButtonID -> this.pullDownButton.toggle();
            default -> {
                if (id < 200) {
                    this.needInit = true;
                    this.setCurrentTab(id);
                }
            }
        }
    }

    @Override
    public void confirmClicked(boolean result, int flag) {
        if (result && flag == ScreenConstants.confirmFlag) {
            this.currentTab.getAllConfigs().forEach(IConfigResettable::resetToDefault);
        }
        this.mc.displayGuiScreen(this);
    }

    @Override
    protected void keyTyped(char c, int i) {
        this.getConfigItems().forEach(configItem -> configItem.keyTyped(c, i));
        this.searchField.keyTyped(c, i);
        if (this.cancelKeyType) {
            this.cancelKeyType = false;
        } else {
            super.keyTyped(c, i);
        }
    }

    public void markCancelKeyType() {
        this.cancelKeyType = true;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.configInstance.save();
        InputEventHandler.getKeybindManager().updateUsedKeys();
    }

    private Stream<ConfigItem<?>> getConfigItems() {
        return this.configItems.stream();
    }

    private void setCurrentTab(int index) {
        this.buttonMap.get(this.currentTabIndex).enabled = true;
        this.currentTabIndex = index;
        this.currentTab = this.configTabs.get(index);
        this.buttonMap.get(index).enabled = false;
    }

    @Override
    public String toString() {
        return "Config Instance: " + this.configInstance.getName() + "\n"
                + "Tab: " + this.currentTab.getUnlocalizedName() + "\n"
                + "Single Paged: " + this.singlePage + "\n"
                + "Status: " + this.status;
    }

    private void sort() {
        this.currentTab.sort(this.sortCategory.getEnumValue());
        this.status = 0;
        this.updateConfigItems();
    }

    @Override
    public void updateSearchResult(String input) {
        this.currentTab.updateSearchableConfigs(input);
        this.updateConfigItemsAfterTabChange();
    }

    @Override
    public void resetSearchResult() {
        this.currentTab.resetSearchableConfigs();
        this.updateConfigItemsAfterTabChange();
    }

    @Override
    public void leaveThisScreen() {
        super.leaveThisScreen();
        ProgressSaving.saveProgress(this.configInstance.getName(), this.currentTabIndex, this.status);
    }
}

