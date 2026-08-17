package fi.dy.masa.malilib.client.event;

import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.config.interfaces.IClientConfigHandler;
import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.client.input.IKeybindManager;
import fi.dy.masa.malilib.client.input.IKeybindProvider;
import fi.dy.masa.malilib.client.unsafe.ModMenuAccess;
import fi.dy.masa.malilib.integration.ModReference;
import net.minecraft.KeyBinding;

import java.util.List;

public class ClientHooks {
    public static void onInitialization() {
        ManyLibApi.streamConfigHandlers().forEach(configHandler -> {
            if (configHandler instanceof IClientConfigHandler clientConfigHandler) {
                List<ConfigHotkey> hotkeys = clientConfigHandler.getHotkeys();
                if (!hotkeys.isEmpty()) {
                    InputEventHandler.getKeybindManager().registerKeybindProvider(new IKeybindProvider() {
                        @Override
                        public void addKeysToMap(IKeybindManager manager) {
                            hotkeys.forEach(hotkey -> manager.addKeybindToMap(hotkey.getKeybind()));
                        }

                        @Override
                        public void addHotkeys(IKeybindManager manager) {
                            String id = clientConfigHandler.getModId();
                            manager.addHotkeysForCategory(id, id + ".hotkeys.category.generic_hotkeys", hotkeys);
                        }
                    });
                }
            }
        });

        KeyBinding.resetKeyBindingArrayAndHash();
        InputEventHandler.getKeybindManager().updateUsedKeys();

        if (ModReference.hasMod(ModReference.MOD_MENU)) {
            ModMenuAccess.registerFactories();
        }
    }
}
