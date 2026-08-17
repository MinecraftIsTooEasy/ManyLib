package fi.dy.masa.malilib.config.interfaces;

import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.core.Side;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IConfigHandler extends Comparable<IConfigHandler> {
    String getModId();

    Side getSide();

    void load();

    void save();

    @NotNull
    List<ConfigBase<?>> getValues();

    @Override
    default int compareTo(@NotNull IConfigHandler o) {
        int compare = this.getModId().compareTo(o.getModId());
        if (compare != 0) return compare;
        return Integer.compare(this.getSide().ordinal(), o.getSide().ordinal());
    }
}
