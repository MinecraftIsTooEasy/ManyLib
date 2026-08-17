package fi.dy.masa.malilib.client.input;

import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.util.InfoUtils;
import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;

public class KeyCallbackToggleBooleanConfigWithMessage extends KeyCallbackToggleBoolean {
    public KeyCallbackToggleBooleanConfigWithMessage(IConfigBoolean config) {
        super(config);
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        super.onKeyAction(action, key);
        InfoUtils.printBooleanConfigToggleMessage(ConfigDisplayApi.getConfigDisplayName(this.config), this.config.getBooleanValue());
        return true;
    }
}
