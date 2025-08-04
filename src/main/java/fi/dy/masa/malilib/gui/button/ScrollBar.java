package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.screen.interfaces.StatusElement;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.MathHelper;
import org.lwjgl.opengl.GL11;

public class ScrollBar extends ButtonGeneric {
    protected boolean dragging;
    protected float sliderRatio;
    /**
     * The quotient of the number of elements that can be displayed on a page to the total number of elements.
     */
    protected float percentage;
    protected int sliderHeight;
    protected final StatusElement target;

    public ScrollBar(int xPos, int yPos, int width, int height, int pageCapacity, int contentSize, StatusElement target) {
        super(xPos, yPos, width, height, "", button -> {
        });
        this.updateArguments(pageCapacity, contentSize);
        this.target = target;
        this.setRenderDefaultBackground(false);
    }

    public void updateArguments(boolean visible) {
        this.setVisible(visible);
        this.updateArguments(this.target.getPageCapacity(), this.target.getContentSize());
    }

    private void updateArguments(int pageCapacity, int contentSize) {
        float temp;
        if (contentSize <= pageCapacity) {
            temp = 1.0F;
        } else {
            temp = (float) pageCapacity / contentSize;
        }
        this.percentage = temp;
        this.sliderHeight = (int) ((this.height - 2) * temp);
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
            if (this.enabled) {
                if (this.dragging) {
                    this.sliderRatio = this.getRatioFromSlider(mouseY);
                    this.updateTargetByRatio();
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                int scrollColor = StringUtils.getColor("#FFFFFFFF", 0);
                int sliderY = this.y + 1 + (int) (this.sliderRatio * (float) (this.height - 2));
                int border = this.y + this.height - this.sliderHeight - 1;
                if (sliderY > border) {
                    sliderY = border;
                }
                RenderUtils.drawGradientRect(this.x + 1, sliderY, this.x + this.width - 1, sliderY + this.sliderHeight, 0, scrollColor, scrollColor);
            }
        }
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
            this.sliderRatio = this.getRatioFromSlider(mouseY);
            this.updateTargetByRatio();
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
        if (this.dragging) return;// i.e. the update comes from here
        int maxStatus = this.target.getMaxStatus();
        if (maxStatus > 0) {
            this.sliderRatio = (1 - this.percentage) * ((float) status / maxStatus);
        }
    }

    private void updateTargetByRatio() {
        float temp = 1.0F;
        if (this.sliderRatio < 1.0F - this.percentage) {
            temp = this.sliderRatio / (1.0F - this.percentage);
        }
        this.target.setPageByRatio(temp);
    }

    private float getRatioFromSlider(int mouseY) {
        return MathHelper.clamp_float((float) (mouseY - (this.y + 4)) / (float) (this.height - 8), 0.0F, 1.0F);
    }
}
