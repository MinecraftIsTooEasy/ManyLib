package fi.dy.masa.malilib.localization;

public enum ScreenText implements ITranslatable {
    CONFIGURING("manyLib.gui.configuring"),
    DONE("gui.done"),
    FALSE("boolean.false"),
    GLOBAL_SEARCHING("manyLib.gui.title.globalSearching"),
    HOLD_SHIFT_FOR_INFO("manyLib.gui.button.hover.hold_shift_for_info"),
    KEY_SETTINGS("manyLib.gui.button.keySettings"),
    LIST_EMPTY("list.empty"),
    NO("gui.no"),
    OFF("toggle.off"),
    ON("toggle.on"),
    OPTIONS("manyLib.gui.button.options"),
    OTHER_MODS("manyLib.gui.button.other_mods"),
    PAGE_DOWN("manyLib.gui.button.pageDown"),
    PAGE_UP("manyLib.gui.button.pageUp"),
    RESET_ALL_BUTTON("manyLib.gui.button.reset_all"),
    RESET_BUTTON("manyLib.gui.button.reset"),
    RESET_TAB_QUESTION("manyLib.gui.reset_tab_question"),
    RIGHT_CLICK_TO_COPY("manyLib.gui.rightClickToCopy"),
    SEARCH_BUTTON("manyLib.gui.button.search"),
    TITLE_OPTIONS("manyLib.gui.title.options"),
    TOGGLE("manyLib.configToggle.toggle"),
    TRUE("boolean.true"),
    YES("gui.yes");

    private final String key;

    ScreenText(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
