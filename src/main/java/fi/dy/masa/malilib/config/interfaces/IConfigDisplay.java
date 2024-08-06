package fi.dy.masa.malilib.config.interfaces;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.util.Color4f;

public interface IConfigDisplay {
    /**
     * Returns the text displayed inside the button.
     */
    String getDisplayText();

    /*
     * Returns the color of the config item.
     * */
    default Color4f getDisplayColor() {
        return Color4f.fromColor(GuiBase.COLOR_WHITE);
    }
}
