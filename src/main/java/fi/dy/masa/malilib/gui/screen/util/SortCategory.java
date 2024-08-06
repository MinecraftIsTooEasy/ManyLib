package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.compat.PinyinHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public enum SortCategory {
    Default((a, b) -> 0),// dummy
    PinYin((a, b) -> {
        PinyinHandler instance = PinyinHandler.getInstance();
        if (instance.isValid()) {
            try {
                return instance.compareTheInit(a.getConfigGuiDisplayName(), b.getConfigGuiDisplayName());
            } catch (InvocationTargetException | IllegalAccessException e) {
                ManyLib.logger.warn("PinyinHandler: failed to compare config names");
            }
        }
        return Default.category.compare(a, b);
    }),
    Alphabetical((a, b) -> a.getConfigGuiDisplayName().compareToIgnoreCase(b.getConfigGuiDisplayName())),
    Inverted((a, b) -> -Alphabetical.category.compare(a, b)),
    ;
    public final Comparator<ConfigBase<?>> category;

    SortCategory(Comparator<ConfigBase<?>> category) {
        this.category = category;
    }
}
