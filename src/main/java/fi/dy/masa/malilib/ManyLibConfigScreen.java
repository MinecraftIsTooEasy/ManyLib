package fi.dy.masa.malilib;

import fi.dy.masa.malilib.config.interfaces.IConfigHandler;
import fi.dy.masa.malilib.gui.screen.DefaultConfigScreen;
import net.minecraft.GuiScreen;

public class ManyLibConfigScreen extends DefaultConfigScreen {
    public ManyLibConfigScreen(GuiScreen parentScreen, IConfigHandler configInstance) {
        super(parentScreen, configInstance);
    }

    public void updateTitle() {
        this.reload();// just reload all haha
    }
}
