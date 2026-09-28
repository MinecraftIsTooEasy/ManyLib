package fi.dy.masa.malilib.config.interfaces;

public interface IConfigCyclic {
    void cycle(boolean forward);

    default void cycle() {
        this.cycle(true);
    }
}
