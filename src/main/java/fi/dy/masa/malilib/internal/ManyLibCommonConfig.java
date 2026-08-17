package fi.dy.masa.malilib.internal;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.config.SimpleConfigs;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.core.Side;

import java.util.List;

public class ManyLibCommonConfig extends SimpleConfigs {
    private static final ManyLibCommonConfig Instance;

    public static final ConfigDouble testDoubleBox = new ConfigDouble("Double文本框", 0.0d, -1.0d, 1.0d, false, "测试");
    public static final ConfigDouble testDoubleSlider = new ConfigDouble("Double滑块", 0.0d, -1.0d, 1.0d, true, "测试");
    public static final ConfigInteger testIntegerBox = new ConfigInteger("Integer文本框", 0, Integer.MIN_VALUE, Integer.MAX_VALUE, false, "测试");
    public static final ConfigInteger testIntegerSlider = new ConfigInteger("Integer滑块", 0, -2, 2, true, "测试");
    public static final ConfigString testString = new ConfigString("String文本框", "文本", "测试");

    public static final ConfigColor testColor = new ConfigColor("颜色", "#C03030F0");

    public static final ConfigStringList testStringList = new ConfigStringList("StringList", List.of("11", "22"), "测试");

    public ManyLibCommonConfig(String modId, List<ConfigBase<?>> values) {
        super(modId, values);
    }

    public static ManyLibCommonConfig getInstance() {
        return Instance;
    }

    @Override
    public Side getSide() {
        return Side.COMMON;
    }

    static {
        Instance = new ManyLibCommonConfig(
                ManyLib.MOD_ID,
                List.of(
                        testDoubleBox,
                        testDoubleSlider,
                        testIntegerBox,
                        testIntegerSlider,
                        testString,
                        testColor,
                        testStringList
                )
        );
    }
}
