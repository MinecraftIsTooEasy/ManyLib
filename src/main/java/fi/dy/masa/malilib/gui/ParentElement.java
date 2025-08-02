package fi.dy.masa.malilib.gui;

import javax.annotation.Nullable;

public interface ParentElement extends Element {
    @Nullable
    Element getFocused();

    void setFocused(@Nullable Element focused);

    @Override
    default void setFocused(boolean focused) {
    }

    @Override
    default boolean isFocused() {
        return this.getFocused() != null;
    }
}
