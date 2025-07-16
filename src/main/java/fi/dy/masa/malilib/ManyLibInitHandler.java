package fi.dy.masa.malilib;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.fml.ManyLibEventsFML;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.gui.screen.FakeModMenu;
import fi.dy.masa.malilib.gui.screen.GlobalSearchScreen;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.xiaoyu233.fml.reload.event.MITEEvents;

public class ManyLibInitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        MITEEvents.MITE_EVENT_BUS.register(new ManyLibEventsFML());
        ConfigManager.getInstance().registerConfig(ManyLibConfig.getInstance());
        ManyLibConfig.OpenConfigMenu.getKeybind().setCallback(new CallbackOpenConfigGui());
        ManyLibConfig.OpenModMenu.getKeybind().setCallback(new CallbackOpenModMenu());
        ManyLibConfig.SearchAny.getKeybind().setCallback(new CallbackSearchAny());
        ManyLibConfig.TitleFormat.setValueChangeCallback(config -> {
            GuiScreen screen = GuiUtils.getCurrentScreen();
            if (screen instanceof DefaultConfigScreen defaultConfigScreen) {
                defaultConfigScreen.setTitle(config.getEnumValue() + defaultConfigScreen.createTitle());
            }
        });
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
            minecraft.displayGuiScreen(new FakeModMenu(minecraft.currentScreen));
            return true;
        }
    }

    private static class CallbackSearchAny implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(new GlobalSearchScreen(null));
            return true;
        }
    }
}
