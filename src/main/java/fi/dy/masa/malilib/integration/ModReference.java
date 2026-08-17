package fi.dy.masa.malilib.integration;

import fi.dy.masa.malilib.util.Platform;

public class ModReference {
    public static final String MOD_MENU = "modmenu";

    public static boolean hasMod(String id) {
        return Platform.hasMod(id);
    }
}
