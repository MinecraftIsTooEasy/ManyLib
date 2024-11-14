package fi.dy.masa.malilib.gui.button.interfaces;

import fi.dy.masa.malilib.gui.button.ButtonBase;

@FunctionalInterface
public interface IButtonUpdateListener {
    void onUpdate(ButtonBase button);
}
