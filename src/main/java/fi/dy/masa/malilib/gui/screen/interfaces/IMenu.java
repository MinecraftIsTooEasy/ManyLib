package fi.dy.masa.malilib.gui.screen.interfaces;

import fi.dy.masa.malilib.gui.button.GuiButtonCommented;
import net.minecraft.GuiButton;
import net.minecraft.GuiSmallButton;

public interface IMenu {
    default GuiButton getButton(int index, int x, int y, String name, String comment) {
        if (comment == null) {
            return new GuiSmallButton(index, x, y, name);
        }
        return new GuiButtonCommented(index, x, y, 150, 20, name, comment);
    }
}
