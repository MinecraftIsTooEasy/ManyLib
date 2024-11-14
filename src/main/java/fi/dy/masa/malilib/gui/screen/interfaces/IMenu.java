package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;

public interface IMenu {
    default ButtonBase getButton(int x, int y, String name, String tooltip, IButtonActionListener onPress) {
        return ButtonGeneric.builder(name, onPress).position(x, y).hoverStrings(tooltip).build();
    }
}
