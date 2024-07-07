package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigBase;

import java.util.Comparator;

public enum SortCategory {
    Default((a, b) -> 0),// dummy
    Alphabetical((a, b) -> a.getConfigGuiDisplayName().compareToIgnoreCase(b.getConfigGuiDisplayName())),
    Inverted((a, b) -> -a.getConfigGuiDisplayName().compareToIgnoreCase(b.getConfigGuiDisplayName())),
    ;
    public final Comparator<ConfigBase<?>> category;

    SortCategory(Comparator<ConfigBase<?>> category) {
        this.category = category;
    }
}
