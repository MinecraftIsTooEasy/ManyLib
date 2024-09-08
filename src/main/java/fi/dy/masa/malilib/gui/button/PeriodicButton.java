package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;

public class PeriodicButton<T extends IConfigBase & IConfigPeriodic> extends ButtonGeneric implements IButtonPeriodic {
    protected final IConfigPeriodic configPeriodic;

    public PeriodicButton(int x, int y, int width, int height, T configPeriodic) {
        this(x, y, width, height, configPeriodic, button -> ((IButtonPeriodic) button).next());
    }

    public PeriodicButton(int x, int y, int width, int height, T configPeriodic, IButtonActionListener onPress) {
        super(x, y, width, height, configPeriodic.getDisplayText(), onPress);
        this.configPeriodic = configPeriodic;
        this.tooltip(configPeriodic.getConfigGuiDisplayComment());
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
