package fi.dy.masa.malilib.core;

import java.util.Locale;

public enum Side {
    COMMON,
    CLIENT,
    SERVER,
    ;

    @Override
    public String toString() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
