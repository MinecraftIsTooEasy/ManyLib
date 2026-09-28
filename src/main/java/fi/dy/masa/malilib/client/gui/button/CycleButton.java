package fi.dy.masa.malilib.client.gui.button;

import fi.dy.masa.malilib.client.gui.button.interfaces.IButtonActionListener;
import fi.dy.masa.malilib.client.gui.button.interfaces.ICycleButton;
import fi.dy.masa.malilib.client.gui.config.ConfigDisplayApi;
import fi.dy.masa.malilib.client.util.SoundUtils;
import fi.dy.masa.malilib.config.interfaces.IConfigCyclic;
import fi.dy.masa.malilib.config.options.ConfigBase;

public class CycleButton<T extends ConfigBase<T> & IConfigCyclic> extends ButtonGeneric implements ICycleButton {
    protected final T configCyclic;

    public CycleButton(int x, int y, int width, int height, T configCyclic) {
        this(x, y, width, height, configCyclic, button -> ((ICycleButton) button).cycle());
    }

    public CycleButton(int x, int y, int width, int height, T configCyclic, IButtonActionListener onPress) {
        super(x, y, width, height, ConfigDisplayApi.getButtonText(configCyclic), onPress);
        this.configCyclic = configCyclic;
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
            return true;
        }
        if (mouseButton == 1) {// right click
            SoundUtils.click(this.mc);
            this.cycle(false);
            return true;
        }
        return false;
    }

    @Override
    public void cycle(boolean forward) {
        this.configCyclic.cycle(forward);
        this.updateString();
    }

    @Override
    public void updateString() {
        this.setDisplayString(ConfigDisplayApi.getButtonText(this.configCyclic));
    }
}
