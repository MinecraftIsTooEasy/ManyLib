package fi.dy.masa.malilib.hotkeys;

public enum EnumKeybindSettingsPreSet {
    DEFAULT(KeybindSettings.DEFAULT),
    EXCLUSIVE(KeybindSettings.EXCLUSIVE),
    RELEASE(KeybindSettings.RELEASE),
    RELEASE_ALLOW_EXTRA(KeybindSettings.RELEASE_ALLOW_EXTRA),
    RELEASE_EXCLUSIVE(KeybindSettings.RELEASE_EXCLUSIVE),
    NOCANCEL(KeybindSettings.NOCANCEL),
    PRESS_ALLOWEXTRA(KeybindSettings.PRESS_ALLOWEXTRA),
    PRESS_ALLOWEXTRA_EMPTY(KeybindSettings.PRESS_ALLOWEXTRA_EMPTY),
    PRESS_NON_ORDER_SENSITIVE(KeybindSettings.PRESS_NON_ORDER_SENSITIVE),
    INGAME_BOTH(KeybindSettings.INGAME_BOTH),
    MODIFIER_INGAME(KeybindSettings.MODIFIER_INGAME),
    MODIFIER_INGAME_EMPTY(KeybindSettings.MODIFIER_INGAME_EMPTY),
    MODIFIER_GUI(KeybindSettings.MODIFIER_GUI),
    GUI(KeybindSettings.GUI),
    ;

    public final KeybindSettings keybindSettings;

    EnumKeybindSettingsPreSet(KeybindSettings keybindSettings) {
        this.keybindSettings = keybindSettings;
    }

    public static EnumKeybindSettingsPreSet mapToEnum(KeybindSettings keybindSettings) {
        for (EnumKeybindSettingsPreSet value : EnumKeybindSettingsPreSet.values()) {
            if (value.keybindSettings.equals(keybindSettings)) return value;
        }
        return null;
    }
}
