package fi.dy.masa.malilib.client.gui.layer;

import com.google.common.base.Predicate;
import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.button.ModLinkButton;
import fi.dy.masa.malilib.client.gui.screen.util.ModLinkEntry;
import fi.dy.masa.malilib.client.gui.widgets.WidgetListView;
import fi.dy.masa.malilib.client.gui.widgets.WidgetScrollBar;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;

import java.util.function.Function;
import java.util.function.Supplier;

public class ModLinkLayer extends Layer {
    private final Predicate<String> presentPredicate;
    /**
     * The button will be recreated on reloading.
     */
    private final Supplier<ModLinkButton> buttonAccess;
    private final Function<String, GuiScreen> screenFactory;

    public ModLinkLayer(GuiScreen screen,
                        Predicate<String> presentPredicate,
                        Supplier<ModLinkButton> buttonAccess,
                        Function<String, GuiScreen> screenFactory
    ) {
        super(screen);
        this.presentPredicate = presentPredicate;
        this.buttonAccess = buttonAccess;
        this.screenFactory = screenFactory;
    }

    /**
     * Note that the scroll bar has higher priority, so the overlap between scroll bar and mod link entries
     * is safe at least now
     */
    @Override
    public void reload() {
        super.reload();

        ModLinkButton modLinkButton = this.buttonAccess.get();
        WidgetModLink widgetModLink = new WidgetModLink(this.presentPredicate, this.screenFactory);
        widgetModLink.setDimensions(modLinkButton.getX(),
                modLinkButton.getY() + modLinkButton.getHeight(),
                modLinkButton.getWidth(),
                modLinkButton.getHeight() * widgetModLink.getPageCapacity()
        );
        this.addWidget(widgetModLink);
        widgetModLink.onStatusChange();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawOverlay();
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean autoExit() {
        return true;
    }

    @Override
    public boolean blocksInteraction() {
        return true;
    }

    private static class WidgetModLink extends WidgetListView<ModLinkEntry> {
        private final Predicate<String> presentPredicate;
        private final Function<String, GuiScreen> screenFactory;
        private final String[] links;
        private static final int CAPACITY = 10;

        private WidgetModLink(Predicate<String> presentPredicate, Function<String, GuiScreen> screenFactory) {
            super(0, 0, 0, 0);
            this.presentPredicate = presentPredicate;
            this.screenFactory = screenFactory;
            this.links = ManyLibApi.streamIds().toArray(String[]::new);
        }

        @Override
        protected ModLinkEntry createEntry(int realIndex, int relativeIndex) {
            String link = this.links[realIndex];

            ModLinkEntry widget = new ModLinkEntry(
                    this.presentPredicate.test(link),
                    link,
                    button -> this.mc.displayGuiScreen(this.screenFactory.apply(link))
            );
            widget.setVisible(true);
            return widget;
        }

        @Override
        public int getEntryHeight() {
            return ModLinkEntry.HeightUnit;
        }

        @Override
        protected WidgetScrollBar createScrollBar() {
            int x = this.getX();
            int y = this.getY();

            return new WidgetScrollBar(x + this.getWidth() - 10, y, 10, this.getHeight(), this);
        }

        @Override
        public int getContentSize() {
            return this.links.length;
        }

        @Override
        public int getPageCapacity() {
            return CAPACITY;
        }
    }
}
