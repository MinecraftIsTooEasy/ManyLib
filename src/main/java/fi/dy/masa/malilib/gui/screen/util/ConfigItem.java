package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.gui.button.ResetButton;
import fi.dy.masa.malilib.gui.button.interfaces.CommentedText;
import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import fi.dy.masa.malilib.gui.button.interfaces.ISuppressibleElement;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigItem<T extends ConfigBase<?>> implements IInteractiveElement, ISuppressibleElement {
    final T config;
    final ResetButton resetButton;
    final CommentedText commentedText;
    boolean visible;
    final List<GuiButton> buttons = new ArrayList<>();
    final GuiScreen screen;

    public ConfigItem(int index, T config, GuiScreen screen) {
        this.config = config;
        this.screen = screen;
        this.resetButton = ScreenConstants.getResetButton(index, screen);
        this.commentedText = ScreenConstants.getCommentedText(index, config, screen);
        this.buttons.add(this.resetButton);
    }

    public void draw(GuiScreen guiScreen, int x, int y) {
        if (this.visible) {
            this.commentedText.draw(guiScreen, x, y);
            this.buttons.forEach(guiButton -> guiButton.drawButton(guiScreen.mc, x, y));
            this.customDraw(guiScreen, x, y);
        }
//        this.tryDrawComment(guiScreen, x, y);// TODO
    }

    public void tryDrawComment(GuiScreen guiScreen, int x, int y) {
        this.commentedText.tryDrawComment(guiScreen, x, y);
        this.resetButton.tryDrawComment(guiScreen, x, y);
    }

    public abstract void customDraw(GuiScreen guiScreen, int x, int y);

    @Override
    public void mouseClicked(int mouseX, int mouseY, int click) {
        if (click == 0) {
            this.buttons.forEach(guiButton -> this.buttonListen(guiButton, this.screen, mouseX, mouseY));
        }
        this.customMouseClicked(this.screen, mouseX, mouseY, click);
    }

    public abstract void customMouseClicked(GuiScreen guiScreen, int mouseX, int mouseY, int click);

    public void buttonListen(GuiButton guiButton, GuiScreen guiScreen, int mouseX, int mouseY) {
        if (guiButton.mousePressed(guiScreen.mc, mouseX, mouseY)) {
            guiScreen.selectedButton = guiButton;
            guiButton.playClickedSound(guiScreen.mc.sndManager);
            this.actionPerformed(guiButton);
        }
    }

    @Override
    public void updateScreen() {
        this.resetButton.enabled = this.config.isModified();
    }

    public void actionPerformed(GuiButton guiButton) {
        if (guiButton == this.resetButton) {
            this.config.resetToDefault();
            this.resetButtonClicked();
        }
        this.customActionPerformed(guiButton);
    }

    @Override
    public void keyTyped(char c, int i) {
    }

    public abstract void resetButtonClicked();

    public abstract void customActionPerformed(GuiButton guiButton);

    //TODO kill it
    @Override
    public void setVisible(boolean visible) {
        this.visible = visible;
        this.buttons.forEach(guiButton -> guiButton.drawButton = visible);
        this.commentedText.setVisible(visible);
        this.customSetVisible(visible);
    }

    public abstract void customSetVisible(boolean visible);

    public static ConfigItem<?> getConfigItem(int index, ConfigBase<?> config, GuiScreen screen) {
        return switch (config.getType()) {
            case DOUBLE -> new ConfigItemSlideable<>(index, (ConfigDouble) config, screen);
            case BOOLEAN -> new ConfigItemPeriodic<>(index, (ConfigBoolean) config, screen);
            case INTEGER -> new ConfigItemSlideable<>(index, (ConfigInteger) config, screen);
            case STRING -> new ConfigItemInputBox<>(index, (ConfigString) config, screen);
            case ENUM -> new ConfigItemPeriodic<>(index, (ConfigEnum<?>) config, screen);
            case COLOR -> new ConfigItemColor(index, (ConfigColor) config, screen);
            default -> {
                Minecraft.setErrorMessage("unsupported config type");
                yield null;
            }
        };
    }
}
