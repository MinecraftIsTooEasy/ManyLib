package fi.dy.masa.malilib.config.interfaces;

import fi.dy.masa.malilib.core.Side;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface IConfigHandler extends Comparable<IConfigHandler> {
    /**
     * @return The id is same to mod id in most cases.
     * <br>
     * You can assign a different id for another config handler.
     */
    String getId();

    Side getSide();

    void load();

    void save();

    @NotNull
    List<? extends IConfigBase> getValues();

    @Override
    default int compareTo(@NotNull IConfigHandler o) {
        int compare = this.getId().compareTo(o.getId());
        if (compare != 0) return compare;
        return Integer.compare(this.getSide().ordinal(), o.getSide().ordinal());
    }
}
