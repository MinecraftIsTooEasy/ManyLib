package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.screen.util.ModLinkEntry;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.GuiScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class LegacyModLinkButton<T extends ModLinkEntry> extends ButtonGeneric {

    private boolean expand;

    List<T> dropDownEntries = new ArrayList<>();

    Constructor<T> constructor;

    public LegacyModLinkButton(int x, int y, int width, int height, String message, String tooltip, Constructor<T> constructor) {
        super(x, y, width, height, message, null);
        this.actionListener = button -> {
            this.dropDownEntries.forEach(dropDownEntry -> dropDownEntry.setVisible(!dropDownEntry.isVisible()));
            this.expand = !this.expand;
        };
        this.setHoverStrings(tooltip);
        this.setTextCentered(false);
        this.setRenderDefaultBackground(false);
        this.constructor = constructor;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.visible) {
            if (expand) {
                RenderUtils.drawRect(0, 0, GuiUtils.getScaledWindowWidth(), GuiUtils.getScaledWindowHeight(), ManyLibConfig.HighlightColor.getColorInteger());
            }
            RenderUtils.drawOutline(this.x, this.y, this.width, this.height, GuiBase.COLOR_WHITE);
            this.bindTexture(ManyLibIcons.ARROW_DOWN.getTexture());
            ManyLibIcons.ARROW_DOWN.renderAt(this.x + this.width - 20, this.y + 1, 0, false, false);
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        super.onMouseReleasedImpl(mouseX, mouseY, mouseButton);
        if (this.expand && !this.isMouseOver(mouseX, mouseY)) this.actionListener.actionPerformedWithButton(this);
    }

    public void initDropDownEntries(IConfigHandler currentConfigInstance, GuiScreen parent) {
        this.dropDownEntries.clear();
        Map<String, IConfigHandler> configMap = ConfigManager.getInstance().getConfigMap();

        String[] array = configMap.keySet().toArray(String[]::new);

        for (int i = 0; i < array.length; i++) {
            String key = array[i];
            IConfigHandler iConfigHandler = configMap.get(key);
            this.dropDownEntries.add(
                    this.constructor.createEntry(
                            i, this.x, this.y + this.height, iConfigHandler == currentConfigInstance, iConfigHandler.getName(),
                            button -> this.mc.displayGuiScreen(iConfigHandler.getConfigScreen(parent))));
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        return super.onMouseClickedImpl(mouseX, mouseY, mouseButton);
    }

    public void addToList(Consumer<T> consumer) {
        this.dropDownEntries.forEach(consumer);
    }

    @FunctionalInterface
    public interface Constructor<T> {
        T createEntry(int index, int startX, int startY, boolean present, String content, IButtonActionListener listener);
    }
}
