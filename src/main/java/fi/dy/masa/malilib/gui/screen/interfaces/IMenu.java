package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.button.ButtonWidget;

public interface IMenu {
    default ButtonWidget getButton(int x, int y, String name, String tooltip, ButtonWidget.PressAction onPress) {
        return ButtonWidget.builder(name, onPress).position(x, y).tooltip(tooltip).build();
    }
}
