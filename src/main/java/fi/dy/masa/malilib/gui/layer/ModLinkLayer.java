package fi.dy.masa.malilib.gui.layer;

import com.google.common.base.Predicate;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.ModLinkButton;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.util.ModLinkEntry;
import fi.dy.masa.malilib.gui.widgets.WidgetListView;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModLinkLayer extends Layer {
    private final Predicate<IConfigHandler> presentPredicate;
    /**
     * The button will be recreated on reloading.
     */
    private final Supplier<ModLinkButton> buttonAccess;
    private final Function<IConfigHandler, GuiScreen> screenFactory;

    public ModLinkLayer(Predicate<IConfigHandler> presentPredicate,
                        Supplier<ModLinkButton> buttonAccess,
                        Function<IConfigHandler, GuiScreen> screenFactory
    ) {
        this.presentPredicate = presentPredicate;
        this.buttonAccess = buttonAccess;
        this.screenFactory = screenFactory;
    }

    /**
     * Note that the scroll bar has higher priority, so the overlap between scroll bar and mod link entries
     * is safe at least now
     */
    @Override
    public void initGui() {
        super.initGui();

        WidgetModLink widgetModLink = new WidgetModLink(this.presentPredicate, this.buttonAccess.get(), this.screenFactory);
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
        private final Predicate<IConfigHandler> presentPredicate;
        private final ModLinkButton modLinkButton;
        private final Function<IConfigHandler, GuiScreen> screenFactory;
        private final Map<String, IConfigHandler> configMap;
        private final String[] links;
        private static final int CAPACITY = 10;

        private WidgetModLink(Predicate<IConfigHandler> presentPredicate,
                              ModLinkButton modLinkButton,
                              Function<IConfigHandler, GuiScreen> screenFactory) {
            super(modLinkButton.getX(),
                    modLinkButton.getY() + modLinkButton.getHeight(),
                    modLinkButton.getX(),
                    modLinkButton.getHeight() * CAPACITY
            );
            this.presentPredicate = presentPredicate;
            this.modLinkButton = modLinkButton;
            this.screenFactory = screenFactory;
            this.configMap = ConfigManager.getInstance().getConfigMap();
            this.links = this.configMap.keySet().toArray(String[]::new);
        }

        @Override
        protected ModLinkEntry createEntry(int realIndex, int relativeIndex) {
            ModLinkButton modLinkButton = this.modLinkButton;
            int x = modLinkButton.getX();
            int y = modLinkButton.getY() + modLinkButton.getHeight();

            String link = this.links[realIndex];

            IConfigHandler iConfigHandler = this.configMap.get(link);

            ModLinkEntry widget = new ModLinkEntry(x,
                    y + relativeIndex * ModLinkEntry.HeightUnit,
                    this.presentPredicate.test(iConfigHandler),
                    iConfigHandler.getName(),
                    button -> Minecraft.getMinecraft().displayGuiScreen(this.screenFactory.apply(iConfigHandler))
            );
            widget.setVisible(true);
            return widget;
        }

        @Override
        protected ScrollBar createScrollBar() {
            ModLinkButton modLinkButton = this.modLinkButton;

            int x = modLinkButton.getX();
            int y = modLinkButton.getY() + modLinkButton.getHeight();

            return new ScrollBar(x + modLinkButton.getWidth() - 10,
                    y,
                    10,
                    this.getPageCapacity() * modLinkButton.getHeight(),
                    this
            );
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
