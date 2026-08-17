package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.client.gui.button.ResetButton;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.client.gui.widgets.WidgetListView;
import fi.dy.masa.malilib.client.gui.widgets.WidgetText;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.client.util.GuiUtils;
import fi.dy.masa.malilib.client.util.RenderUtils;
import fi.dy.masa.malilib.config.interfaces.IConfigResettable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.localization.ScreenText;

public abstract class WidgetConfig<T extends ConfigBase<?>> extends WidgetContainer {
    protected final T config;
    protected final ButtonGeneric resetButton;
    protected final WidgetText widgetText;

    /**
     * The dimensions will be set automatically in {@link WidgetListView#placeEntry}
     * <br>
     * And the position of sub-widgets should be set in {@link WidgetConfig#init}
     */
    public WidgetConfig(T config) {
        super(0, 0, 0, 0);
        this.config = config;

        this.resetButton = new ResetButton(0, 0, ((IConfigResettable) config)::isModified, button -> {
            config.resetToDefault();
            this.onResetClicked();
        });
        this.resetButton.setHoverStrings(ScreenText.RESET_BUTTON.translate());

        this.widgetText = new WidgetText(
                0, 0,
                ConfigDisplayApi.getConfigDisplayName(config),
                ConfigDisplayApi.getConfigDisplayComment(config),
                ConfigDisplayApi.getConfigDisplayColor(config)
        );

        this.addWidget(this.widgetText);
        this.addWidget(this.resetButton);
    }

    /**
     * At this point the dimensions of this is already set, and you can start placing your sub-widgets
     */
    @Override
    public void init() {
        super.init();
        ScreenConstants.placeResetButton(this, this.resetButton);
        ScreenConstants.placeCommentedText(this, this.config, this.widgetText);
    }

    public T getConfig() {
        return this.config;
    }

    public void addTooltip(String tooltip) {
        this.widgetText.addTooltip(tooltip, false);
    }

    public void addTooltip(String tooltip, boolean head) {
        this.widgetText.addTooltip(tooltip, head);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (drawContext.isTopLayer() && this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawRect(0, this.y, GuiUtils.getScaledWindowWidth(), this.height, ManyLibClientConfig.HighlightColor.getColorInteger());
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    public void onResetClicked() {
    }

}
