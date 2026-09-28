package fi.dy.masa.malilib.client.gui.screen;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.WidgetModListView;
import fi.dy.masa.malilib.client.gui.widgets.WidgetSearchField;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.GuiScreen;

public class ModMenuV1 extends LayeredScreen {
    public ModMenuV1(GuiScreen parent) {
        super();
        this.setParent(parent);
    }

    @Override
    protected void initBaseLayer(Layer layer) {
        super.initBaseLayer(layer);

        layer.addWidget(ScreenConstants.getTitle(ManyLibClientConfig.TitleFormat.getEnumValue() + ScreenText.TITLE_OPTIONS.translate()));

        WidgetModListView widgetListView = new WidgetModListView(ManyLibApi.streamIds().toList());
        ScreenConstants.setWidgetListViewDimensions(this, widgetListView);
        layer.addWidget(widgetListView);

        WidgetSearchField searchField = ScreenConstants.getSearchField(widgetListView);
        searchField.toggle();// make visible
        layer.addWidget(searchField);
    }
}
