package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.button.*;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.screen.interfaces.GuiScreenParented;
import fi.dy.masa.malilib.gui.screen.interfaces.SearchableScreen;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusScreen;
import fi.dy.masa.malilib.gui.screen.util.*;
import net.minecraft.GuiScreen;
import net.minecraft.GuiYesNoMITE;
import net.minecraft.I18n;
import net.minecraft.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DefaultConfigScreen extends GuiScreenParented implements SearchableScreen, StatusScreen {
    private final IConfigHandler configInstance;
    private final List<ConfigTab> configTabs;
    private ConfigTab currentTab;
    private int currentTabIndex;
    private final List<ButtonWidget> buttons = new ArrayList<>();
    private final List<ConfigItem<?>> configItems = new ArrayList<>();
    private ResetButton resetAllButton;
    private SearchField<?> searchField;
    private ScrollBar<?> scrollBar;
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
        if (this.firstSeen) {
            this.firstSeen = false;
            this.setStatus(ProgressSaving.getStatus(this.configInstance.getName()));
        }
        this.onStatusChange();
    }

    /**
     * 0~199: config tabs
     * 200~: look up the Constant class
     */
    private void putButtons() {
        this.buttons.clear();
        WidthAdder widthAdder = new WidthAdder(20);
        for (int index = 0; index < this.configTabs.size(); index++) {
            ConfigTab configTab = this.configTabs.get(index);
            String name = configTab.getGuiDisplayName();
            int stringWidth = this.fontRenderer.getStringWidth(name);
            int finalIndex = index;
            this.buttons.add(ButtonWidget.builder(name, button -> {
                        this.needInit = true;
                        this.setCurrentTab(finalIndex);
                    })
                    .dimensions(widthAdder.getWidth(), 30, stringWidth + 10, 20)
                    .build());
            widthAdder.addWidth(stringWidth + 14);
        }
        this.resetAllButton = ScreenConstants.getResetAllButton(widthAdder, button -> {
            String question = I18n.getString("manyLib.gui.reset_tab_question"), yes = I18n.getString("gui.yes"), no = I18n.getString("gui.no");
            GuiYesNoMITE var3 = new GuiYesNoMITE
                    (this, question, this.configInstance.getName(), yes, no, ScreenConstants.confirmFlag);
            this.mc.displayGuiScreen(var3);
        });

        this.buttons.add(this.resetAllButton);
        this.buttons.add(ScreenConstants.getSortButton(this, widthAdder, this.sortCategory, button -> {
            ((IButtonPeriodic) button).next();
            this.sort();
        }));
        this.searchField = ScreenConstants.getSearchButton(this);
        this.buttons.add(this.searchField);
        this.scrollBar = ScreenConstants.getScrollBar(this, ScreenConstants.pageCapacity, 0);// dummy
        this.buttons.add(this.scrollBar);
        this.pullDownButton = ScreenConstants.getPullDownButton(this);
        this.pullDownButton.drawButton = false;
        this.buttons.add(this.pullDownButton);
    }

    private void updateConfigItems() {
        this.configItems.clear();
        for (int i = this.status; i < this.currentTab.getSearchableConfigSize() && i < this.status + ScreenConstants.pageCapacity; i++) {
            ConfigItem<?> configItem = ConfigItem.getConfigItem(i - this.status, this.currentTab.getSearchableConfig(i), this);
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
        this.buttons.forEach(guiButton -> guiButton.drawButton(this.mc, i, j));
        this.getConfigItems().forEach(configItem -> configItem.draw(this, i, j));
        this.buttons.stream().anyMatch(buttonWidget -> buttonWidget.tryDrawTooltip(this, i, j));
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
        int oldStatus = this.status;
        this.status = MathHelper.clamp_int(status, 0, this.maxStatus);
        if (status != oldStatus) {
            this.onStatusChange();
        }
    }

    private void onStatusChange() {
        this.updateConfigItems();
        if (this.singlePage == false) {
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
     * Fourth block: after the buttons clicked, the needInit may be true
     */

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int click) {
        this.searchField.mouseClicked(mouseX, mouseY, click);
        if (click == 0) {
            this.buttons.stream().filter(x -> x.mousePressed(this.mc, mouseX, mouseY))
                    .forEach(buttonWidget -> {
                        this.selectedButton = buttonWidget;
                        buttonWidget.playClickedSound(this.mc.sndManager);
                        buttonWidget.onPress();
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
        ProgressSaving.saveProgress(this.configInstance.getName(), this.currentTabIndex, this.status);
    }

    private Stream<ConfigItem<?>> getConfigItems() {
        return this.configItems.stream();
    }

    private void setCurrentTab(int index) {
        this.buttons.get(this.currentTabIndex).enabled = true;
        this.currentTabIndex = index;
        this.currentTab = this.configTabs.get(index);
        this.buttons.get(index).enabled = false;
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

}

