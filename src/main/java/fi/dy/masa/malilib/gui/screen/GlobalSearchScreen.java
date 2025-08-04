package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.feat.SortCategory;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.SearchField;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.layer.Layer;
import fi.dy.masa.malilib.gui.screen.interfaces.IConfigList;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.screen.util.ConfigItem;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.screen.util.WidthAdder;
import fi.dy.masa.malilib.gui.widgets.WidgetConfigListView;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class GlobalSearchScreen extends LayeredScreen implements Searchable, IConfigList {
    private SearchField searchField;
    private final List<SearchResult> searchResultsCache = new ArrayList<>();
    private final List<SearchResult> searchResults = new ArrayList<>();
    private final GuiScreen parent;
    private WidgetGlobalConfigListView widgetListView;
    private boolean firstSeen = true;

    public GlobalSearchScreen(GuiScreen parentScreen) {
        super();
        this.parent = parentScreen;
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        String text = null;
        if (!this.firstSeen) {
            text = this.searchField.getText();
        }
        super.initBaseLayer(layer);
        layer.addWidget(ScreenConstants.getTitle(StringUtils.translate("manyLib.gui.title.globalSearching")));

        WidthAdder widthAdder = new WidthAdder(40);

        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        layer.addWidget(ScreenConstants.getSortButton(this, widthAdder, 30, sortCategoryConfigEnum, button -> {
            ((IButtonPeriodic) button).next();
            this.sort(sortCategoryConfigEnum.getEnumValue());
        }));

        WidgetGlobalConfigListView widgetListView = new WidgetGlobalConfigListView(this);
        layer.addWidget(widgetListView);
        this.widgetListView = widgetListView;

        SearchField searchField = ScreenConstants.getSearchButton(this, this.widgetListView);
        if (text != null) {
            searchField.initialSearch(text);
        } else {
            searchField.initialSearch();
        }
        layer.addWidget(searchField);
        this.searchField = searchField;

        this.widgetListView.onStatusChange();
        Keyboard.enableRepeatEvents(true);

        this.firstSeen = false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (super.charTyped(chr, keyCode)) {
            return true;
        }
        if (keyCode == Keyboard.KEY_ESCAPE) {
            this.mc.displayGuiScreen(this.parent);
            return true;
        }
        return false;
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
        this.widgetListView.onContentChange();
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
        this.widgetListView.resetStatus();
        this.widgetListView.markDirty();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
        ConfigManager.getInstance().saveAllConfigs();
    }

    @Override
    public int size() {
        return this.searchResults.size();
    }

    @Override
    public ConfigBase<?> get(int index) {
        return this.searchResults.get(index).configBase();
    }

    private record SearchResult(String mod, ConfigBase<?> configBase) {
    }

    private static class WidgetGlobalConfigListView extends WidgetConfigListView {
        private final GlobalSearchScreen myScreen;

        private WidgetGlobalConfigListView(GlobalSearchScreen screen) {
            super(screen, screen);
            this.myScreen = screen;
        }

        @Override
        protected ConfigItem<?> createEntry(int realIndex, int relativeIndex) {
            SearchResult searchResult = this.myScreen.searchResults.get(realIndex);
            ConfigItem<?> configItem = ConfigItem.getConfigItem(relativeIndex, searchResult.configBase(), this.myScreen);
            configItem.addTooltip(GuiBase.TXT_AQUA + "<" + searchResult.mod() + ">", true);
            return configItem;
        }
    }
}
