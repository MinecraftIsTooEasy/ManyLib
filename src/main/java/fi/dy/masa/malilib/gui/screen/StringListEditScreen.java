package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.widgets.WidgetStringEditEntry;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class StringListEditScreen extends LegacyListScreen<WidgetStringEditEntry> {
    private final ConfigStringList config;

    private final List<String> tempList;

    public StringListEditScreen(ConfigStringList config, GuiScreen parent) {
        this.config = config;
        this.setParent(parent);
        this.tempList = new ArrayList<>(config.getStringListValue());
    }

    @Override
    public void initGui() {
        super.initGui();
        this.setTitle(StringUtils.translate("manyLib.gui.title.editStringList"));
        this.addWidget(WidgetText.of(GuiBase.TXT_AQUA + StringUtils.translate("manyLib.gui.configuring") + ": " + this.config.getConfigGuiDisplayName()).position(this.width / 2, 35).centered());
        this.onContentChange();
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        List<String> list = this.config.getStringListValue();
        list.clear();
        list.addAll(this.tempList);
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected WidgetStringEditEntry createEntry(int realIndex, int relativeIndex) {
        return new WidgetStringEditEntry(
                realIndex, relativeIndex,
                this.tempList.get(realIndex), this.tempList, this
                , this::onContentChange);
    }

    @Override
    public int getContentSize() {
        return this.tempList.size();
    }

    @Override
    public int getPageCapacity() {
        return 8;
    }
}
