package fi.dy.masa.malilib.client.gui.layer;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.client.gui.widgets.WidgetListView;
import fi.dy.masa.malilib.client.gui.widgets.WidgetScrollBar;
import fi.dy.masa.malilib.client.gui.widgets.WidgetStringEditEntry;
import fi.dy.masa.malilib.client.gui.widgets.WidgetText;
import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.GuiScreen;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class StringListEditLayer extends Layer {
    private final ConfigStringList config;
    private WidgetStringListView widgetListView;
    private final Consumer<List<String>> finishAction;

    public StringListEditLayer(ConfigStringList config, GuiScreen screen, Consumer<List<String>> finishAction) {
        super(screen);
        this.config = config;
        this.finishAction = finishAction;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawOverlay();
        this.drawOutlinedBox(150, 90);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void reload() {
        List<String> tempList = null;
        if (this.widgetListView != null) {
            tempList = this.widgetListView.tempList;
        }
        super.reload();
        this.addWidget(WidgetText.of(
                                GuiBase.TXT_AQUA + ScreenText.CONFIGURING.translate()
                                        + ": " + ConfigDisplayApi.getConfigDisplayName(this.config)
                        )
                        .position(this.screen.width / 2, this.screen.height / 2 - 85)
                        .centered()
        );
        Keyboard.enableRepeatEvents(true);

        WidgetStringListView widgetListView = new WidgetStringListView(
                Objects.requireNonNullElseGet(tempList, this::initializeList)
        );
        widgetListView.setSize(this.screen.width, this.screen.height);// TODO more specific
        this.addWidget(widgetListView);
        this.widgetListView = widgetListView;

        widgetListView.onContentChange();
    }

    private @NotNull List<String> initializeList() {
        List<String> list = new ArrayList<>(this.config.getStringListValue());
        if (list.isEmpty()) {
            list.add("");
        }
        return list;
    }

    @Override
    public void removed() {
        super.removed();
        Keyboard.enableRepeatEvents(false);
        this.finishAction.accept(this.widgetListView.tempList);
    }

    @Override
    public boolean autoExit() {
        return true;
    }

    @Override
    public boolean blocksInteraction() {
        return true;
    }

    private static class WidgetStringListView extends WidgetListView<WidgetStringEditEntry> {
        private final List<String> tempList;

        private WidgetStringListView(List<String> tempList) {
            super(0, 0, 0, 0);
            this.tempList = tempList;
        }

        @Override
        protected WidgetStringEditEntry createEntry(int realIndex, int relativeIndex) {
            return new WidgetStringEditEntry(
                    realIndex,
                    this.tempList.get(realIndex),
                    this.tempList,
                    this::onContentChange
            );
        }

        @Override
        protected void placeEntry(WidgetStringEditEntry entry, int relativeIndex) {
            int x = this.width / 2 - 130;
            int y = this.height / 2 - 70 + relativeIndex * this.getEntryHeight();
            entry.setPosition(x, y);
        }

        @Override
        public int getEntryHeight() {
            return ScreenConstants.listEntryHeight;
        }

        @Override
        protected WidgetScrollBar createScrollBar() {
            // differ from the parent screen
            return ScreenConstants.getScrollBar(this.width / 2 + 135,
                    this.height / 2 - 70,
                    this
            );
        }

        @Override
        public int getContentSize() {
            return this.tempList.size();
        }

        @Override
        public int getPageCapacity() {
            return 7;
        }
    }
}
