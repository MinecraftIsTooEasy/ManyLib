package fi.dy.masa.malilib.localization;

public enum CommandText implements ITranslatable {
    CONFIG_NOT_FOUND("commands.manyLib.reload.configNotFound"),
    RELOAD_ALL_SUCCESS("commands.manyLib.reloadAll.success"),
    RELOAD_ALL_USAGE("commands.manyLib.reloadAll.usage"),
    RELOAD_SUCCESS("commands.manyLib.reload.success"),
    RELOAD_USAGE("commands.manyLib.reload.usage"),
    USAGE("commands.manyLib.usage"),
    ;

    private final String key;

    CommandText(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
