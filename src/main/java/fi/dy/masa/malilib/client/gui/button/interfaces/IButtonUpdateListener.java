package fi.dy.masa.malilib.client.gui.button.interfaces;

import fi.dy.masa.malilib.client.gui.button.ButtonBase;

@FunctionalInterface
public interface IButtonUpdateListener {
    void onUpdate(ButtonBase button);
}
