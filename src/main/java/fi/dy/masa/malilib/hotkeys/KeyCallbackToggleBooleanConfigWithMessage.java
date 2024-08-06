package fi.dy.masa.malilib.hotkeys;

import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;
import fi.dy.masa.malilib.util.InfoUtils;

public class KeyCallbackToggleBooleanConfigWithMessage extends KeyCallbackToggleBoolean {
    public KeyCallbackToggleBooleanConfigWithMessage(IConfigBoolean config) {
        super(config);
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        super.onKeyAction(action, key);
        InfoUtils.printBooleanConfigToggleMessage(this.config.getConfigGuiDisplayName(), this.config.getBooleanValue());
        return true;
    }
}
