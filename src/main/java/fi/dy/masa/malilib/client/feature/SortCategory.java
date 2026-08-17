package fi.dy.masa.malilib.client.feature;

import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.integration.PinyinHandler;
import fi.dy.masa.malilib.config.options.ConfigBase;

import java.util.Comparator;

public enum SortCategory {
    Default((a, b) -> 0),// dummy
    PinYin((a, b) -> {
        PinyinHandler instance = PinyinHandler.getInstance();
        return instance.isValid() ? instance.compareInitials(a, b) : Default.stringComparator.compare(a, b);
    }),
    Alphabetical(String::compareToIgnoreCase),
    Inverted((a, b) -> -Alphabetical.stringComparator.compare(a, b)),
    ;
    private final Comparator<String> stringComparator;

    public final Comparator<ConfigBase<?>> category;

    SortCategory(Comparator<String> category) {
        this.stringComparator = category;
        this.category = (a, b) -> category.compare(ConfigDisplayApi.getConfigDisplayName(a), ConfigDisplayApi.getConfigDisplayName(b));
    }
}
