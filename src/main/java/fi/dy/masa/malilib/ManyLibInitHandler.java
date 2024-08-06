package fi.dy.masa.malilib;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.gui.screen.ValueMenu;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.Minecraft;

public class ManyLibInitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ConfigManager.getInstance().registerConfig(ManyLibConfig.getInstance());
//        ManyLibConfig.openValueMenu.setHotKeyPressCallBack(minecraft -> minecraft.displayGuiScreen(ValueMenu.getInstance(null)));
//        ManyLibConfig.openHotkeyMenu.setHotKeyPressCallBack(minecraft -> minecraft.displayGuiScreen(HotKeyMenu.getInstance(null)));
//        TickHandler.getInstance().registerClientTickHandler(mc ->
//                ConfigManager.getInstance().getAllHotKeys().forEach(x -> {
//                    if (x.isPressed()) {
//                        x.onPressed(Minecraft.getMinecraft());
//                    }
//                }));
        ManyLibConfig.openConfigMenu.getKeybind().setCallback(new CallbackOpenConfigGui());
        ManyLibConfig.openModMenu.getKeybind().setCallback(new CallbackOpenModMenu());
    }

    private static class CallbackOpenConfigGui implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(ManyLibConfig.getInstance().getConfigScreen(minecraft.currentScreen));
            return true;
        }
    }

    private static class CallbackOpenModMenu implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(ValueMenu.getInstance(minecraft.currentScreen));
            return true;
        }
    }
}
