package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.config.interfaces.IConfigPeriodic;
import fi.dy.masa.malilib.gui.button.interfaces.ICommentedElement;
import net.minecraft.Minecraft;
import org.jetbrains.annotations.Nullable;

public class PeriodicButtonCommented<T extends IConfigBase & IConfigPeriodic> extends PeriodicButton implements ICommentedElement {
    private String comment;

    public PeriodicButtonCommented(int index, int x, int y, int width, int height, T configPeriodic) {
        super(index, x, y, width, height, configPeriodic);
        this.comment = configPeriodic.getConfigGuiDisplayComment();
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        super.drawButton(par1Minecraft, par2, par3);
        this.tryDrawComment(par1Minecraft.currentScreen, par2, par3);
    }

    @Override
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Nullable
    @Override
    public String getComment() {
        return this.comment;
    }

    @Override
    public boolean shouldDrawComment() {
        return this.drawButton && this.enabled && this.func_82252_a();
    }
}
