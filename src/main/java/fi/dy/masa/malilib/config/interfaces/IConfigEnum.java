package fi.dy.masa.malilib.config.interfaces;

public interface IConfigEnum<E> extends IConfigValue, IConfigCyclic {
    E getEnumValue();

    E getDefaultEnumValue();

    void setEnumValue(E value);

    E getNext();

    int getOrdinal();

    E[] getAllEnumValues();

    default void next() {
        this.setEnumValue(this.getNext());
    }

    ;
}
