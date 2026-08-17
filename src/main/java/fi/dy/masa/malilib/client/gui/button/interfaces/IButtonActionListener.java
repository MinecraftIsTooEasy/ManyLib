package fi.dy.masa.malilib.client.gui.button.interfaces;

import fi.dy.masa.malilib.client.gui.button.ButtonBase;

@FunctionalInterface
public interface IButtonActionListener {
    void actionPerformedWithButton(ButtonBase button);

//    default void actionPerformedWithButton(ButtonBase button, int mouseButton) {
//        this.actionPerformedWithButton(button);
//    }
}
