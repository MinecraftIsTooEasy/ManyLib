package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.*;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.button.*;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusScreen;
import fi.dy.masa.malilib.gui.widgets.*;
import fi.dy.masa.malilib.gui.wrappers.TextFieldWrapper;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.FontRenderer;
import net.minecraft.GuiScreen;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

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
    private static final int pullDownButtonXFromRight = -120;
    public static final int oneScroll = 3;
    public static final int confirmFlag = 0;
    public static final int commonButtonHeight = 20;
    public static final int commentedTextShift = 6;

    public static int getYPos(int index, GuiScreen screen) {
        int capacity;
        if (screen instanceof StatusScreen statusScreen) {
            capacity = statusScreen.getMaxCapacity();
        } else {
            capacity = 7;
        }
        return screen.height / 6 + 22 * index + 32 - capacity * 22 + 7 * 22;
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
        ButtonGeneric buttonGeneric = new ResetButton(screen.width + resetButtonXFromRight, getYPos(index, screen), config::isModified, onPress);
        buttonGeneric.setHoverStrings(StringUtils.translate("manyLib.gui.button.reset"));
        return buttonGeneric;
    }

    static <T extends ConfigBase<?> & IStringRepresentable> TextFieldWrapper<WidgetTextField> getTextFieldWrapper(int index, T config, GuiScreen screen) {
        return new TextFieldWrapper<>(new WidgetTextField(screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 2, 18), textField -> config.setValueFromString(textField.getText()));
    }

    static <T extends ConfigBase<T> & IStringRepresentable> TextFieldWrapper<WidgetTextField> getWrapperForSlideable(int index, T config, Supplier<String> setStringOnFinish, GuiScreen screen) {
        ConfigType type = config.getType();
        WidgetTextField widgetTextField;
        if (type == ConfigType.DOUBLE) {
            widgetTextField = new WidgetTextFieldDouble(screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18);
        } else {
            widgetTextField = new WidgetTextFieldInteger(screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18);
        }
        return new TextFieldWrapper<>(widgetTextField, new ITextFieldListener<>() {
            @Override
            public void onTextChange(WidgetTextField textField) {
                config.setValueFromString(textField.getText());
            }

            @Override
            public void onFinish(WidgetTextField textField) {
                textField.setText(setStringOnFinish.get());
            }
        });
    }

    static TextFieldWrapper<WidgetTextFieldColor> getWrapperForColor(int index, ConfigColor config, GuiScreen screen) {
        return new TextFieldWrapper<>(new WidgetTextFieldColor(screen.width + commonButtonXFromRight + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18), textField -> config.setValueFromString(textField.getText()));
    }

    static ColorBoard getColorBoard(int index, ConfigColor configColor, GuiScreen screen) {
        return new ColorBoard(configColor, screen.width + commonButtonXFromRight + commonButtonWidth - 15, getYPos(index, screen) + 2, 16, 16);
    }

    static <T extends ConfigBase<T> & IConfigPeriodic & IConfigDisplay> PeriodicButton<T> getPeriodicButton(int index, T config, GuiScreen screen) {
        return new PeriodicButton<>(screen.width + commonButtonXFromRight, getYPos(index, screen), commonButtonWidth, commonButtonHeight, config);
    }

    static ButtonBase getCommonButton(int index, String content, GuiScreen screen, IButtonActionListener onPress) {
        return ButtonGeneric.builder(content, onPress).dimensions(screen.width + commonButtonXFromRight, getYPos(index, screen), commonButtonWidth, commonButtonHeight).build();
    }

    static ButtonBase getHotkeyButton(int index, ConfigHotkey config, GuiScreen screen, IButtonActionListener onPress) {
        boolean isShort = config.getType() == ConfigType.TOGGLE;
        int xPos = screen.width + hotKeyFirstButtonXFromRight;
        int width = isShort ? shortHotkeyButtonWidth : commonHotkeyButtonWidth;
        return ButtonGeneric.builder("", onPress).dimensions(xPos, getYPos(index, screen), width, commonButtonHeight).build();
    }

    static ButtonBase getJumpButton(int index, GuiScreen screen, IButtonActionListener onPress) {
        return ButtonGeneric.builder(StringUtils.translate("manyLib.gui.button.keySettings"), onPress).dimensions(screen.width + keySettingButtonXFromRight, getYPos(index, screen), keySettingButtonXWidth, commonButtonHeight).build();
    }

    static <T extends ConfigBase<T> & IConfigSlideable & IConfigDisplay & IStringRepresentable> SliderButton<T> getSliderButton(int index, T config, GuiScreen screen) {
        return new SliderButton<>(screen.width + commonButtonXFromRight, getYPos(index, screen), commonButtonWidth - 20, commonButtonHeight, config);
    }

    static SlideableToggleButton getSlideableToggleButton(int index, boolean useSlider, GuiScreen screen, IButtonActionListener onPress) {
        return new SlideableToggleButton(screen.width + commonButtonXFromRight + commonButtonWidth - 15, getYPos(index, screen) + 2, useSlider, onPress);
    }

    static ButtonBase getConfigToggleButton(int index, GuiScreen screen, IButtonActionListener onPress) {
        return ButtonGeneric.builder("", onPress).dimensions(screen.width + hotKeyFirstButtonXFromRight + shortHotkeyButtonWidth + 5, getYPos(index, screen), configToggleButtonXWidth, commonButtonHeight).build();
    }

    public static PullDownButton<DropDownEntry> getPullDownButton(GuiScreen screen, IConfigHandler configInstance) {
        return new PullDownButton<>(screen.width + pullDownButtonXFromRight, 10, 100, 16, configInstance.getName(), StringUtils.translate("manyLib.gui.button.other_mods"),
                (index, startX, startY, present, content, listener) -> new DropDownEntry(startX, startY + index * DropDownEntry.HeightUnit, present, content, listener));
    }

    public static <T extends GuiScreen & StatusScreen> ScrollBar<?> getScrollBar(T screen, int pageCapacity, int maxStatus) {
        return new ScrollBar<>(screen.width + scrollBarXFromRight, getYPos(0, screen), 8, 22 * screen.getMaxCapacity() - 2, pageCapacity, maxStatus, screen);
    }

    public static ButtonGeneric getResetAllButton(WidthAdder widthAdder, BooleanSupplier predicate, IButtonActionListener onPress) {
        ButtonGeneric resetButton = new ResetButton(widthAdder.getWidth(), 30, predicate, onPress);
        resetButton.setHoverStrings(StringUtils.translate("manyLib.gui.button.reset_all"));
        widthAdder.addWidth(25);
        return resetButton;
    }

    public static PeriodicButton<?> getSortButton(GuiScreen screen, WidthAdder widthAdder, int y, ConfigEnum<SortCategory> sortCategory, IButtonActionListener onPress) {
        int stringWidth = getMaxStringWidth(screen.fontRenderer, sortCategory);
        int width = widthAdder.getWidth();
        widthAdder.addWidth(stringWidth + 15);
        return new PeriodicButton<>(width, y, stringWidth + 10, commonButtonHeight, sortCategory, onPress);
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

    public static <T extends GuiScreen & Searchable & StatusScreen> SearchField getSearchButton(T screen) {
        return new SearchField(23, getSearchFieldY(screen), screen.width - 95, 13, screen);
    }

    static <T extends GuiScreen & Searchable & StatusScreen> int getSearchFieldY(T screen) {
        return 35 - 22 * screen.getMaxCapacity() + 22 * 8;
    }
}
