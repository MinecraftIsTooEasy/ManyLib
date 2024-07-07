package fi.dy.masa.malilib.config.interfaces;

import java.util.List;

public interface IConfigStringList extends IConfigBase, IConfigResettable {
    List<String> getStringListValue();

    List<String> getDefaultStringListValue();
}
