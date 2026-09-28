package fi.dy.masa.malilib.localization;

public enum TooltipText implements ITranslatable {
    AVAILABLE_VALUES("manyLib.gui.tooltip.available_values"),
    CLICK_TO_EDIT_STRING_LIST("manyLib.gui.tooltip.clickToEditStringList"),
    CLICK_TO_SELECT_COLOR("manyLib.gui.tooltip.clickToSelectColor"),
    CURRENT_VALUE("manyLib.gui.tooltip.current_value"),
    DEFAULT_VALUE("manyLib.gui.tooltip.default_value"),
    EDIT_CLIENT_CONFIG("manyLib.gui.tooltip.edit_client_config"),
    EDIT_COMMON_CONFIG("manyLib.gui.tooltip.edit_common_config"),
    EDIT_SERVER_CONFIG("manyLib.gui.tooltip.edit_server_config"),
    MOD_ID("manyLib.gui.tooltip.mod_id"),
    OPEN_IN_MOD_MENU("manyLib.gui.tooltip.open_in_mod_menu"),
    ;
    private final String key;

    TooltipText(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
