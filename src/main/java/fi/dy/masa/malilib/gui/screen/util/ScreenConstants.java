package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.interfaces.*;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigEnum;
import fi.dy.masa.malilib.gui.button.*;
import fi.dy.masa.malilib.gui.button.interfaces.CommentedText;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import fi.dy.masa.malilib.gui.button.interfaces.ISliderButton;
import fi.dy.masa.malilib.gui.screen.ValueScreen;
import net.minecraft.GuiScreen;
import net.minecraft.I18n;

public class ScreenConstants {
    public static final int resetAllButtonID = 201;
    public static final int sortButtonID = 202;
    public static final int scrollBarID = 203;
    public static final int searchButtonID = 204;
    public static final int pullDownButtonID = 205;
    private static final int commonButtonX = -190;
    private static final int commonButtonWidth = 105;
    private static final int resetButtonX = -80;
    private static final int scrollBarX = -40;
    private static final int nameX = 20;
    private static final int scrollBarHeight = 152;
    private static final int pullDownButtonX = -120;
    public static final int pageCapacity = 7;
    public static final int oneScroll = 3;
    public static final int confirmFlag = 0;

    static int getYPos(int index, GuiScreen screen) {
        return screen.height / 6 + 22 * index + 32;
    }

    static CommentedText getCommentedText(int index, IConfigBase config, GuiScreen screen) {
        CommentedText commentedText = new CommentedText(nameX, getYPos(index, screen) + 5, config.getConfigGuiDisplayName(), config.getConfigGuiDisplayComment(), screen.fontRenderer);
        commentedText.setCommentBoundX(screen.width + commonButtonX - nameX);
        return commentedText;
    }

    static ResetButton getResetButton(int index, GuiScreen screen) {
        return new ResetButton(0, screen.width + resetButtonX, getYPos(index, screen));
    }

    static <T extends ConfigBase<?> & IStringRepresentable> InputBox<T> getInputBox(int index, T config, GuiScreen screen) {
        return new InputBox<>(config, screen.fontRenderer, screen.width + commonButtonX + 2, getYPos(index, screen) + 1, commonButtonWidth - 2, 18);
    }

    static <T extends ConfigBase<T> & IStringRepresentable> InputBox<T> getInputBoxForSlideable(int index, T config, GuiScreen screen) {
        return new InputBox<>(config, screen.fontRenderer, screen.width + commonButtonX + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18);
    }

    static InputBox<ConfigColor> getInputBoxForColor(int index, ConfigColor config, GuiScreen screen) {
        return new InputBox<>(config, screen.fontRenderer, screen.width + commonButtonX + 2, getYPos(index, screen) + 1, commonButtonWidth - 22, 18);
    }

    static ColorBoard getColorBoard(int index, ConfigColor configColor, GuiScreen screen) {
        return new ColorBoard(configColor, screen.width + commonButtonX + commonButtonWidth - 15, getYPos(index, screen) + 1, 18, 18);
    }

    static <T extends ConfigBase<T> & IConfigPeriodic & IConfigDisplay> IButtonPeriodic getPeriodicButton(int index, T config, GuiScreen screen) {
        return new PeriodicButton(0, screen.width + commonButtonX, getYPos(index, screen), commonButtonWidth, 20, config);
    }

    static <T extends ConfigBase<T> & IConfigSlideable & IConfigDisplay> ISliderButton getSliderButton(int index, T config, GuiScreen screen) {
        return new SliderButton<>(0, screen.width + commonButtonX, getYPos(index, screen), commonButtonWidth - 20, 20, config);
    }

    static SlideableToggleButton getToggleButton(int index, boolean useSlider, GuiScreen screen) {
        return new SlideableToggleButton(0, screen.width + commonButtonX + commonButtonWidth - 15, getYPos(index, screen) + 2, useSlider);
    }

    public static PullDownButton getPullDownButton(GuiScreen screen) {
        return new PullDownButton(pullDownButtonID, screen.width + pullDownButtonX, 10, 100, 20, I18n.getString("manyLib.gui.button.other_mods"));
    }

    public static ScrollBar getScrollBar(GuiScreen screen, int pageCapacity, int maxStatus) {
        return new ScrollBar(scrollBarID, screen.width + scrollBarX, getYPos(0, screen), 8, scrollBarHeight, pageCapacity, maxStatus, (ValueScreen) screen);
    }

    public static ResetButton getResetAllButton(int buttonX) {
        ResetButton resetButton = new ResetButton(resetAllButtonID, buttonX, 30);
        resetButton.setComment(I18n.getString("manyLib.gui.button.reset_all"));
        return resetButton;
    }

    public static PeriodicButtonCommented<?> getSortButton(int buttonX, ConfigEnum<SortCategory> sortCategory) {
        return new PeriodicButtonCommented<>(sortButtonID, buttonX + 25, 30, 70, 20, sortCategory);
    }

    public static SearchField getSearchButton(GuiScreen screen) {
        return new SearchField(searchButtonID, 23, 57, screen.width - 95, 13, screen);
    }
}
