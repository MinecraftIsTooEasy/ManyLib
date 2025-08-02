package fi.dy.masa.malilib.gui.layer;

import com.google.common.base.Predicate;
import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.ModLinkButton;
import fi.dy.masa.malilib.gui.button.ScrollBar;
import fi.dy.masa.malilib.gui.screen.interfaces.PagedElement;
import fi.dy.masa.malilib.gui.screen.util.ModLinkEntry;
import fi.dy.masa.malilib.gui.widgets.WidgetPageTurner;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.GuiUtils;
import net.minecraft.GuiScreen;
import net.minecraft.MathHelper;
import net.minecraft.Minecraft;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModLinkLayer extends Layer implements PagedElement {
    private final Predicate<IConfigHandler> presentPredicate;
    private final Supplier<ModLinkButton> buttonAccess;
    private final Function<IConfigHandler, GuiScreen> screenFactory;
    private final Runnable exitAction;
    private final Map<String, IConfigHandler> configMap;
    private final String[] links;
    private final boolean singlePage;
    @Nullable
    private ScrollBar<ModLinkLayer> scrollBar;
    private int page;
    private boolean dirty;
    private final List<ModLinkEntry> entries = new ArrayList<>();

    public ModLinkLayer(Predicate<IConfigHandler> presentPredicate,
                        Supplier<ModLinkButton> buttonAccess,
                        Function<IConfigHandler, GuiScreen> screenFactory,
                        Runnable exitAction
    ) {
        this.presentPredicate = presentPredicate;
        this.buttonAccess = buttonAccess;
        this.screenFactory = screenFactory;
        this.exitAction = exitAction;
        this.configMap = ConfigManager.getInstance().getConfigMap();
        this.links = this.configMap.keySet().toArray(String[]::new);
        this.singlePage = this.getContentSize() <= this.getPageCapacity();
    }

    @Override
    public void initGui() {
        super.initGui();

        if (!this.singlePage) {
            ModLinkButton modLinkButton = this.buttonAccess.get();
            int x = modLinkButton.getX();
            int y = modLinkButton.getY() + modLinkButton.getHeight();

            this.scrollBar = new ScrollBar<>(x + modLinkButton.getWidth() - 10,
                    y + 1,
                    10,
                    this.getPageCapacity() * modLinkButton.getHeight() - 2,
                    this.getPageCapacity(),
                    this.getContentSize(),
                    this
            );

            this.addWidget(this.scrollBar);
            this.addWidget(new WidgetPageTurner(this));
        }

        this.onPageChange();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.dirty) {
            this.entries.forEach(this::removeWidget);
            this.entries.clear();
            for (int i = this.page; i < this.getContentSize() && i < this.page + this.getPageCapacity(); i++) {
                ModLinkEntry entry = this.createEntry(i, i - this.page);
                this.entries.add(entry);
                this.addWidget(entry);
            }
            this.dirty = false;
        }
        RenderUtils.drawRect(0, 0, GuiUtils.getScaledWindowWidth(), GuiUtils.getScaledWindowHeight(), ManyLibConfig.HighlightColor.getColorInteger());
        super.render(context, mouseX, mouseY, delta);
    }

    private ModLinkEntry createEntry(int realIndex, int relativeIndex) {
        ModLinkButton modLinkButton = this.buttonAccess.get();
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

    /**
     * Note that the scroll bar has higher priority, so the overlap between scroll bar and mod link entries
     * is safe at least now
     */
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (super.mouseClicked(mouseX, mouseY, button)) return true;
        this.exitAction.run();
        return true;
    }

    @Override
    public boolean blocksInteraction() {
        return true;
    }

    @Override
    public void setPage(int page) {
        int oldPage = this.page;
        this.page = MathHelper.clamp_int(page, 0, this.getMaxPage());
        if (page != oldPage) this.onPageChange();
    }

    private void onPageChange() {
        this.markDirty();
        if (!this.singlePage) this.scrollBar.updateRatioByScreen(this.page);
    }

    private void markDirty() {
        this.dirty = true;
    }

    @Override
    public int getPage() {
        return this.page;
    }

    @Override
    public int getContentSize() {
        return this.links.length;
    }

    @Override
    public int getPageCapacity() {
        return 10;
    }

}
