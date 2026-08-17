package fi.dy.masa.malilib.util;

import java.util.List;
import java.util.Map;

public class CollectionUtils {
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static <K, V> K getFirstKey(Map<K, V> map) {
        return map.keySet().stream().findFirst().get();
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static <K, V> V getFirstValue(Map<K, V> map) {
        return map.values().stream().findFirst().get();
    }

    public static <T> T lastOf(List<T> list) {
        return list.get(list.size() - 1);
    }
}
