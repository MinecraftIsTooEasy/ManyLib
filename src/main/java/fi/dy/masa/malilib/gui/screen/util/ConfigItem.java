package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.ManyLib;
import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.options.*;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.button.ButtonGeneric;
import fi.dy.masa.malilib.gui.screen.interfaces.ScreenParented;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

public abstract class ConfigItem<T extends ConfigBase<?> & IConfigDisplay> extends WidgetBase {
    final T config;
    final ButtonGeneric resetButton;
    final WidgetText widgetText;
    boolean visible = true;
    final List<ButtonBase> buttons = new ArrayList<>();
    final GuiScreen screen;

    public ConfigItem(int index, T config, GuiScreen screen) {
        super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.config = config;
        this.screen = screen;
        this.resetButton = ScreenConstants.getResetButton(index, screen, config, button -> {
            this.config.resetToDefault();
            this.resetButtonClicked();
        });
        this.y = this.resetButton.getY();
        this.height = this.resetButton.getHeight();
        this.update();
        this.widgetText = ScreenConstants.getCommentedText(index, config, screen);
        this.buttons.add(this.resetButton);
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        if (this.visible) {
            int color = ManyLibConfig.HighlightColor.getColorInteger();
            if (((ScreenParented) this.screen).getHoveredSubWidget() == null && this.isMouseOver(mouseX, mouseY)) {
                RenderUtils.drawRect(0, this.y, GuiUtils.getScaledWindowWidth(), this.height, color);
            }
            this.widgetText.render(mouseX, mouseY, selected, drawContext);
            this.buttons.forEach(guiButton -> guiButton.render(mouseX, mouseY, guiButton.isMouseOver(), drawContext));
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.widgetText.postRenderHovered(mouseX, mouseY, selected, drawContext);
        this.resetButton.postRenderHovered(mouseX, mouseY, selected, drawContext);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        return mouseButton == 0 && this.buttons.stream().anyMatch(button -> button.onMouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        this.buttons.forEach(x -> x.onMouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void update() {
        super.update();
        this.resetButton.update();
    }

    public void resetButtonClicked() {
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        this.customSetVisible(visible);
    }

    public void customSetVisible(boolean visible) {
    }

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
