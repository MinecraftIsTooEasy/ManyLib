package fi.dy.masa.malilib.client.internal;

import fi.dy.masa.malilib.client.gui.screen.DefaultConfigScreen;
import net.minecraft.GuiScreen;

public class ManyLibConfigScreen extends DefaultConfigScreen {
    public ManyLibConfigScreen(GuiScreen parentScreen) {
        super(parentScreen, ManyLibClientConfig.getInstance());
    }

    public void updateTitle() {
        this.reload();// just reload all haha
    }
}
