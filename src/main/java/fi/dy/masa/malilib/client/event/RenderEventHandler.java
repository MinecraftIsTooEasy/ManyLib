package fi.dy.masa.malilib.client.event;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.interfaces.IRenderDispatcher;
import fi.dy.masa.malilib.client.interfaces.IRenderer;
import fi.dy.masa.malilib.client.util.InfoUtils;
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
}
