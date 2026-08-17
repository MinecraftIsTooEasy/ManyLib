package fi.dy.masa.malilib.client.gui.widgets;

import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.screen.interfaces.ElementList;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.config.WidgetConfig;
import fi.dy.masa.malilib.config.options.ConfigBase;

public class WidgetConfigListView extends WidgetListView<WidgetConfig<?>> {
    private final ElementList<ConfigBase<?>> source;

    public WidgetConfigListView(ElementList<ConfigBase<?>> source) {
        super(0, 0, 0, 0);
        this.source = source;
    }

    @Override
    protected WidgetConfig<?> createEntry(int realIndex, int relativeIndex) {
        return ConfigDisplayApi.createWidget(this.source.get(realIndex));
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
