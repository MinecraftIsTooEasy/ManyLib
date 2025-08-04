package fi.dy.masa.malilib.gui.widgets;

import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.interfaces.AboutInputMethod;
import fi.dy.masa.malilib.gui.screen.interfaces.IConfigList;
import fi.dy.masa.malilib.gui.screen.util.ConfigItem;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import net.minecraft.GuiScreen;

public class WidgetConfigListView extends WidgetListView<ConfigItem<?>> {
    private final GuiScreen screen;
    private final IConfigList source;

    public WidgetConfigListView(GuiScreen screen, IConfigList source) {
        super(0, 0, 0, 0);
        this.screen = screen;
        this.source = source;
    }

    //  Second block: only for compatibility with Modern Mite's IMBlocker, this block enables the input method.
    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) return true;
        if (this.entries.stream()
                .filter(configItem -> configItem instanceof AboutInputMethod)
                .map(configItem -> (AboutInputMethod) configItem)
                .anyMatch(aboutInputMethod -> aboutInputMethod.tryActivateIM((int) mouseX, (int) mouseY, mouseButton)))
            return true;
        return false;
    }

    @Override
    protected ConfigItem<?> createEntry(int realIndex, int relativeIndex) {
        return ConfigItem.getConfigItem(relativeIndex, this.source.get(realIndex), this.screen);
    }

    @Override
    protected ScrollBar createScrollBar() {
        return ScreenConstants.getScrollBar(this.screen, this);
    }

    @Override
    public int getContentSize() {
        return this.source.size();
    }

    @Override
    public int getPageCapacity() {
        return 7;
    }
}
