package fi.dy.masa.malilib.client.internal;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.client.gui.screen.GlobalSearchScreen;
import fi.dy.masa.malilib.client.input.IHotkeyCallback;
import fi.dy.masa.malilib.client.input.IKeybind;
import fi.dy.masa.malilib.client.input.KeyAction;
import fi.dy.masa.malilib.client.util.GuiUtils;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;

public class ManyLibClientInitHandler implements IInitializationHandler {
    @Override
    public void registerModHandlers() {
        ManyLibApi.registerConfigHandler(ManyLibClientConfig.getInstance());

        ManyLibClientApi.setConfigScreenFactory(ManyLibClientConfig.getInstance(), ManyLibConfigScreen::new);

        ManyLibClientConfig.OpenConfigMenu.getKeybind().setCallback(new CallbackOpenConfigGui());
        ManyLibClientConfig.OpenModMenu.getKeybind().setCallback(new CallbackOpenModMenu());
        ManyLibClientConfig.SearchAny.getKeybind().setCallback(new CallbackSearchAny());
        ManyLibClientConfig.TitleFormat.setValueChangeCallback(config -> {
            GuiScreen screen = GuiUtils.getCurrentScreen();
            if (screen instanceof ManyLibConfigScreen configScreen) {
                configScreen.updateTitle();
            }
        });
    }

    private static class CallbackOpenConfigGui implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(ManyLibClientApi.createConfigScreen(ManyLibClientConfig.getInstance(), minecraft.currentScreen));
            return true;
        }
    }

    private static class CallbackOpenModMenu implements IHotkeyCallback {
        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {
            Minecraft minecraft = Minecraft.getMinecraft();
            minecraft.displayGuiScreen(ManyLibClientApi.createModMenu(minecraft.currentScreen));
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
