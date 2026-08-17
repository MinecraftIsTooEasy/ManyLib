package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.button.ButtonBase;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.layer.StringListEditLayer;
import fi.dy.masa.malilib.client.gui.screen.LayeredScreen;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.localization.TooltipText;

import java.util.ArrayList;
import java.util.List;

public class WidgetConfigStringList extends WidgetConfig<ConfigStringList> {
    final ButtonBase editButton;

    public WidgetConfigStringList(ConfigStringList config) {
        super(config);
        this.editButton = ScreenConstants.getCommonButton(
                this.getStringPreview(),
                button -> toggleLayer(this.config, (LayeredScreen) this.mc.currentScreen)
        );
        this.addWidget(editButton);
    }

    @Override
    public void init() {
        super.init();

        ScreenConstants.placeCommonButton(this, this.editButton);
    }

    private void toggleLayer(ConfigStringList config, LayeredScreen screen) {
        screen.toggleLayer(layer -> layer instanceof StringListEditLayer,
                () -> new StringListEditLayer(config, screen, this::onEditFinished)
        );
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.editButton.isMouseOver()) {
            List<String> strings = new ArrayList<>();
            strings.add(TooltipText.CLICK_TO_EDIT_STRING_LIST.translate());
            strings.addAll(this.config.getStringListValue());
            RenderUtils.renderTooltip(mouseX, mouseY, strings, drawContext);
        }
    }

    private String getStringPreview() {
        String raw = ConfigDisplayApi.getButtonText(this.config);
        return raw.length() > 24 ? raw.substring(0, 20) + ",...]" : raw;
    }

    private void onEditFinished(List<String> list) {
        List<String> oldList = this.config.getStringListValue();
        oldList.clear();
        oldList.addAll(list);
        this.editButton.setDisplayString(this.getStringPreview());
    }

    @Override
    public void onResetClicked() {
        this.editButton.setDisplayString(this.getStringPreview());
    }
}
