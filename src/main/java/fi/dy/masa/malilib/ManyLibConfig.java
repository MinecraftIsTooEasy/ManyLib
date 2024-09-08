package fi.dy.masa.malilib;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.EnumChatFormatting;
import net.xiaoyu233.fml.FishModLoader;

import java.util.ArrayList;
import java.util.List;

import static fi.dy.masa.malilib.ManyLib.MOD_ID;

public class ManyLibConfig extends SimpleConfigs {
    private static final ManyLibConfig Instance;
    public static final List<ConfigHotkey> hotkeys;
    public static final List<ConfigBase<?>> values;//openValueMenu
    public static final ConfigHotkey OpenConfigMenu = new ConfigHotkey("manyLib.openMenu", "M,C", "按下打开ManyLib数值配置页面");
    public static final ConfigHotkey OpenModMenu = new ConfigHotkey("manyLib.openModMenu", KeybindMulti.fromStorageString("M", KeybindSettings.RELEASE), "按下打开使用ManyLib的模组的菜单");
    public static final ConfigBoolean HideConfigButton = new ConfigBoolean("manyLib.hideValueButton", false, "隐藏在游戏主界面以及暂停界面的数值配置按钮");
    public static final ConfigInteger HoverTextYLevel = new ConfigInteger("manyLib.hoverInfoY", 70, 0, 512, false, "从屏幕底部往上数");
    public static final ConfigColor HighlightColor = new ConfigColor("高亮颜色", "#77777777");
    public static final ConfigEnum<EnumChatFormatting> TitleFormat = new ConfigEnum<>("标题格式", EnumChatFormatting.WHITE);

//    public static final ConfigDouble testDoubleBox = new ConfigDouble("Double文本框", 0.0d, -1.0d, 1.0d, false, "测试");
//    public static final ConfigDouble testDoubleSlider = new ConfigDouble("Double滑块", 0.0d, -1.0d, 1.0d, true, "测试");
//    public static final ConfigInteger testIntegerBox = new ConfigInteger("Integer文本框", 0, -2, 2, false, "测试");
//    public static final ConfigInteger testIntegerSlider = new ConfigInteger("Integer滑块", 0, -2, 2, true, "测试");
//    public static final ConfigString testString = new ConfigString("String文本框", "文本", "测试");

//    public static final ConfigColor testColor = new ConfigColor("颜色", "#C03030F0");

    //    public static final ConfigStringList testStringList = new ConfigStringList("StringList", List.of("11", "22"), "测试");
    public ManyLibConfig(String name, List<ConfigHotkey> hotkeys, List<?> values) {
        super(name, hotkeys, values);
    }

    static {
        values = new ArrayList<>();
        values.addAll(List.of(HideConfigButton, HoverTextYLevel, HighlightColor, TitleFormat));
        hotkeys = List.of(OpenConfigMenu, OpenModMenu);
        Instance = new ManyLibConfig(MOD_ID, hotkeys, values);
    }

    public static ManyLibConfig getInstance() {
        return Instance;
    }

    @Override
    public String getMenuComment() {
        return StringUtils.translate("config.menu.comment." + this.name, OpenConfigMenu.getDisplayText());
    }

    @Override
    public void load() {
        super.load();
        if (FishModLoader.hasMod("modmenu")) {
            HideConfigButton.setBooleanValue(true);
        }
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
