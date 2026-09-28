package fi.dy.masa.malilib.client.gui.button.interfaces;

public interface ICycleButton extends IButtonStringUpdatable {
    void cycle(boolean forward);

    default void cycle() {
        this.cycle(true);
    }
}
