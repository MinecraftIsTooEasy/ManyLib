package fi.dy.masa.malilib.client.input;

public interface IHotkeyCallback {
    /**
     * Called when a hotkey action happens.
     *
     * @param action
     * @param key
     * @return true if further processing of the just pressed key should be cancelled
     */
    boolean onKeyAction(KeyAction action, IKeybind key);
}
