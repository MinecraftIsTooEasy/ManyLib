package fi.dy.masa.malilib.client.gui.screen.util;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.client.util.RenderUtils;
import net.minecraft.MathHelper;
import net.minecraft.Tessellator;

import java.util.function.IntSupplier;

public class ColorBoardSV extends WidgetBase {
    private final IntSupplier hSupplier;
    private final IntSupplier aSupplier;
    private final Runnable onDrag;
    private final Runnable onRightClick;

    public float s;
    public float v;

    private int circleX;
    private int circleY;

    private boolean dragging = false;

    public ColorBoardSV(IntSupplier hSupplier, IntSupplier aSupplier, Runnable onDrag, Runnable onRightClick) {
        super(0, 0, 0, 0);
        this.hSupplier = hSupplier;
        this.aSupplier = aSupplier;
        this.onDrag = onDrag;
        this.onRightClick = onRightClick;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        if (this.dragging) {
            this.updateSV(mouseX, mouseY);
        }
        int h = this.hSupplier.getAsInt();
        int a = this.aSupplier.getAsInt();

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

        RenderUtils.drawOutline(this.x, this.y, this.width, this.height, 0xFFA0A0A0);
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
