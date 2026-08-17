package fi.dy.masa.malilib.mixin.interfaces;

public interface IGuiIngame {
    default void manylib$setOverlayMessage(String string) {
        this.manylib$setOverlayMessage(string, 60);
    }

    void manylib$setOverlayMessage(String string, int time);
}
