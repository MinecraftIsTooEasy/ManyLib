package fi.dy.masa.malilib.client.input;

import fake.org.lwjgl.glfw.GLFW;
import fi.dy.masa.malilib.client.util.StringUtils;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class KeyCodes {
    public static final int KEY_NONE = GLFW.GLFW_KEY_UNKNOWN;

    public static final int KEY_SPACE = GLFW.GLFW_KEY_SPACE;
    public static final int KEY_APOSTROPHE = GLFW.GLFW_KEY_APOSTROPHE;
    public static final int KEY_COMMA = GLFW.GLFW_KEY_COMMA;
    public static final int KEY_MINUS = GLFW.GLFW_KEY_MINUS;
    public static final int KEY_PERIOD = GLFW.GLFW_KEY_PERIOD;
    public static final int KEY_SLASH = GLFW.GLFW_KEY_SLASH;
    public static final int KEY_0 = GLFW.GLFW_KEY_0;
    public static final int KEY_1 = GLFW.GLFW_KEY_1;
    public static final int KEY_2 = GLFW.GLFW_KEY_2;
    public static final int KEY_3 = GLFW.GLFW_KEY_3;
    public static final int KEY_4 = GLFW.GLFW_KEY_4;
    public static final int KEY_5 = GLFW.GLFW_KEY_5;
    public static final int KEY_6 = GLFW.GLFW_KEY_6;
    public static final int KEY_7 = GLFW.GLFW_KEY_7;
    public static final int KEY_8 = GLFW.GLFW_KEY_8;
    public static final int KEY_9 = GLFW.GLFW_KEY_9;
    public static final int KEY_SEMICOLON = GLFW.GLFW_KEY_SEMICOLON;
    public static final int KEY_EQUAL = GLFW.GLFW_KEY_EQUAL;
    public static final int KEY_A = GLFW.GLFW_KEY_A;
    public static final int KEY_B = GLFW.GLFW_KEY_B;
    public static final int KEY_C = GLFW.GLFW_KEY_C;
    public static final int KEY_D = GLFW.GLFW_KEY_D;
    public static final int KEY_E = GLFW.GLFW_KEY_E;
    public static final int KEY_F = GLFW.GLFW_KEY_F;
    public static final int KEY_G = GLFW.GLFW_KEY_G;
    public static final int KEY_H = GLFW.GLFW_KEY_H;
    public static final int KEY_I = GLFW.GLFW_KEY_I;
    public static final int KEY_J = GLFW.GLFW_KEY_J;
    public static final int KEY_K = GLFW.GLFW_KEY_K;
    public static final int KEY_L = GLFW.GLFW_KEY_L;
    public static final int KEY_M = GLFW.GLFW_KEY_M;
    public static final int KEY_N = GLFW.GLFW_KEY_N;
    public static final int KEY_O = GLFW.GLFW_KEY_O;
    public static final int KEY_P = GLFW.GLFW_KEY_P;
    public static final int KEY_Q = GLFW.GLFW_KEY_Q;
    public static final int KEY_R = GLFW.GLFW_KEY_R;
    public static final int KEY_S = GLFW.GLFW_KEY_S;
    public static final int KEY_T = GLFW.GLFW_KEY_T;
    public static final int KEY_U = GLFW.GLFW_KEY_U;
    public static final int KEY_V = GLFW.GLFW_KEY_V;
    public static final int KEY_W = GLFW.GLFW_KEY_W;
    public static final int KEY_X = GLFW.GLFW_KEY_X;
    public static final int KEY_Y = GLFW.GLFW_KEY_Y;
    public static final int KEY_Z = GLFW.GLFW_KEY_Z;
    public static final int KEY_LEFT_BRACKET = GLFW.GLFW_KEY_LEFT_BRACKET;
    public static final int KEY_BACKSLASH = GLFW.GLFW_KEY_BACKSLASH;
    public static final int KEY_RIGHT_BRACKET = GLFW.GLFW_KEY_RIGHT_BRACKET;
    public static final int KEY_GRAVE_ACCENT = GLFW.GLFW_KEY_GRAVE_ACCENT;
    public static final int KEY_WORLD_1 = GLFW.GLFW_KEY_WORLD_1;
    public static final int KEY_WORLD_2 = GLFW.GLFW_KEY_WORLD_2;
    public static final int KEY_ESCAPE = GLFW.GLFW_KEY_ESCAPE;
    public static final int KEY_ENTER = GLFW.GLFW_KEY_ENTER;
    public static final int KEY_TAB = GLFW.GLFW_KEY_TAB;
    public static final int KEY_BACKSPACE = GLFW.GLFW_KEY_BACKSPACE;
    public static final int KEY_INSERT = GLFW.GLFW_KEY_INSERT;
    public static final int KEY_DELETE = GLFW.GLFW_KEY_DELETE;
    public static final int KEY_RIGHT = GLFW.GLFW_KEY_RIGHT;
    public static final int KEY_LEFT = GLFW.GLFW_KEY_LEFT;
    public static final int KEY_DOWN = GLFW.GLFW_KEY_DOWN;
    public static final int KEY_UP = GLFW.GLFW_KEY_UP;
    public static final int KEY_PAGE_UP = GLFW.GLFW_KEY_PAGE_UP;
    public static final int KEY_PAGE_DOWN = GLFW.GLFW_KEY_PAGE_DOWN;
    public static final int KEY_HOME = GLFW.GLFW_KEY_HOME;
    public static final int KEY_END = GLFW.GLFW_KEY_END;
    public static final int KEY_CAPS_LOCK = GLFW.GLFW_KEY_CAPS_LOCK;
    public static final int KEY_SCROLL_LOCK = GLFW.GLFW_KEY_SCROLL_LOCK;
    public static final int KEY_NUM_LOCK = GLFW.GLFW_KEY_NUM_LOCK;
    public static final int KEY_PRINT_SCREEN = GLFW.GLFW_KEY_PRINT_SCREEN;
    public static final int KEY_PAUSE = GLFW.GLFW_KEY_PAUSE;
    public static final int KEY_F1 = GLFW.GLFW_KEY_F1;
    public static final int KEY_F2 = GLFW.GLFW_KEY_F2;
    public static final int KEY_F3 = GLFW.GLFW_KEY_F3;
    public static final int KEY_F4 = GLFW.GLFW_KEY_F4;
    public static final int KEY_F5 = GLFW.GLFW_KEY_F5;
    public static final int KEY_F6 = GLFW.GLFW_KEY_F6;
    public static final int KEY_F7 = GLFW.GLFW_KEY_F7;
    public static final int KEY_F8 = GLFW.GLFW_KEY_F8;
    public static final int KEY_F9 = GLFW.GLFW_KEY_F9;
    public static final int KEY_F10 = GLFW.GLFW_KEY_F10;
    public static final int KEY_F11 = GLFW.GLFW_KEY_F11;
    public static final int KEY_F12 = GLFW.GLFW_KEY_F12;
    public static final int KEY_F13 = GLFW.GLFW_KEY_F13;
    public static final int KEY_F14 = GLFW.GLFW_KEY_F14;
    public static final int KEY_F15 = GLFW.GLFW_KEY_F15;
    public static final int KEY_F16 = GLFW.GLFW_KEY_F16;
    public static final int KEY_F17 = GLFW.GLFW_KEY_F17;
    public static final int KEY_F18 = GLFW.GLFW_KEY_F18;
    public static final int KEY_F19 = GLFW.GLFW_KEY_F19;
    public static final int KEY_F20 = GLFW.GLFW_KEY_F20;
    public static final int KEY_F21 = GLFW.GLFW_KEY_F21;
    public static final int KEY_F22 = GLFW.GLFW_KEY_F22;
    public static final int KEY_F23 = GLFW.GLFW_KEY_F23;
    public static final int KEY_F24 = GLFW.GLFW_KEY_F24;
    public static final int KEY_F25 = GLFW.GLFW_KEY_F25;
    public static final int KEY_KP_0 = GLFW.GLFW_KEY_KP_0;
    public static final int KEY_KP_1 = GLFW.GLFW_KEY_KP_1;
    public static final int KEY_KP_2 = GLFW.GLFW_KEY_KP_2;
    public static final int KEY_KP_3 = GLFW.GLFW_KEY_KP_3;
    public static final int KEY_KP_4 = GLFW.GLFW_KEY_KP_4;
    public static final int KEY_KP_5 = GLFW.GLFW_KEY_KP_5;
    public static final int KEY_KP_6 = GLFW.GLFW_KEY_KP_6;
    public static final int KEY_KP_7 = GLFW.GLFW_KEY_KP_7;
    public static final int KEY_KP_8 = GLFW.GLFW_KEY_KP_8;
    public static final int KEY_KP_9 = GLFW.GLFW_KEY_KP_9;
    public static final int KEY_KP_DECIMAL = GLFW.GLFW_KEY_KP_DECIMAL;
    public static final int KEY_KP_DIVIDE = GLFW.GLFW_KEY_KP_DIVIDE;
    public static final int KEY_KP_MULTIPLY = GLFW.GLFW_KEY_KP_MULTIPLY;
    public static final int KEY_KP_SUBTRACT = GLFW.GLFW_KEY_KP_SUBTRACT;
    public static final int KEY_KP_ADD = GLFW.GLFW_KEY_KP_ADD;
    public static final int KEY_KP_ENTER = GLFW.GLFW_KEY_KP_ENTER;
    public static final int KEY_KP_EQUAL = GLFW.GLFW_KEY_KP_EQUAL;
    public static final int KEY_LEFT_SHIFT = GLFW.GLFW_KEY_LEFT_SHIFT;
    public static final int KEY_LEFT_CONTROL = GLFW.GLFW_KEY_LEFT_CONTROL;
    public static final int KEY_LEFT_ALT = GLFW.GLFW_KEY_LEFT_ALT;
    public static final int KEY_LEFT_SUPER = GLFW.GLFW_KEY_LEFT_SUPER;
    public static final int KEY_RIGHT_SHIFT = GLFW.GLFW_KEY_RIGHT_SHIFT;
    public static final int KEY_RIGHT_CONTROL = GLFW.GLFW_KEY_RIGHT_CONTROL;
    public static final int KEY_RIGHT_ALT = GLFW.GLFW_KEY_RIGHT_ALT;
    public static final int KEY_RIGHT_SUPER = GLFW.GLFW_KEY_RIGHT_SUPER;
    public static final int KEY_MENU = GLFW.GLFW_KEY_MENU;

    public static final int MOUSE_BUTTON_1 = GLFW.GLFW_MOUSE_BUTTON_1 - 100;
    public static final int MOUSE_BUTTON_2 = GLFW.GLFW_MOUSE_BUTTON_2 - 100;
    public static final int MOUSE_BUTTON_3 = GLFW.GLFW_MOUSE_BUTTON_3 - 100;
    public static final int MOUSE_BUTTON_4 = GLFW.GLFW_MOUSE_BUTTON_4 - 100;
    public static final int MOUSE_BUTTON_5 = GLFW.GLFW_MOUSE_BUTTON_5 - 100;
    public static final int MOUSE_BUTTON_6 = GLFW.GLFW_MOUSE_BUTTON_6 - 100;
    public static final int MOUSE_BUTTON_7 = GLFW.GLFW_MOUSE_BUTTON_7 - 100;
    public static final int MOUSE_BUTTON_8 = GLFW.GLFW_MOUSE_BUTTON_8 - 100;

    private static final Map<Integer, String> KEY_DISPLAY_NAME_MAP = createKeyDisplayNameMap();


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

    private static Map<Integer, String> createKeyDisplayNameMap() {
        HashMap<Integer, String> map = new HashMap<>(64);
        map.put(0, "unknown");
        map.put(1, "escape");
        map.put(12, "minus");
        map.put(13, "equal");
        map.put(14, "backspace");
        map.put(15, "tab");
        map.put(26, "left.bracket");
        map.put(27, "right.bracket");
        map.put(28, "enter");
        map.put(29, "left.control");
        map.put(39, "semicolon");
        map.put(40, "apostrophe");
        map.put(41, "grave.accent");
        map.put(42, "left.shift");
        map.put(43, "backslash");
        map.put(51, "comma");
        map.put(52, "period");
        map.put(53, "slash");
        map.put(54, "right.shift");
        map.put(55, "keypad.multiply");
        map.put(56, "left.alt");
        map.put(57, "space");
        map.put(58, "caps.lock");
        map.put(69, "num.lock");
        map.put(70, "scroll.lock");
        map.put(74, "keypad.subtract");
        map.put(78, "keypad.add");
        map.put(83, "keypad.decimal");
        map.put(82, "keypad.0");
        map.put(79, "keypad.1");
        map.put(80, "keypad.2");
        map.put(81, "keypad.3");
        map.put(75, "keypad.4");
        map.put(76, "keypad.5");
        map.put(77, "keypad.6");
        map.put(71, "keypad.7");
        map.put(72, "keypad.8");
        map.put(73, "keypad.9");
        map.put(141, "keypad.equal");
        map.put(156, "keypad.enter");
        map.put(181, "keypad.divide");
        map.put(183, "print.screen");
        map.put(184, "right.alt");
        map.put(197, "pause");
        map.put(199, "home");
        map.put(200, "up");
        map.put(201, "page.up");
        map.put(203, "left");
        map.put(205, "right");
        map.put(207, "end");
        map.put(208, "down");
        map.put(209, "page.down");
        map.put(210, "insert");
        map.put(211, "delete");
        map.put(219, "left.win");
        map.put(220, "right.win");
        map.put(221, "menu");
        return map;
    }

    public static String getKeyDisplayName(int keyCode) {
        String name = KEY_DISPLAY_NAME_MAP.get(keyCode);
        if (name != null) {
            return StringUtils.translate("key.keyboard." + name);
        } else {
            return Keyboard.getKeyName(keyCode);
        }
    }
}
