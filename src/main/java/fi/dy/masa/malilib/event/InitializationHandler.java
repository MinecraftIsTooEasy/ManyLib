package fi.dy.masa.malilib.event;

import fi.dy.masa.malilib.interfaces.IInitializationDispatcher;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;

import java.util.ArrayList;
import java.util.List;

public class InitializationHandler implements IInitializationDispatcher {
    private static final InitializationHandler INSTANCE = new InitializationHandler();

    private final List<IInitializationHandler> handlers = new ArrayList<>();

    public static IInitializationDispatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public void registerInitializationHandler(IInitializationHandler handler) {
        if (!this.handlers.contains(handler)) {
            this.handlers.add(handler);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onGameStartDone() {
        if (!this.handlers.isEmpty()) {
            for (IInitializationHandler handler : this.handlers) {
                handler.registerModHandlers();
            }
        }
//        ConfigManager.getInstance().loadAllConfigs();
//        KeyBinding.resetKeyBindingArrayAndHash();
        InputEventHandler.getKeybindManager().updateUsedKeys();
    }
}
