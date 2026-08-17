package fi.dy.masa.malilib.client.gui.screen;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.feature.SortCategory;
import fi.dy.masa.malilib.client.gui.widgets.WidgetSearchField;
import fi.dy.masa.malilib.client.gui.button.interfaces.ICycleButton;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.client.gui.screen.interfaces.ElementList;
import fi.dy.masa.malilib.client.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.screen.util.WidthAdder;
import fi.dy.masa.malilib.client.gui.widgets.WidgetGlobalConfigListView;
import fi.dy.masa.malilib.client.util.StringUtils;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.ConfigUtils;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class GlobalSearchScreen extends LayeredScreen implements ElementList<GlobalSearchScreen.SearchResult>, Searchable {
    private WidgetSearchField searchField;
    private final List<SearchResult> searchResultsCache = new ArrayList<>();
    private final List<SearchResult> searchResults = new ArrayList<>();
    private WidgetGlobalConfigListView widgetListView;

    public GlobalSearchScreen(GuiScreen parent) {
        super();
        this.setParent(parent);
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        String text = null;
        if (this.searchField != null) {
            text = this.searchField.getText();
        }
        super.initBaseLayer(layer);
        layer.addWidget(ScreenConstants.getTitle(ScreenText.GLOBAL_SEARCHING.translate()));

        WidthAdder widthAdder = new WidthAdder(40);

        ConfigEnum<SortCategory> sortCategoryConfigEnum = new ConfigEnum<>("manyLib.sortCategory", SortCategory.Default);
        layer.addWidget(ScreenConstants.getSortButton(this, widthAdder, 30, sortCategoryConfigEnum, button -> {
            ((ICycleButton) button).next();
            this.sort(sortCategoryConfigEnum.getEnumValue());
        }));

        WidgetGlobalConfigListView widgetListView = new WidgetGlobalConfigListView(this);
        ScreenConstants.setWidgetListViewDimensions(this,widgetListView);
        layer.addWidget(widgetListView);
        this.widgetListView = widgetListView;

        WidgetSearchField searchField = ScreenConstants.getSearchField(this, widgetListView);
        if (text != null) {
            searchField.initialSearch(text);
        } else {
            searchField.initialSearch();
        }
        layer.addWidget(searchField);
        this.searchField = searchField;

        widgetListView.onStatusChange();// initial search will change contents
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void updateSearchResult(String input) {
        this.searchResultsCache.clear();
        ManyLibApi.streamConfigHandlers()
                .flatMap(
                        iConfigHandler -> ConfigUtils.streamAllOptions(iConfigHandler)
                                .map(configBase -> new SearchResult(iConfigHandler.getModId(), configBase))
                )
                .filter(x -> ConfigDisplayApi.isSupported(x.configBase().getType()))
                .filter(x -> this.matchResult(x, input))
                .forEach(this.searchResultsCache::add);
        this.searchResults.clear();
        this.searchResults.addAll(this.searchResultsCache);
        this.widgetListView.onContentChange();
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean matchResult(SearchResult searchResult, String input) {
        if (input.isEmpty()) return true;
        String name = ConfigDisplayApi.getConfigDisplayName(searchResult.configBase());
        if (name != null && StringUtils.stringMatchesInput(name, input)) {
            return true;// match the name
        }
        String comment = ConfigDisplayApi.getConfigDisplayComment(searchResult.configBase());
        if (comment != null && StringUtils.stringMatchesInput(comment, input)) {
            return true;// match the comment
        }
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
    public SearchResult get(int index) {
        return this.searchResults.get(index);
    }

    public record SearchResult(String mod, ConfigBase<?> configBase) {
    }

}
