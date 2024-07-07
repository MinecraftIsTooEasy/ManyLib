package fi.dy.masa.malilib.gui.button.interfaces;

import net.minecraft.GuiButton;
import net.minecraft.Minecraft;
import org.jetbrains.annotations.Nullable;

public class GuiButtonCommented extends GuiButton implements ICommentedElement {
    protected String comment;

    public GuiButtonCommented(int index, int xPosition, int yPosition, String textWithin, String comment) {
        super(index, xPosition, yPosition, textWithin);
        this.comment = comment;
    }

    public GuiButtonCommented(int index, int xPosition, int yPosition, int width, int height, String textWithin, String comment) {
        super(index, xPosition, yPosition, width, height, textWithin);
        this.comment = comment;
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
        return this.drawButton && this.enabled && this.field_82253_i;
    }
}

