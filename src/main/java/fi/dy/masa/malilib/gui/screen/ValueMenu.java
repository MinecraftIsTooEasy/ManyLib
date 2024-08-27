package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.button.ButtonWidget;
import fi.dy.masa.malilib.gui.button.PageButton;
import fi.dy.masa.malilib.gui.button.interfaces.ITooltipElement;
import fi.dy.masa.malilib.gui.screen.interfaces.GuiScreenPaged;
import fi.dy.masa.malilib.gui.screen.interfaces.IMenu;
import net.minecraft.GuiButton;
import net.minecraft.GuiScreen;
import net.minecraft.I18n;

import java.util.List;

public class ValueMenu extends GuiScreenPaged implements IMenu {
    private static final ValueMenu Instance = new ValueMenu();
    private final List<IConfigHandler> configs;

    public ValueMenu() {
        super(null, I18n.getString("manyLib.gui.title.options"), 6, 2);
        this.configs = ConfigManager.getInstance().getConfigMap().values().stream().sorted((x, y) -> x.getName().compareToIgnoreCase(y.getName())).toList();
        this.updatePageCount(this.configs.size());
    }

    public static ValueMenu getInstance(GuiScreen parentScreen) {
        Instance.parentScreen = parentScreen;
        return Instance;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        for (int i = 0; i < this.configs.size(); i++) {
            IConfigHandler configHandler = this.configs.get(i);
            int finalI = i;
            this.buttonList.add(this.getButton(this.getButtonPosX(i), this.getButtonPosY(i), configHandler.getName(), configHandler.getMenuComment(), button -> {
                IConfigHandler simpleConfigs = this.configs.get(finalI);
                this.mc.displayGuiScreen(simpleConfigs.getConfigScreen(this));
            }));
        }
        this.setVisibilities();
        this.buttonList.add(ButtonWidget.builder(I18n.getString("gui.done"), button -> this.leaveThisScreen())
                .dimensions(this.width / 2 - 100, this.height / 6 + 168, 200, 20)
                .build());
        if (this.pageCount > 1) {
            this.buttonList.add(new PageButton(this.width / 2 + 132, this.height / 6 + 168, false, button -> this.scroll(false)));
            this.buttonList.add(new PageButton(this.width / 2 + 154, this.height / 6 + 168, true, button -> this.scroll(true)));
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        super.drawScreen(i, j, f);
        this.buttonList.stream().filter(x -> x instanceof ITooltipElement)
                .anyMatch(x -> ((ITooltipElement) x).tryDrawTooltip(this, i, j));
    }

    @Override
    public void setVisibilities() {
        for (int i = 0; i < this.configs.size(); ++i) {
            ((GuiButton) this.buttonList.get(i)).drawButton = this.isVisible(i);
        }
    }

    @Override
    protected void actionPerformed(GuiButton par1GuiButton) {
        ((ButtonWidget) par1GuiButton).onPress();
    }
}
