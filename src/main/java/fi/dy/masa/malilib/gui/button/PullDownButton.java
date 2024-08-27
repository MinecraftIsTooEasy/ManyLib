package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import fi.dy.masa.malilib.gui.button.interfaces.IToggleableElement;
import net.minecraft.Minecraft;

public class PullDownButton extends ButtonWidget implements IInteractiveElement, IToggleableElement {
    private boolean expand;

    public PullDownButton(int x, int y, int width, int height, String tooltip) {
        super(x, y, width, height, "测试", button -> ((PullDownButton) button).toggle());
        this.setTooltip(tooltip);
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {// TODO
        if (!this.drawButton) {
            return;
        }
        this.tryDrawTooltip(minecraft.currentScreen, par2, par3);
    }

    @Override
    public void keyTyped(char c, int i) {

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int click) {
        if (!this.expand) return;

    }

    @Override
    public void updateScreen() {

    }

    @Override
    public void toggle() {
        this.expand = !this.expand;
    }
}
