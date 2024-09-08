package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;

class SlideableToggleButton extends ButtonGeneric {
    private boolean useSlider;

    public SlideableToggleButton(int x, int y, boolean useSlider, IButtonActionListener onPress) {
        super(x, y, 16, 16, "", onPress);
        this.setRenderDefaultBackground(false);
        this.useSlider = useSlider;
        this.updateIcon();
    }

    public void toggle() {
        this.useSlider = !this.useSlider;
        this.updateIcon();
    }

    private void updateIcon() {
        this.icon = this.useSlider ? ManyLibIcons.BTN_SLIDER : ManyLibIcons.BTN_TXTFIELD;
    }

}
