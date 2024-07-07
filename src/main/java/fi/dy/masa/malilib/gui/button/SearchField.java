package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.button.interfaces.GuiButtonCommented;
import fi.dy.masa.malilib.gui.button.interfaces.IInteractiveElement;
import fi.dy.masa.malilib.gui.button.interfaces.ISuppressibleElement;
import fi.dy.masa.malilib.gui.button.interfaces.IToggleableElement;
import fi.dy.masa.malilib.gui.screen.interfaces.SearchableScreen;
import fi.dy.masa.malilib.util.Constant;
import net.minecraft.GuiScreen;
import net.minecraft.GuiTextField;
import net.minecraft.I18n;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class SearchField extends GuiButtonCommented implements ISuppressibleElement, IInteractiveElement, IToggleableElement {
    private final GuiTextField guiTextField;

    private boolean searchEnabled;

    private final GuiScreen screen;

    public SearchField(int index, int x, int y, int width, int height, GuiScreen screen) {
        super(index, x, y, 9, 9, "", I18n.getString("manyLib.gui.button.search"));
        this.guiTextField = new GuiTextField(screen.fontRenderer, x + 16, y - 2, width, height);
        this.screen = screen;
        this.setVisible(false);
    }

    @Override
    public void keyTyped(char c, int i) {
        if (this.guiTextField.isFocused()) {
            String temp = this.guiTextField.getText();
            this.guiTextField.textboxKeyTyped(c, i);
            String after = this.guiTextField.getText();
            if (!after.equals(temp)) {
                ((SearchableScreen) this.screen).updateSearchResult(after);
            }
            if (i == 1 || i == 28 || i == 156) {
                this.guiTextField.setFocused(false);
            }
        }
    }

    @Override
    public void updateScreen() {
        this.guiTextField.updateCursorCounter();
    }

    @Override
    public void mouseClicked(int par1, int par2, int par3) {
        this.guiTextField.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawButton(Minecraft minecraft, int par2, int par3) {
        if (!this.drawButton) {
            return;
        }
        minecraft.getTextureManager().bindTexture(Constant.buttonTexturesManyLib);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 20, 60, this.width, this.height);
        this.tryDrawComment(this.screen, par2, par3);
        this.guiTextField.drawTextBox();
    }

    @Override
    public void toggle() {
        this.setVisible(!this.searchEnabled);
        if (this.searchEnabled) {
            ((SearchableScreen) this.screen).resetSearchResult();
            this.guiTextField.setText("");
        } else {
            this.guiTextField.setFocused(true);
        }
        this.searchEnabled = !this.searchEnabled;
    }

    @Override
    public void setVisible(boolean visible) {
        this.guiTextField.setVisible(visible);
        this.guiTextField.setEnabled(visible);
    }
}
