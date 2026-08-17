package fi.dy.masa.malilib.client.gui.interfaces;

import javax.annotation.Nullable;

public interface ISelectionListener<T> {
    void onSelectionChange(@Nullable T entry);
}
