package fi.dy.masa.malilib.interfaces;

import net.minecraft.Minecraft;
import net.minecraft.WorldClient;

import javax.annotation.Nullable;

public interface IWorldLoadListener {
    /**
     * Called when the client world is going to be changed,
     * before the reference has been changed
     *
     * @param worldBefore the old world reference, before the new one gets assigned
     * @param worldAfter  the new world reference that is going to get assigned
     * @param mc
     */
    default void onWorldLoadPre(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc) {
    }

    /**
     * Called after the client world reference has been changed
     *
     * @param worldBefore the old world reference, before the new one gets assigned
     * @param worldAfter  the new world reference that is going to get assigned
     * @param mc
     */
    default void onWorldLoadPost(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc) {
    }
}
