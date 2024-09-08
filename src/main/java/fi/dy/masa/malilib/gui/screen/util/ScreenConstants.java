package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.*;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.button.*;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.widgets.InputBox;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.FontRenderer;
import net.minecraft.GuiScreen;

import java.util.function.BooleanSupplier;

public class ScreenConstants {
    private static final int commonButtonXFromRight = -200;
    private static final int commonButtonWidth = 115;
    private static final int hotKeyFirstButtonXFromRight = -300;
    private static final int shortHotkeyButtonWidth = 110;
    private static final int commonHotkeyButtonWidth = 155;
    private static final int keySettingButtonXFromRight = -140;
    private static final int keySettingButtonXWidth = 55;
    private static final int resetButtonXFromRight = -80;
    private static final int scrollBarXFromRight = -40;
    private static final int configToggleButtonXWidth = 40;
    private static final int nameX = 20;
    private static final int scrollBarHeight = 152;
    private static final int pullDownButtonXFromRight = -120;
    public static final int pageCapacity = 7;
    public static final int oneScroll = 3;
    public static final int confirmFlag = 0;
    public static final int commonButtonHeight = 20;
    public static final int commentedTextShift = 6;

    static int getYPos(int index, GuiScreen screen) {
        return screen.height / 6 + 22 * index + 32;
    }

    static <T extends ConfigBase<?> & IConfigDisplay> WidgetText getCommentedText(int index, T config, GuiScreen screen) {
        WidgetText widgetText = new WidgetText(nameX, getYPos(index, screen) + commentedTextShift, config.getConfigGuiDisplayName(), config.getConfigGuiDisplayComment(), config.getDisplayColor());
        ConfigType type = config.getType();
        int right;
        if (type == ConfigType.HOTKEY || type == ConfigType.TOGGLE) {
            right = screen.width + hotKeyFirstButtonXFromRight;
        } else {
            right = screen.width + commonButtonXFromRight;
        }
        widgetText.setCommentIntervalX(-nameX, right - nameX);
        return widgetText;
    }

    static <T extends IConfigResettable> ButtonGeneric getResetButton(int index, GuiScreen screen, T config, IButtonActionListener onPress) {
        return (ButtonGeneric) new ResetButton(screen.width + resetButtonXFromRight, getYPos(index, screen), config::isModified, onPress).tooltip(StringUtils.translate("manyLib.gui.button.reset"));
    }

    static <T extends ConfigBase<?> & IStringRepresentable> InputBox<T> getInputBox(int index, T config, GuiScreen screen) {
        return new InputBox<>(config, screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 2, 18);
    }

    static <T extends ConfigBase<T> & IStringRepresentable> InputBox<T> getInputBoxForSlideable(int index, T config, GuiScreen screen) {
        return new InputBox<>(config, screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18);
    }

    static InputBox<ConfigColor> getInputBoxForColor(int index, ConfigColor config, GuiScreen screen) {
        return new InputBox<>(config, screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18);
    }

    static ColorBoard getColorBoard(int index, ConfigColor configColor, GuiScreen screen) {
        return new ColorBoard(configColor, screen.width + commonButtonXFromRight + commonButtonWidth - 15, getYPos(index, screen) + 2, 16, 16);
    }

    static <T extends ConfigBase<T> & IConfigPeriodic & IConfigDisplay> PeriodicButton<T> getPeriodicButton(int index, T config, GuiScreen screen) {
        return new PeriodicButton<>(screen.width + commonButtonXFromRight, getYPos(index, screen), commonButtonWidth, commonButtonHeight, config);
    }

    static ButtonBase getHotkeyButton(int index, ConfigHotkey config, GuiScreen screen, IButtonActionListener onPress) {
        boolean isShort = config.getType() == ConfigType.TOGGLE;
        int xPos = isShort ? screen.width + hotKeyFirstButtonXFromRight + configToggleButtonXWidth + 5 : screen.width + hotKeyFirstButtonXFromRight;
        int width = isShort ? shortHotkeyButtonWidth : commonHotkeyButtonWidth;
        return ButtonGeneric.builder("", onPress).dimensions(xPos, getYPos(index, screen), width, commonButtonHeight).build();
    }

    static ButtonBase getJumpButton(int index, GuiScreen screen, IButtonActionListener onPress) {
        return ButtonGeneric.builder(StringUtils.translate("manyLib.gui.button.keySettings"), onPress).dimensions(screen.width + keySettingButtonXFromRight, getYPos(index, screen), keySettingButtonXWidth, commonButtonHeight).build();
    }

    static ButtonBase getConfigToggleButton(int index, GuiScreen screen, IButtonActionListener onPress) {
        return ButtonGeneric.builder("", onPress).dimensions(screen.width + hotKeyFirstButtonXFromRight, getYPos(index, screen), configToggleButtonXWidth, commonButtonHeight).build();
    }

    static <T extends ConfigBase<T> & IConfigSlideable & IConfigDisplay & IStringRepresentable> SliderButton<T> getSliderButton(int index, T config, GuiScreen screen) {
        return new SliderButton<>(screen.width + commonButtonXFromRight, getYPos(index, screen), commonButtonWidth - 20, commonButtonHeight, config);
    }

    static SlideableToggleButton getSlideableToggleButton(int index, boolean useSlider, GuiScreen screen, IButtonActionListener onPress) {
        return new SlideableToggleButton(screen.width + commonButtonXFromRight + commonButtonWidth - 15, getYPos(index, screen) + 2, useSlider, onPress);
    }

    static PullDownButton getPullDownButton(GuiScreen screen, String message, IButtonActionListener listener) {
        return new PullDownButton(screen.width + pullDownButtonXFromRight, 10, 100, 16, message, StringUtils.translate("manyLib.gui.button.other_mods"), listener);
    }

    static ScrollBar<?> getScrollBar(DefaultConfigScreen screen, int pageCapacity, int maxStatus) {
        return new ScrollBar<>(screen.width + scrollBarXFromRight, getYPos(0, screen), 8, scrollBarHeight, pageCapacity, maxStatus, screen);
    }

    static ButtonGeneric getResetAllButton(WidthAdder widthAdder, BooleanSupplier predicate, IButtonActionListener onPress) {
        ButtonBase buttonBase = new ResetButton(widthAdder.getWidth(), 30, predicate, onPress).tooltip(StringUtils.translate("manyLib.gui.button.reset_all"));
        widthAdder.addWidth(25);
        return (ButtonGeneric) buttonBase;
    }

    public static PeriodicButton<?> getSortButton(GuiScreen screen, WidthAdder widthAdder, ConfigEnum<SortCategory> sortCategory, IButtonActionListener onPress) {
        int stringWidth = getMaxStringWidth(screen.fontRenderer, sortCategory);
        int width = widthAdder.getWidth();
        widthAdder.addWidth(stringWidth + 15);
        return new PeriodicButton<>(width, 30, stringWidth + 10, commonButtonHeight, sortCategory, onPress);
    }

    private static int getMaxStringWidth(FontRenderer fontRenderer, ConfigEnum<SortCategory> sortCategory) {
        int maxWidth = 0;
        for (SortCategory value : SortCategory.values()) {
            ConfigEnum<SortCategory> temp = new ConfigEnum<>(sortCategory.getName(), value);
            int stringWidth = fontRenderer.getStringWidth(temp.getDisplayText());
            if (stringWidth > maxWidth) {
                maxWidth = stringWidth;
            }
        }
        return maxWidth;
    }

    public static <T extends GuiScreen & Searchable> SearchField getSearchButton(T screen) {
        return new SearchField(23, 57, screen.width - 95, 13, screen);// TODO
    }
}
