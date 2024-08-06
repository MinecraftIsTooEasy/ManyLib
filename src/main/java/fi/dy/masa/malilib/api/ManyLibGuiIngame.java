package fi.dy.masa.malilib.api;

public interface ManyLibGuiIngame {
    default void manyLib$setInfo(String string) {
        this.manyLib$setInfo(string, 60);
    }

    void manyLib$setInfo(String string, int duration);
}
