package fi.dy.masa.malilib.util;

import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;

public class KeyCodes {

    public static int KEY_NONE = Keyboard.KEY_KANA;

    public static String getNameForKeyCode(int keyCode) {
        return Keyboard.getKeyName(keyCode);
    }

    public static int getKeyCodeFromName(String name) {
        return Keyboard.getKeyIndex(name);
    }

    @Nullable
    public static String getNameForKey(int keyCode)
    {
        return Keyboard.getKeyName(keyCode);
    }
}
