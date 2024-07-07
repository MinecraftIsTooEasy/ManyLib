package fi.dy.masa.malilib;

import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.gui.screen.HotKeyMenu;
import fi.dy.masa.malilib.gui.screen.ValueMenu;
import fi.dy.masa.malilib.util.StringUtils;
import org.lwjgl.input.Keyboard;

import java.util.List;

import static fi.dy.masa.malilib.ManyLib.MOD_ID;

public class ManyLibConfig extends SimpleConfigs {
    private static final ManyLibConfig Instance;
    public static final List<ConfigHotkey> hotkeys;
    public static final List<ConfigBase<?>> values;
    public static final ConfigHotkey openValueMenu = new ConfigHotkey("manyLib.openValueMenu", Keyboard.KEY_M, "按下打开ManyLib数值配置页面");
    public static final ConfigHotkey openHotkeyMenu = new ConfigHotkey("manyLib.openHotkeyMenu", Keyboard.KEY_P, "按下打开ManyLib按键配置页面");
    public static final ConfigBoolean hideValueButton = new ConfigBoolean("manyLib.hideValueButton", false, "隐藏在游戏主界面以及暂停界面的数值配置按钮");
    public static final ConfigInteger hoverTextYLevel = new ConfigInteger("manyLib.hoverInfoY", 60, 0, 512, false, "从屏幕底部往上数");

//    public static final ConfigDouble testDoubleBox = new ConfigDouble("Double文本框", 0.0d, -1.0d, 1.0d, false, "测试");
//    public static final ConfigDouble testDoubleSlider = new ConfigDouble("Double滑块", 0.0d, -1.0d, 1.0d, true, "测试");
//    public static final ConfigInteger testIntegerBox = new ConfigInteger("Integer文本框", 0, -2, 2, false, "测试");
//    public static final ConfigInteger testIntegerSlider = new ConfigInteger("Integer滑块", 0, -2, 2, true, "测试");
//    public static final ConfigString testString = new ConfigString("String文本框", "文本", "测试");

//    public static final ConfigColor testColor = new ConfigColor("颜色", "#C03030F0");

    //    public static final ConfigStringList testStringList = new ConfigStringList("StringList", List.of("11", "22"), "测试");
    public ManyLibConfig(String name, List<ConfigHotkey> hotkeys, List<ConfigBase<?>> values) {
        super(name, hotkeys, values);
    }

    static {
        hotkeys = List.of(openValueMenu, openHotkeyMenu);
        values = List.of(hideValueButton, hoverTextYLevel);
//        values = List.of(hideValueButton, testColor);

        openValueMenu.setHotKeyPressCallBack(minecraft -> minecraft.displayGuiScreen(ValueMenu.getInstance(null)));
        openHotkeyMenu.setHotKeyPressCallBack(minecraft -> minecraft.displayGuiScreen(HotKeyMenu.getInstance(null)));

        Instance = new ManyLibConfig(MOD_ID, hotkeys, values);
    }

    public static ManyLibConfig getInstance() {
        return Instance;
    }

    @Override
    public String getValuesComment() {
        return StringUtils.translateParams("config.value.comment." + this.name, openValueMenu.getKeyName());
    }
}
