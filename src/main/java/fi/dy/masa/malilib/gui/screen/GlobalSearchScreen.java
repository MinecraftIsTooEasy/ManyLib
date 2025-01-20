package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.SearchField;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.screen.util.ConfigItem;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.screen.util.SortCategory;
import fi.dy.masa.malilib.gui.screen.util.WidthAdder;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class GlobalSearchScreen extends ListScreen<ConfigItem<?>> implements Searchable {
    private final List<SearchResult> searchResultsCache = new ArrayList<>();
    private final List<SearchResult> searchResults = new ArrayList<>();

    public GlobalSearchScreen(GuiScreen parentScreen) {
        super();
        this.setParent(parentScreen);
    }

    @Override
    public void initGui() {
        super.initGui();
        this.setTitle(StringUtils.translate("manyLib.gui.title.globalSearching"));

        WidthAdder widthAdder = new WidthAdder(200);

        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        this.addButton(ScreenConstants.getSortButton(this, widthAdder, 10, sortCategoryConfigEnum, button -> {
            ((IButtonPeriodic) button).next();
            this.sort(sortCategoryConfigEnum.getEnumValue());
        }));

        SearchField searchField = ScreenConstants.getSearchButton(this);
        searchField.initialSearch();
        this.addWidget(searchField);
        this.onStatusChange();
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected ConfigItem<?> createEntry(int realIndex, int relativeIndex) {
        SearchResult searchResult = this.searchResults.get(realIndex);
        ConfigItem<?> configItem = ConfigItem.getConfigItem(relativeIndex, searchResult.configBase(), this);
        configItem.addTooltip(GuiBase.TXT_AQUA + "<" + searchResult.mod() + ">", true);
        return configItem;
    }

    @Override
    public int getMaxCapacity() {
        return 8;
    }

    @Override
    public int getContentSize() {
        return this.searchResults.size();
    }

    @Override
    public void updateSearchResult(String input) {
        this.searchResultsCache.clear();
        ConfigManager.getInstance().getConfigMap().values().stream()
                .sorted(Comparator.comparing(IConfigHandler::getName))
                .flatMap(iConfigHandler ->
                        Stream.concat(iConfigHandler.getValues().stream(), iConfigHandler.getHotkeys().stream())
                                .map(configBase -> new SearchResult(iConfigHandler.getName(), configBase)))
                .filter(x -> ConfigItem.supported(x.configBase()))
                .filter(x -> this.matchResult(x, input))
                .forEach(this.searchResultsCache::add);
        this.searchResults.clear();
        this.searchResults.addAll(this.searchResultsCache);
        this.onContentChange();
    }

    private boolean matchResult(SearchResult searchResult, String input) {
        if (input.isEmpty()) return true;
        String configGuiDisplayName = searchResult.configBase().getConfigGuiDisplayName();
        if (configGuiDisplayName != null && StringUtils.stringMatchesInput(configGuiDisplayName, input))
            return true;// match the name
        String configGuiDisplayComment = searchResult.configBase().getConfigGuiDisplayComment();
        if (configGuiDisplayComment != null && StringUtils.stringMatchesInput(configGuiDisplayComment, input))
            return true;// match the comment
        return false;
    }

    void sort(SortCategory sortCategory) {
        if (sortCategory == SortCategory.Default) {
            this.searchResults.clear();
            this.searchResults.addAll(this.searchResultsCache);
        } else {
            this.searchResults.sort((x, y) -> sortCategory.category.compare(x.configBase(), y.configBase()));
        }
        this.resetStatus();
        this.markShouldUpdateEntries();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        ConfigManager.getInstance().saveAllConfigs();
    }

    private record SearchResult(String mod, ConfigBase<?> configBase) {
    }
}
