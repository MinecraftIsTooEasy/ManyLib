package fi.dy.masa.malilib.client.feature;

import fi.dy.masa.malilib.client.input.IHotkeyCallback;
import fi.dy.masa.malilib.client.input.IKeybind;
import fi.dy.masa.malilib.client.input.KeyAction;

public class TriggerCallback {
    @SuppressWarnings("UnusedReturnValue")
    public static boolean run(IKeybind keybind) {
        IHotkeyCallback callback = keybind.getCallback();
        if (callback == null) return false;
        boolean cancel = callback.onKeyAction(KeyAction.PRESS, keybind);
        if (cancel) return true;
        return callback.onKeyAction(KeyAction.RELEASE, keybind);
    }
}
