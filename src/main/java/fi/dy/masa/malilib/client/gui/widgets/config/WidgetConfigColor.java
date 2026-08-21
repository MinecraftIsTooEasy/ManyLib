package fi.dy.masa.malilib.client.gui.widgets.config;

import fi.dy.masa.malilib.client.gui.layer.ColorEditLayer;
import fi.dy.masa.malilib.client.gui.screen.LayeredScreen;
import fi.dy.masa.malilib.client.gui.screen.util.ColorBoard;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.text.TextField;
import fi.dy.masa.malilib.client.gui.widgets.WidgetTextField;
import fi.dy.masa.malilib.client.util.SoundUtils;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.localization.TooltipText;

public class WidgetConfigColor extends WidgetConfig<ConfigColor> {
    final WidgetTextField<? extends TextField> widgetTextField;
    final ColorBoard colorBoard;

    public WidgetConfigColor(ConfigColor config) {
        super(config);

        this.widgetTextField = ScreenConstants.getWrapperForColor(config);
        this.widgetTextField.setText(config.getColorString());
        this.colorBoard = new ColorBoard(config, 0, 0, 16, 16);
        this.colorBoard.setTooltip(TooltipText.CLICK_TO_SELECT_COLOR.translate());
        this.addWidget(this.colorBoard);
        this.addWidget(this.widgetTextField);
    }

    @Override
    public void init() {
        super.init();

        ScreenConstants.placeTextFieldWrapper(this, this.widgetTextField);
        ScreenConstants.placeColorBoard(this, this.colorBoard);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        if (this.colorBoard.isMouseOver(mouseX, mouseY)) {
            SoundUtils.click(this.mc);
            ((LayeredScreen) this.mc.currentScreen).toggleLayer(layer -> layer instanceof ColorEditLayer,
                    () -> new ColorEditLayer(this.config, this.mc.currentScreen, this::onColorFinished)
            );
            return true;
        }
        return false;
    }

    private void onColorFinished() {
        this.widgetTextField.setText(this.config.getColorString());
    }

    @Override
    public void onResetClicked() {
        this.widgetTextField.getTextField().setText(this.config.getStringValue());
    }
}
