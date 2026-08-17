package fi.dy.masa.malilib.client.gui.screen.util;

import fi.dy.masa.malilib.client.config.options.ConfigHotkey;
import fi.dy.masa.malilib.client.feature.SortCategory;
import fi.dy.masa.malilib.client.gui.button.*;
import fi.dy.masa.malilib.client.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.interfaces.ITextFieldListener;
import fi.dy.masa.malilib.client.gui.screen.interfaces.Searchable;
import fi.dy.masa.malilib.client.gui.screen.interfaces.StatusElement;
import fi.dy.masa.malilib.client.gui.widgets.*;
import fi.dy.masa.malilib.client.gui.wrappers.TextFieldWrapper;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.ConfigTypes;
import fi.dy.masa.malilib.config.interfaces.IConfigCyclic;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.config.interfaces.IConfigSlideable;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.FontRenderer;
import net.minecraft.GuiScreen;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;


/**
 * This is shit
 */
public class ScreenConstants {
    public static final int scrollBarXFromRight = -40;
    public static final int commonButtonHeight = 20;
    public static final int commentedTextShift = 7;
    public static final int listEntryHeight = 22;
    public static final int listEntryMargin = 1;
    private static final int commonButtonXFromRight = -200;
    private static final int commonButtonWidth = 115;
    private static final int hotKeyFirstButtonXFromRight = -300;
    private static final int shortHotkeyButtonWidth = 110;
    private static final int commonHotkeyButtonWidth = 155;
    private static final int keySettingButtonXFromRight = -140;
    private static final int keySettingButtonXWidth = 55;
    private static final int resetButtonXFromRight = -80;
    private static final int configToggleButtonXWidth = 40;
    private static final int nameX = 20;
    private static final int modLinkButtonXFromRight = -120;
    private static final int searchBoxHeight = 13;
    private static final int scrollBarWidth = 8;
    private static final int scrollBarMargin = 1;

    public static final int confirmFlag = 0;
    public static final int oneScroll = 3;
    public static final int configScreenCapacity = 7;
    public static final int modMenuCapacity = 7;

    public static int getWidgetListViewY(GuiScreen screen) {
        return screen.height / 6 + 32;
    }

    public static WidgetText getTitle(String content) {
        return WidgetText.of(content).position(40, 15);
    }

    public static void placeCommentedText(WidgetBase parent, ConfigBase<?> config, WidgetText text) {
        text.setPosition(nameX, parent.getY() + commentedTextShift);
        ConfigType type = config.getType();
        int right;
        if (type == ConfigTypes.HOTKEY || type == ConfigTypes.TOGGLE) {
            right = parent.getWidth() + hotKeyFirstButtonXFromRight;
        } else {
            right = parent.getWidth() + commonButtonXFromRight;
        }
        text.getTooltipRange().setBounds(0, parent.getY(), right, listEntryHeight);
    }

    public static void placeResetButton(WidgetBase parent, ButtonGeneric button) {
        button.setPosition(parent.getWidth() + resetButtonXFromRight, parent.getY() + listEntryMargin);
    }

    public static void placeTextFieldWrapper(WidgetBase parent, TextFieldWrapper<?> wrapper) {
        wrapper.setPosition(parent.getWidth() + commonButtonXFromRight + 2, parent.getY() + 2 * listEntryMargin);
    }

    public static <T extends ConfigBase<?> & IStringRepresentable> TextFieldWrapper<WidgetTextField> getTextFieldWrapper(T config) {
        return new TextFieldWrapper<>(
                new WidgetTextField(0, 0, commonButtonWidth - 2, 18),
                textField -> config.setValueFromString(textField.getText())
        );
    }

