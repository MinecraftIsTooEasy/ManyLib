package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.gui.button.ButtonWidget;
import fi.dy.masa.malilib.gui.button.CommentedText;
import fi.dy.masa.malilib.gui.button.ResetButton;
import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigItem<T extends ConfigBase<?> & IConfigDisplay> implements IInteractiveElement {
    final T config;
    final ResetButton resetButton;
    final CommentedText commentedText;
    boolean visible = true;
    final List<ButtonWidget> buttons = new ArrayList<>();
    final GuiScreen screen;

    public ConfigItem(int index, T config, GuiScreen screen) {
        this.config = config;
        this.screen = screen;
        this.resetButton = ScreenConstants.getResetButton(index, screen, button -> {
            this.config.resetToDefault();
            this.resetButtonClicked();
        });
        this.updateScreen();
        this.commentedText = ScreenConstants.getCommentedText(index, config, screen);
        this.buttons.add(this.resetButton);
    }

    public void draw(GuiScreen guiScreen, int x, int y) {
        if (this.visible) {
            int yStart = this.resetButton.yPosition;
            int yEnd = yStart + this.resetButton.height;
            int color = ManyLibConfig.highlightColor.getColorInteger();
            if (y >= yStart && y <= yEnd) {
                guiScreen.drawGradientRect(0, yStart, guiScreen.width, yEnd, color, color);
            }
            this.commentedText.draw(guiScreen, x, y);
            this.buttons.forEach(guiButton -> guiButton.drawButton(guiScreen.mc, x, y));
            this.customDraw(guiScreen, x, y);
        }
    }

    public void tryDrawComment(GuiScreen guiScreen, int x, int y) {
        this.commentedText.tryDrawTooltip(guiScreen, x, y);
        this.resetButton.tryDrawTooltip(guiScreen, x, y);
    }

    public abstract void customDraw(GuiScreen guiScreen, int x, int y);

    @Override
    public void mouseClicked(int mouseX, int mouseY, int click) {
        if (click == 0) {
            this.buttons.forEach(guiButton -> this.buttonListen(guiButton, this.screen, mouseX, mouseY));
        }
        this.customMouseClicked(this.screen, mouseX, mouseY, click);
    }

    protected abstract void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click);

    protected void buttonListen(ButtonWidget button, GuiScreen guiScreen, int mouseX, int mouseY) {
        if (button.mousePressed(guiScreen.mc, mouseX, mouseY)) {
            guiScreen.selectedButton = button;
            button.playClickedSound(guiScreen.mc.sndManager);
            button.onPress();
        }
    }

    @Override
    public void updateScreen() {
        this.resetButton.enabled = this.config.isModified();
    }

    @Override
    public void keyTyped(char c, int i) {
    }

    public abstract void resetButtonClicked();

    public abstract void customSetVisible(boolean visible);

    public static ConfigItem<?> getConfigItem(int index, ConfigBase<?> config, GuiScreen screen) {
        return switch (config.getType()) {
            case DOUBLE -> new ConfigItemSlideable<>(index, (ConfigDouble) config, screen);
            case BOOLEAN -> new ConfigItemPeriodic<>(index, (ConfigBoolean) config, screen);
            case INTEGER -> new ConfigItemSlideable<>(index, (ConfigInteger) config, screen);
            case STRING -> new ConfigItemInputBox<>(index, (ConfigString) config, screen);
            case ENUM -> new ConfigItemPeriodic<>(index, (ConfigEnum<?>) config, screen);
            case COLOR -> new ConfigItemColor(index, (ConfigColor) config, screen);
            case HOTKEY -> new ConfigItemHotkey(index, (ConfigHotkey) config, screen);
            case TOGGLE -> new ConfigItemToggle(index, (ConfigToggle) config, screen);
            default -> {
                ManyLib.logger.error("unsupported config type");
                yield null;
            }
        };
    }
}
