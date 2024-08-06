package fi.dy.masa.malilib.event;

import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import net.minecraft.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class TickHandler {
    private static final TickHandler INSTANCE = new TickHandler();
    private final List<IClientTickHandler> clientTickHandlers = new ArrayList<>();

    public static TickHandler getInstance() {
        return INSTANCE;
    }

    public void registerClientTickHandler(IClientTickHandler handler) {
        if (!this.clientTickHandlers.contains(handler)) {
            this.clientTickHandlers.add(handler);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onClientTick(Minecraft mc) {
        if (!this.clientTickHandlers.isEmpty()) {
            for (IClientTickHandler handler : this.clientTickHandlers) {
                handler.onClientTick(mc);
            }
        }
    }

    static {
        INSTANCE.registerClientTickHandler(mc -> KeybindMulti.reCheckPressedKeys());
    }
}
