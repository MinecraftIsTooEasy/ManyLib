package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.ButtonBase;
import fi.dy.masa.malilib.gui.layer.StringListEditLayer;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import fi.dy.masa.malilib.gui.screen.LayeredScreen;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;

class ConfigItemStringList extends ConfigItem<ConfigStringList> {
    final ButtonBase editButton;

    public ConfigItemStringList(int index, ConfigStringList config, GuiScreen screen) {
        super(index, config, screen);
        this.editButton = ScreenConstants.getCommonButton(index,
                this.getStringPreview(),
                screen,
//                button -> screen.mc.displayGuiScreen(new StringListEditScreen(config, screen))
                button -> toggleLayer(config, (DefaultConfigScreen) screen)
        );
        this.buttons.add(editButton);
    }

    private static void toggleLayer(ConfigStringList config, LayeredScreen screen) {
        screen.toggleLayer(layer -> layer instanceof StringListEditLayer,
                () -> new StringListEditLayer(config, screen)
        );
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.editButton.isMouseOver()) {
            List<String> strings = new ArrayList<>();
            strings.add(StringUtils.translate("manyLib.gui.comment.clickToEditStringList"));
            strings.addAll(this.config.getStringListValue());
            RenderUtils.drawTextList(strings, mouseX, mouseY, drawContext);
        }
    }

    private String getStringPreview() {
        String raw = this.config.getDisplayText();
        return raw.length() > 24 ? raw.substring(0, 20) + ",...]" : raw;
    }

    @Override
    public void resetButtonClicked() {
        this.editButton.setDisplayString(this.getStringPreview());
    }
}
