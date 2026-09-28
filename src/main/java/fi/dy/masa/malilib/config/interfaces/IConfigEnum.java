package fi.dy.masa.malilib.config.interfaces;

public interface IConfigEnum<E extends Enum<E>> extends IConfigValue, IConfigCyclic {
    E getEnumValue();

    E getDefaultEnumValue();

    void setEnumValue(E value);

    E[] getAllEnumValues();

    default void cycle(boolean forward) {
        E value = this.getEnumValue();
        E[] array = this.getAllEnumValues();
        this.setEnumValue(array[(value.ordinal() + (forward ? 1 : -1)) % array.length]);
    }
}
