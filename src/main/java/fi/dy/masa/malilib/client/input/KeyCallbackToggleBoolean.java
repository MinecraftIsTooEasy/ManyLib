package fi.dy.masa.malilib.client.input;


import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;

public class KeyCallbackToggleBoolean implements IHotkeyCallback {
    protected final IConfigBoolean config;

    public KeyCallbackToggleBoolean(IConfigBoolean config) {
        this.config = config;
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        this.config.toggleBooleanValue();
        return true;
    }
}
