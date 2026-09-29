package fi.dy.masa.malilib.client.gui.interfaces;

import org.jspecify.annotations.Nullable;

public interface ISelectionListener<T> {
    void onSelectionChange(@Nullable T entry);
}
