package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.gui.button.interfaces.IButtonPeriodic;

public class PeriodicButton extends ButtonGeneric implements IButtonPeriodic {
    protected final IConfigPeriodic configPeriodic;

    public PeriodicButton(int x, int y, int width, int height, IConfigPeriodic configPeriodic) {
        this(x, y, width, height, configPeriodic, button -> ((IButtonPeriodic) button).next());
    }

    public PeriodicButton(int x, int y, int width, int height, IConfigPeriodic configPeriodic, IButtonActionListener onPress) {
        super(x, y, width, height, configPeriodic.getDisplayText(), onPress);
        this.configPeriodic = configPeriodic;
        this.setHoverStrings(configPeriodic.getConfigGuiDisplayComment());
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
