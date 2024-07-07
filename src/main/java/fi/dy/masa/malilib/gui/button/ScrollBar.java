package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.screen.ValueScreen;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiButton;
import net.minecraft.MathHelper;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

public class ScrollBar extends GuiButton {
    protected boolean dragging;
    protected float sliderRatio;
    protected int maxStatus;
    protected float percentage;
    protected int sliderHeight;
    protected final ValueScreen screen;

    public ScrollBar(int index, int xPos, int yPos, int width, int height, int pageCapacity, int maxStatus, ValueScreen screen) {
        super(index, xPos, yPos, width, height, "");
        this.updateArguments(maxStatus, pageCapacity);
        this.screen = screen;
    }

    public void updateArguments(int pageCapacity, int total) {
        float temp;
        if (total <= pageCapacity) {
            temp = 1.0F;
            this.maxStatus = 0;
        } else {
            temp = (float) pageCapacity / total;
            this.maxStatus = total - pageCapacity;
        }
        this.percentage = temp;
        this.sliderHeight = (int) (height * temp);
    }

    @Override
    protected int getHoverState(boolean par1) {
        return 0;
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        if (this.drawButton) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int backGroundColor = StringUtils.getColor("#C0404040", 0);
            this.drawGradientRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, backGroundColor, backGroundColor);
            this.mouseDragged(par1Minecraft, par2, par3);
        }
    }

    @Override
    protected void mouseDragged(Minecraft minecraft, int mouseX, int mouseY) {
        if (this.enabled) {
            if (this.drawButton) {
                if (this.dragging) {
                    this.sliderRatio = this.getRatioFromSlider(mouseY);
                    this.updateScreenByRatio();
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                int scrollColor = StringUtils.getColor("#FFFFFFFF", 0);
                int y = this.yPosition + (int) (this.sliderRatio * (float) (this.height - 8));
                if (y > this.yPosition + this.height - this.sliderHeight) {
                    y = this.yPosition + this.height - this.sliderHeight;
                }
                this.drawGradientRect(this.xPosition + 1, y + 1, this.xPosition + this.width - 1, y + this.sliderHeight + 3, scrollColor, scrollColor);
            }
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int mouseX, int mouseY) {
        if (super.mousePressed(minecraft, mouseX, mouseY)) {
            this.sliderRatio = this.getRatioFromSlider(mouseY);
            this.updateScreenByRatio();
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void mouseReleased(int par1, int par2) {
        this.dragging = false;
    }

    public void updateRatioByScreen(int status) {
        if (this.maxStatus > 0) {
            this.sliderRatio = (1 - this.percentage) * ((float) status / this.maxStatus);
        }
    }

    private void updateScreenByRatio() {
        float temp = 1.0F;
        if (this.sliderRatio < 1.0F - this.percentage) {
            temp = this.sliderRatio / (1.0F - this.percentage);
        }
        this.screen.setStatusByRatio(temp);
    }

    private float getRatioFromSlider(int mouseY) {
        return MathHelper.clamp_float((float) (mouseY - (this.yPosition + 4)) / (float) (this.height - 8), 0.0F, 1.0F);
    }
}
