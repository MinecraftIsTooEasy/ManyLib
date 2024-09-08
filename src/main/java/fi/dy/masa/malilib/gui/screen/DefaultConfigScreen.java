package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.ConfigTab;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.screen.interfaces.ScreenParented;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusScreen;
import fi.dy.masa.malilib.gui.screen.util.*;
import net.minecraft.GuiScreen;
import net.minecraft.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class DefaultConfigScreen extends ScreenParented implements Searchable, StatusScreen {
    public final IConfigHandler configInstance;
    public ConfigTab currentTab;
    public int currentTabIndex;
    private final List<ConfigItem<?>> configItems = new ArrayList<>();
    public ScrollBar<?> scrollBar;
    private int status;
    private int maxStatus;
    private boolean singlePage;
    public boolean needInit;
    private boolean firstSeen = true;

    public DefaultConfigScreen(GuiScreen parentScreen, IConfigHandler configInstance) {
        super(parentScreen);
        this.configInstance = configInstance;
        Keyboard.enableRepeatEvents(true);
        this.container = new DefaultConfigScreenContainer(this);
    }

    @Override
    public void initGui() {
        super.initGui();
        if (this.firstSeen) {
            this.currentTabIndex = ProgressSaving.getPage(this.configInstance.getName());
        }
        ((DefaultConfigScreenContainer) this.container).setCurrentTab(this.currentTabIndex);
        this.updateConfigItemsAfterTabChange();
        if (this.firstSeen) {
            this.firstSeen = false;
            this.setStatus(ProgressSaving.getStatus(this.configInstance.getName()));
        }
        this.onStatusChange();
        this.updateScreen();
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
        this.scrollBar.setVisible(!this.singlePage);
        this.scrollBar.updateArguments(ScreenConstants.pageCapacity, this.currentTab.getSearchableConfigSize());
        this.updateConfigItems();
    }

    @Override
    protected void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        this.drawDefaultBackground();
        this.getConfigItems().forEach(configItem -> configItem.render(mouseX, mouseY, false, drawContext));
        this.container.render(mouseX, mouseY, false, drawContext);
        this.getConfigItems().forEach(configItem -> configItem.postRenderHovered(mouseX, mouseY, false, drawContext));
        this.container.postRenderHovered(mouseX, mouseY, false, drawContext);
    }

    @Override
    protected void update() {
        super.update();
        if (!this.singlePage) this.wheelListener();
        this.getConfigItems().forEach(ConfigItem::update);
    }

    @Override
    public void setStatus(int status) {
        int oldStatus = this.status;
        this.status = MathHelper.clamp_int(status, 0, this.maxStatus);
        if (status != oldStatus) this.onStatusChange();
    }

    private void onStatusChange() {
        this.updateConfigItems();
        if (!this.singlePage) this.scrollBar.updateRatioByScreen(this.status);
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
        if (isScrollDown && this.status + ScreenConstants.pageCapacity < this.currentTab.getSearchableConfigSize()) {
            this.addStatus(ScreenConstants.oneScroll);
        }
        if (!isScrollDown && this.status > 0) this.addStatus(-ScreenConstants.oneScroll);
    }

    //  Third block: only for compatibility with Modern Mite's IMBlocker, this block enables the input method.

    @Override
    protected boolean onMouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClicked(mouseX, mouseY, mouseButton)) {
            if (this.needInit) {
                this.needInit = false;
                this.initGui();
            }
            return true;
        }
        if (this.getConfigItems().anyMatch(configItem -> configItem.onMouseClicked(mouseX, mouseY, mouseButton)))
            return true;
        if (this.getConfigItems()
                .filter(configItem -> configItem instanceof AboutInputMethod)
                .map(configItem -> (AboutInputMethod) configItem)
                .anyMatch(aboutInputMethod -> aboutInputMethod.tryActivateIM(mouseX, mouseY, mouseButton))) return true;
        return false;
    }

    @Override
    protected void onMouseReleased(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleased(mouseX, mouseY, mouseButton);
        this.getConfigItems().forEach(x -> x.onMouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void confirmClicked(boolean result, int flag) {
        if (result && flag == ScreenConstants.confirmFlag) {
            this.currentTab.getAllConfigs().forEach(IConfigResettable::resetToDefault);
        }
        this.mc.displayGuiScreen(this);
    }

    @Override
    protected boolean onCharTyped(char charIn, int modifiers) {
        if (this.getConfigItems().anyMatch(configItem -> configItem.onCharTyped(charIn, modifiers))) return true;
        if (super.onCharTyped(charIn, modifiers)) return true;
        return false;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.configInstance.save();
        InputEventHandler.getKeybindManager().updateUsedKeys();
        ProgressSaving.saveProgress(this.configInstance.getName(), this.currentTabIndex, this.status);
        this.firstSeen = true;
    }

    private Stream<ConfigItem<?>> getConfigItems() {
        return this.configItems.stream();
    }

    public void sort(SortCategory sortCategory) {
        this.currentTab.sort(sortCategory);
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