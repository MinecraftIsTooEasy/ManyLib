package fi.dy.masa.malilib.api;

import net.minecraft.EnumChatFormatting;

public interface ManyLibGuiIngame {
    void manyLib$setInfo(String string, int rgb, int duration);

    default void manyLib$setInfo(String string) {
        this.manyLib$setInfo(string, EnumChatFormatting.WHITE.rgb, 60);
    }
}
