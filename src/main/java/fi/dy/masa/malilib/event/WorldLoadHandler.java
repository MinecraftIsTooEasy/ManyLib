package fi.dy.masa.malilib.event;

import fi.dy.masa.malilib.interfaces.IWorldLoadListener;
import fi.dy.masa.malilib.interfaces.IWorldLoadManager;
import net.minecraft.Minecraft;
import net.minecraft.WorldClient;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class WorldLoadHandler implements IWorldLoadManager {
    private static final WorldLoadHandler INSTANCE = new WorldLoadHandler();

    private final List<IWorldLoadListener> worldLoadPreHandlers = new ArrayList<>();
    private final List<IWorldLoadListener> worldLoadPostHandlers = new ArrayList<>();

    public static IWorldLoadManager getInstance() {
        return INSTANCE;
    }

    @Override
    public void registerWorldLoadPreHandler(IWorldLoadListener listener) {
        if (!this.worldLoadPreHandlers.contains(listener)) {
            this.worldLoadPreHandlers.add(listener);
        }
    }

    @Override
    public void unregisterWorldLoadPreHandler(IWorldLoadListener listener) {
        this.worldLoadPreHandlers.remove(listener);
    }

    @Override
    public void registerWorldLoadPostHandler(IWorldLoadListener listener) {
        if (!this.worldLoadPostHandlers.contains(listener)) {
            this.worldLoadPostHandlers.add(listener);
        }
    }

    @Override
    public void unregisterWorldLoadPostHandler(IWorldLoadListener listener) {
        this.worldLoadPostHandlers.remove(listener);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onWorldLoadPre(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc) {
        if (!this.worldLoadPreHandlers.isEmpty()) {
            for (IWorldLoadListener listener : this.worldLoadPreHandlers) {
                listener.onWorldLoadPre(worldBefore, worldAfter, mc);
            }
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onWorldLoadPost(@Nullable WorldClient worldBefore, @Nullable WorldClient worldAfter, Minecraft mc) {
        // Save all the configs when exiting a world
//        if (worldBefore != null && worldAfter == null) {
//            ConfigManager.getInstance().saveAllConfigs();
//        }
        // (Re-)Load all the configs from file when entering a world
//        else
        if (worldBefore == null && worldAfter != null) {
//            ConfigManager.getInstance().loadAllConfigs();
            InputEventHandler.getKeybindManager().updateUsedKeys();
        }

        if (!this.worldLoadPostHandlers.isEmpty() &&
                (worldBefore != null || worldAfter != null)) {
            for (IWorldLoadListener listener : this.worldLoadPostHandlers) {
                listener.onWorldLoadPost(worldBefore, worldAfter, mc);
            }
        }
    }
}
