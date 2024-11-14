package fi.dy.masa.malilib.gui.screen.util;

import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;

public class ColorBoardSV extends WidgetBase {
    private final ConfigInteger hConfig;
    private final ConfigInteger aConfig;

    public float s;
    public float v;

    private int circleX;

    private int circleY;

    private final Runnable onDrag;

    private final Runnable onRightClick;

    private boolean dragging = false;

    public ColorBoardSV(ConfigInteger hConfig, ConfigInteger aConfig, Runnable onDrag, Runnable onRightClick, int xPos, int yPos, int width, int height) {
        super(xPos, yPos, width, height);
        this.hConfig = hConfig;
        this.aConfig = aConfig;
        this.onDrag = onDrag;
        this.onRightClick = onRightClick;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.dragging) {
            this.updateSV(mouseX, mouseY);
        }
        int h = this.hConfig.getIntegerValue();
        int a = this.aConfig.getIntegerValue();

        // the main board
        RenderUtils.preRenderGradient();
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        for (int y = this.y; y < this.y + this.height; y++) {
            int startColor = HSV.ofIFF(h, 0.0F, (float) (this.y + this.height - 1 - y) / (this.height - 1)).toColor(a);
            int endColor = HSV.ofIFF(h, 1.0F, (float) (this.y + this.height - 1 - y) / (this.height - 1)).toColor(a);
            RenderUtils.bufferGradientHorizontal(this.x, y, this.x + this.width, y + 1, 0.0D, startColor, endColor, tessellator);
        }
        tessellator.draw();
        RenderUtils.postRenderGradient();

        // circle indicator
        RenderUtils.startScissor(this.x, this.y, this.width, this.height);

        RenderUtils.drawDisk(this.circleX, this.circleY, 4, 0xFFFFFFFF);
        RenderUtils.drawDisk(this.circleX, this.circleY, 2.5F, HSV.ofIFF(h, this.s, this.v).toColor());
        RenderUtils.drawCircle(this.circleX, this.circleY, 4, 0xFF000000);

        RenderUtils.endScissor();
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawHoverText(mouseX, mouseY, this.getHoverStrings(), drawContext);
        }
    }

    @Override
    protected boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.updateSV(mouseX, mouseY);
                this.dragging = true;
            } else {
                this.onRightClick.run();
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    public void setSV(float s, float v) {
        this.s = s;
        this.v = v;
        this.circleX = (int) (this.x + (this.width - 1) * s);
        this.circleY = (int) (this.y + (this.height - 1) * (1 - v));
    }

    private void updateSV(int mouseX, int mouseY) {
        mouseX = MathHelper.clamp_int(mouseX, this.x, this.x + this.width - 1);
        mouseY = MathHelper.clamp_int(mouseY, this.y, this.y + this.width - 1);
        this.s = (float) (mouseX - this.x) / (this.width - 1);
        this.v = (float) (this.y + this.height - 1 - mouseY) / (this.height - 1);
        this.circleX = mouseX;
        this.circleY = mouseY;
        this.onDrag.run();
    }
}
