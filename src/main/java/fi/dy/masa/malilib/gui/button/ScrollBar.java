package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusScreen;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiScreen;
import net.minecraft.MathHelper;
import org.lwjgl.opengl.GL11;

public class ScrollBar<T extends GuiScreen & StatusScreen> extends ButtonGeneric {
    protected boolean dragging;
    protected float sliderRatio;
    protected int maxStatus;
    protected float percentage;
    protected int sliderHeight;
    protected final T screen;

    public ScrollBar(int xPos, int yPos, int width, int height, int pageCapacity, int maxStatus, T screen) {
        super(xPos, yPos, width, height, "", button -> {
        });
        this.updateArguments(maxStatus, pageCapacity);
        this.screen = screen;
        this.setRenderDefaultBackground(false);
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
    protected int getTextureOffset(boolean isMouseOver) {
        return 0;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.visible) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            int backGroundColor = StringUtils.getColor("#C0404040", 0);
            this.drawGradientRect(this.x, this.y, this.x + this.width, this.y + this.height, backGroundColor, backGroundColor);
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public void onMouseDraggedImpl(int mouseX, int mouseY) {
        if (this.enabled) {
            if (this.visible) {
                if (this.dragging) {
                    this.sliderRatio = this.getRatioFromSlider(mouseY);
                    this.updateScreenByRatio();
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                int scrollColor = StringUtils.getColor("#FFFFFFFF", 0);
                int y = this.y + (int) (this.sliderRatio * (float) (this.height - 8));
                if (y > this.y + this.height - this.sliderHeight) {
                    y = this.y + this.height - this.sliderHeight;
                }
                this.drawGradientRect(this.x + 1, y + 1, this.x + this.width - 1, y + this.sliderHeight + 3, scrollColor, scrollColor);
            }
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
            this.sliderRatio = this.getRatioFromSlider(mouseY);
            this.updateScreenByRatio();
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
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
        return MathHelper.clamp_float((float) (mouseY - (this.y + 4)) / (float) (this.height - 8), 0.0F, 1.0F);
    }
}
