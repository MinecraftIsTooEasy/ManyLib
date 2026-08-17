package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.util.StringUtils;

import java.util.List;

public class WidgetModListView extends WidgetListView<WidgetModEntry> implements Searchable {
    private final List<String> mods;
    private List<String> displayedMods;

    public WidgetModListView(List<String> mods) {
        super(0, 0, 0, 0);
        this.mods = mods;
        this.displayedMods = this.mods;
    }

    @Override
    protected WidgetModEntry createEntry(int realIndex, int relativeIndex) {
        return new WidgetModEntry(this.displayedMods.get(realIndex));
    }

    @Override
    public int getEntryHeight() {
        return ScreenConstants.listEntryHeight;
    }

    @Override
    public int getContentSize() {
        return this.displayedMods.size();
    }

    @Override
    public int getPageCapacity() {
        return ScreenConstants.modMenuCapacity;
    }

    @Override
    public void updateSearchResult(String input) {
        this.displayedMods = this.mods.stream().filter(x -> StringUtils.stringMatchesInput(x, input)).toList();
        this.onContentChange();
    }
}
