package fi.dy.masa.malilib.feat;

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
                return instance.compareTheInit(a, b);
            } catch (InvocationTargetException | IllegalAccessException e) {
                ManyLib.logger.warn("PinyinHandler: failed to compare config names");
            }
        }
        return Default.stringComparator.compare(a, b);
    }),
    Alphabetical(String::compareToIgnoreCase),
    Inverted((a, b) -> -Alphabetical.stringComparator.compare(a, b)),
    ;
    private final Comparator<String> stringComparator;

    public final Comparator<ConfigBase<?>> category;

    SortCategory(Comparator<String> category) {
        this.stringComparator = category;
        this.category = (a, b) -> category.compare(a.getConfigGuiDisplayName(), b.getConfigGuiDisplayName());
    }
}
