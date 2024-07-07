package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;
import net.minecraft.GuiButton;

public class PeriodicButton extends GuiButton implements IButtonPeriodic {
    protected final IConfigPeriodic configPeriodic;

    public PeriodicButton(int index, int x, int y, int width, int height, IConfigPeriodic configPeriodic) {
        super(index, x, y, width, height, configPeriodic.getDisplayText());
        this.configPeriodic = configPeriodic;
    }

    @Override
    public void next() {
        this.configPeriodic.next();
        this.updateString();
    }

    @Override
    public void updateString() {
        this.displayString = this.configPeriodic.getDisplayText();
    }
}
