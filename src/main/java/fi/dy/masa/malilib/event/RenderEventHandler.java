package fi.dy.masa.malilib.event;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.interfaces.IRenderDispatcher;
import fi.dy.masa.malilib.interfaces.IRenderer;
import fi.dy.masa.malilib.util.InfoUtils;
import net.minecraft.Minecraft;

import java.util.ArrayList;
import java.util.List;

public class RenderEventHandler implements IRenderDispatcher {
    private static final RenderEventHandler INSTANCE = new RenderEventHandler();

    private final List<IRenderer> overlayRenderers = new ArrayList<>();
    private final List<IRenderer> tooltipLastRenderers = new ArrayList<>();
    private final List<IRenderer> worldLastRenderers = new ArrayList<>();

    public static IRenderDispatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public void registerGameOverlayRenderer(IRenderer renderer) {
        if (this.overlayRenderers.contains(renderer) == false) {
            this.overlayRenderers.add(renderer);
        }
    }

    @Override
    public void registerTooltipLastRenderer(IRenderer renderer) {
        if (this.tooltipLastRenderers.contains(renderer) == false) {
            this.tooltipLastRenderers.add(renderer);
        }
    }

    @Override
    public void registerWorldLastRenderer(IRenderer renderer) {
        if (this.worldLastRenderers.contains(renderer) == false) {
            this.worldLastRenderers.add(renderer);
        }
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public void onRenderGameOverlayPost(DrawContext drawContext, Minecraft mc, float partialTicks) {
        if (this.overlayRenderers.isEmpty() == false) {
            for (IRenderer renderer : this.overlayRenderers) {
                renderer.onRenderGameOverlayPost(drawContext);
            }
        }
        InfoUtils.renderInGameMessages(drawContext);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
//    public void onRenderTooltipLast(DrawContext drawContext, ItemStack stack, int x, int y) {
//        if (this.tooltipLastRenderers.isEmpty() == false) {
//            for (IRenderer renderer : this.tooltipLastRenderers) {
//                renderer.onRenderTooltipLast(drawContext, stack, x, y);
//            }
//        }
//    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
//    public void onRenderWorldLast(MatrixStack matrixStack, Matrix4f projMatrix, MinecraftClient mc) {
//        if (this.worldLastRenderers.isEmpty() == false) {
//            mc.getProfiler().swap("malilib_renderworldlast");
//
//            Framebuffer fb = MinecraftClient.isFabulousGraphicsOrBetter() ? mc.worldRenderer.getTranslucentFramebuffer() : null;
//
//            if (fb != null) {
//                fb.beginWrite(false);
//            }
//
//            for (IRenderer renderer : this.worldLastRenderers) {
//                mc.getProfiler().push(renderer.getProfilerSectionSupplier());
//                renderer.onRenderWorldLast(matrixStack, projMatrix);
//                mc.getProfiler().pop();
//            }
//
//            if (fb != null) {
//                mc.getFramebuffer().beginWrite(false);
//            }
//        }
//    }
}
