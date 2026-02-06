package fi.dy.masa.malilib.gui.layer;

import fi.dy.masa.malilib.config.options.ConfigStringList;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.util.ScreenConstants;
import fi.dy.masa.malilib.gui.widgets.WidgetListView;
import fi.dy.masa.malilib.gui.widgets.WidgetStringEditEntry;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.util.StringUtils;
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
    public void initGui() {
        List<String> tempList = null;
        if (this.widgetListView != null) {
            tempList = this.widgetListView.tempList;
        }
        super.initGui();
        this.addWidget(WidgetText.of(
                                GuiBase.TXT_AQUA + StringUtils.translate("manyLib.gui.configuring")
                                        + ": " + this.config.getConfigGuiDisplayName()
                        )
                        .position(this.screen.width / 2, this.screen.height / 2 - 85)
                        .centered()
        );
        Keyboard.enableRepeatEvents(true);

        WidgetStringListView widgetListView = new WidgetStringListView(
                this.screen,
                Objects.requireNonNullElseGet(tempList, this::initializeList)
        );
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
        private final GuiScreen screen;
        private final List<String> tempList;

        private WidgetStringListView(GuiScreen screen, List<String> tempList) {
            super(0, 0, 0, 0);
            this.screen = screen;
            this.tempList = tempList;
        }

        @Override
        protected WidgetStringEditEntry createEntry(int realIndex, int relativeIndex) {
            return new WidgetStringEditEntry(
                    realIndex,
                    relativeIndex,
                    this.tempList.get(realIndex),
                    this.tempList,
                    this.screen
                    , this::onContentChange
            );
        }

        @Override
        protected ScrollBar createScrollBar() {
            // differ from the parent screen
            return ScreenConstants.getScrollBar(this.screen.width / 2 + 135,
                    this.screen.height / 2 - 70,
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
