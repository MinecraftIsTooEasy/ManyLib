package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.screen.GlobalSearchScreen;
import fi.dy.masa.malilib.client.gui.screen.interfaces.ElementList;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.config.WidgetConfig;

public class WidgetGlobalConfigListView extends WidgetListView<WidgetConfig<?>> {
    private final ElementList<GlobalSearchScreen.SearchResult> source;

    public WidgetGlobalConfigListView(ElementList<GlobalSearchScreen.SearchResult> source) {
        super(0, 0, 0, 0);
        this.source = source;
    }

    @Override
    protected WidgetConfig<?> createEntry(int realIndex, int relativeIndex) {
        GlobalSearchScreen.SearchResult searchResult = this.source.get(realIndex);
        WidgetConfig<?> widgetConfig = ConfigDisplayApi.createWidget(searchResult.configBase());
        widgetConfig.addTooltip(GuiBase.TXT_AQUA + "<" + searchResult.mod() + ">", true);
        return widgetConfig;
    }

    @Override
    public int getEntryHeight() {
        return ScreenConstants.listEntryHeight;
    }

    @Override
    public int getContentSize() {
        return this.source.size();
    }

    @Override
    public int getPageCapacity() {
        return ScreenConstants.configScreenCapacity;
    }
}
