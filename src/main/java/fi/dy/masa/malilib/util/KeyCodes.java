package fi.dy.masa.malilib.util;

import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;

public class KeyCodes {

    public final static int KEY_NONE = Keyboard.KEY_KANA;

    public static String getNameForKeyCode(int keyCode) {
        return Keyboard.getKeyName(keyCode);
    }

    public static int getKeyCodeFromName(String name) {
        return Keyboard.getKeyIndex(name);
    }

    @Nullable
    public static String getNameForKey(int keyCode) {
        return Keyboard.getKeyName(keyCode);
    }

    public static String getStorageString(int... keyCodes) {
        StringBuilder sb = new StringBuilder(32);
        for (int i = 0; i < keyCodes.length; ++i) {
            if (i > 0) {
                sb.append(",");
            }

            int keyCode = keyCodes[i];
            String name = getNameForKey(keyCode);

            if (name != null) {
                sb.append(name);
            }
        }
        return sb.toString();
    }
}
