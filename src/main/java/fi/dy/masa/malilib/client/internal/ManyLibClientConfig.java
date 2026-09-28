package fi.dy.masa.malilib.client.internal;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.client.config.ClientConfigFactory;
import fi.dy.masa.malilib.client.config.ClientSimpleConfigs;
import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.client.input.KeybindMulti;
import fi.dy.masa.malilib.client.input.KeybindSettings;
import fi.dy.masa.malilib.config.options.*;
import net.minecraft.EnumChatFormatting;

import java.util.List;

import static fi.dy.masa.malilib.ManyLib.MOD_ID;

public class ManyLibClientConfig extends ClientSimpleConfigs {
    private static final ManyLibClientConfig Instance;

    public static final List<ConfigHotkey> hotkeys;

    public static final ConfigHotkey OpenConfigMenu = new ConfigHotkey("manyLib.openMenu", "M,C", "打开ManyLib自身配置页面");
    public static final ConfigHotkey OpenModMenu = new ConfigHotkey("manyLib.openModMenu", KeybindMulti.fromStorageString("M", KeybindSettings.RELEASE), "打开ManyLib全部用户的菜单");
    public static final ConfigHotkey SearchAny = new ConfigHotkey("manyLib.searchAny", "M,A", "ManyLib全局配置搜索");
    public static final ConfigHotkey IgnoredKeys = ClientConfigFactory.ofHotkey("manyLib.ignoredKeys");


    public static final List<ConfigBase<?>> values;

    public static final ConfigBoolean HideConfigButton = new ConfigBoolean("manyLib.hideValueButton", false, "隐藏在游戏主界面以及暂停界面的数值配置按钮");
    public static final ConfigBoolean AutoSaveLoad = new ConfigBoolean("manyLib.autoSaveLoad", true, "(对所有模组有效)进入世界时读取配置文件, 退出时保存");
    public static final ConfigBoolean TranslationFallback = new ConfigBoolean("manyLib.translationFallback", true, "翻译时使用备用文本");
    public static final ConfigInteger ActionBarShift = new ConfigInteger("manyLib.actionBarShift", 70, 0, 512, false, "从屏幕底部往上数");
    public static final ConfigColor HighlightColor = new ConfigColor("manyLib.highlightColor", "#77777777");
    public static final ConfigEnum<EnumChatFormatting> TitleFormat = new ConfigEnum<>("manyLib.titleFormat", EnumChatFormatting.WHITE);

    public ManyLibClientConfig() {
        super(MOD_ID, values, hotkeys);
    }

    static {
        values = List.of(HideConfigButton, AutoSaveLoad, TranslationFallback, ActionBarShift, HighlightColor, TitleFormat);
        hotkeys = List.of(OpenConfigMenu, OpenModMenu, SearchAny, IgnoredKeys);
        Instance = new ManyLibClientConfig();
    }

    public static ManyLibClientConfig getInstance() {
        return Instance;
    }

    public static class Debug {
        public static final ConfigBoolean INPUT_CANCELLATION_DEBUG = new ConfigBoolean("inputCancellationDebugging", false, "When enabled, then the cancellation reason/source\nfor inputs (keyboard and mouse) is printed out");
        public static final ConfigBoolean KEYBIND_DEBUG = new ConfigBoolean("keybindDebugging", false, "When enabled, key presses and held keys are\nprinted to the game console (and the action bar, if enabled)");
        public static final ConfigBoolean KEYBIND_DEBUG_ACTIONBAR = new ConfigBoolean("keybindDebuggingIngame", true, "If enabled, then the messages from 'keybindDebugging'\nare also printed to the in-game action bar");
        public static final ConfigBoolean MOUSE_SCROLL_DEBUG = new ConfigBoolean("mouseScrollDebug", false, "If enabled, some debug values from mouse scrolling\nare printed to the game console/log");

        public static final ImmutableList<ConfigBase<?>> OPTIONS = ImmutableList.of(
                INPUT_CANCELLATION_DEBUG,
                KEYBIND_DEBUG,
                KEYBIND_DEBUG_ACTIONBAR,
                MOUSE_SCROLL_DEBUG
        );
    }
}