    public static <T extends ConfigBase<T> & IStringRepresentable> TextFieldWrapper<WidgetTextField> getWrapperForSlideable(T config, Supplier<String> setStringOnFinish, WidgetBase parent) {
        ConfigType type = config.getType();
        WidgetTextField widgetTextField;
        if (type == ConfigTypes.DOUBLE) {
            widgetTextField = new WidgetTextFieldDouble(0, 0, commonButtonWidth - 22, 18);
        } else {
            widgetTextField = new WidgetTextFieldInteger(0, 0, commonButtonWidth - 22, 18);
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

    public static TextFieldWrapper<WidgetTextFieldColor> getWrapperForColor(ConfigColor config) {
        return new TextFieldWrapper<>(
                new WidgetTextFieldColor(0, 0, commonButtonWidth - 22, 18),
                textField -> config.setValueFromString(textField.getText())
        );
    }

    public static void placeColorBoard(WidgetBase parent, ColorBoard colorBoard) {
        colorBoard.setPosition(parent.getWidth() + commonButtonXFromRight + commonButtonWidth - 15, parent.getY() + 3 * listEntryMargin);
    }

    public static <T extends ConfigBase<T> & IConfigCyclic> CycleButton<T> getCycleButton(T config) {
        return new CycleButton<>(0, 0, commonButtonWidth, commonButtonHeight, config);
    }

    public static void placeCommonButton(WidgetBase parent, ButtonBase button) {
        button.setPosition(parent.getWidth() + commonButtonXFromRight, parent.getY() + listEntryMargin);
    }

    public static ButtonBase getCommonButton(String content, IButtonActionListener onPress) {
        return ButtonGeneric.builder(content, onPress).size(commonButtonWidth, commonButtonHeight).build();
    }

    public static void placeHotkeyButton(WidgetBase parent, ButtonBase button) {
        button.setPosition(parent.getWidth() + hotKeyFirstButtonXFromRight, parent.getY() + listEntryMargin);
    }

    public static ButtonBase getHotkeyButton(ConfigHotkey config, IButtonActionListener onPress) {
        ConfigType type = config.getType();
        boolean isShort = type == ConfigTypes.TOGGLE;
        int width = isShort ? shortHotkeyButtonWidth : commonHotkeyButtonWidth;
        return ButtonGeneric.builder("", onPress).dimensions(0, 0, width, commonButtonHeight).build();
    }

    public static void placeKeySettingButton(WidgetBase parent, ButtonBase button) {
        button.setPosition(parent.getWidth() + keySettingButtonXFromRight, parent.getY() + listEntryMargin);
    }

    public static ButtonBase getKeySettingButton(IButtonActionListener onPress) {
        return ButtonGeneric.builder(ScreenText.KEY_SETTINGS.translate(), onPress).size(keySettingButtonXWidth, commonButtonHeight).build();
    }

    public static <T extends ConfigBase<T> & IConfigSlideable & IStringRepresentable> SliderButton<T> getSliderButton(T config) {
        return new SliderButton<>(0, 0, commonButtonWidth - 20, commonButtonHeight, config);
    }

    public static void placeSlideableToggleButton(WidgetBase parent, SlideableToggleButton button) {
        button.setPosition(parent.getWidth() + commonButtonXFromRight + commonButtonWidth - 15, parent.getY() + 3 * listEntryMargin);
    }

    public static void placeConfigToggleButton(WidgetBase parent, ButtonBase button) {
        button.setPosition(parent.getWidth() + hotKeyFirstButtonXFromRight + shortHotkeyButtonWidth + 5, parent.getY() + listEntryMargin);
    }

    public static ButtonBase getConfigToggleButton(IButtonActionListener onPress) {
        return ButtonGeneric.builder("", onPress)
                .size(configToggleButtonXWidth, commonButtonHeight)
                .build();
    }

    public static ModLinkButton getModLinkButton(GuiScreen screen, IConfigHandler configInstance) {
        return new ModLinkButton(screen.width + modLinkButtonXFromRight, 10, 100, 16, configInstance.getModId(), ScreenText.OTHER_MODS.translate());
    }

    public static WidgetScrollBar getScrollBar(int x, int y, StatusElement statusElement) {
        int pageCapacity = statusElement.getPageCapacity();
        return new WidgetScrollBar(x, y, 8, listEntryHeight * pageCapacity - 2, statusElement);
    }

    public static WidgetScrollBar getScrollBar(WidgetListView<?> widgetListView) {
        return new WidgetScrollBar(
                widgetListView.getX() + widgetListView.getWidth() + scrollBarXFromRight,
                widgetListView.getY() + scrollBarMargin,
                scrollBarWidth,
                widgetListView.getHeight() - scrollBarMargin * 2,
                widgetListView
        );
    }

    public static ButtonGeneric getResetAllButton(WidthAdder widthAdder, BooleanSupplier predicate, IButtonActionListener onPress) {
        ButtonGeneric resetButton = new ResetButton(widthAdder.getWidth(), 30, predicate, onPress);
        resetButton.setHoverStrings(ScreenText.RESET_ALL_BUTTON.translate());
        widthAdder.addWidth(25);
        return resetButton;
    }

    public static CycleButton<?> getSortButton(GuiScreen screen, WidthAdder widthAdder, int y, ConfigEnum<SortCategory> sortCategory, IButtonActionListener onPress) {
        int stringWidth = getMaxStringWidth(screen.fontRenderer, sortCategory);
        int width = widthAdder.getWidth();
        widthAdder.addWidth(stringWidth + 15);
        return new CycleButton<>(width, y, stringWidth + 10, commonButtonHeight, sortCategory, onPress);
    }

    private static int getMaxStringWidth(FontRenderer fontRenderer, ConfigEnum<SortCategory> sortCategory) {
        int maxWidth = 0;
        for (SortCategory value : SortCategory.values()) {
            ConfigEnum<SortCategory> temp = new ConfigEnum<>(sortCategory.getName(), value);
            int stringWidth = fontRenderer.getStringWidth(ConfigDisplayApi.getButtonText(temp));
            if (stringWidth > maxWidth) {
                maxWidth = stringWidth;
            }
        }
        return maxWidth;
    }

    public static <T extends WidgetListView<?> & Searchable> WidgetSearchField getSearchField(T widgetListView) {
        return getSearchField(widgetListView, widgetListView);
    }

    public static WidgetSearchField getSearchField(Searchable searchable, WidgetListView<?> widgetListView) {
        return new WidgetSearchField(
                widgetListView.getX() + 23,
                widgetListView.getY() - 18,
                widgetListView.getWidth() - 95,
                searchBoxHeight,
                searchable
        );
    }

    public static void setWidgetListViewDimensions(GuiScreen screen, WidgetListView<?> widgetListView) {
        widgetListView.setDimensions(
                0,
                screen.height / 6 + 32,
                screen.width,
                widgetListView.getEntryHeight() * widgetListView.getPageCapacity()
        );
    }
}
