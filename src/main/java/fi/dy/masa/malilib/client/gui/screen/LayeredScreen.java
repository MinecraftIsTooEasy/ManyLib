package fi.dy.masa.malilib.client.gui.screen;

import com.google.common.collect.Lists;
import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.client.gui.screen.interfaces.Layered;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class LayeredScreen extends ModernScreen implements Layered {
    private final Layer baseLayer = new Layer(this);
    private final List<Layer> layers = new ArrayList<>();
    private final List<Layer> reverseView = Lists.reverse(this.layers);
    private final List<Layer> layersToAdd = new ArrayList<>();
    private final List<Layer> layersToRemove = new ArrayList<>();

    public LayeredScreen() {
        super();
        this.layers.add(this.baseLayer);
    }

    @Override
    protected void reload() {
        super.reload();
        this.initBaseLayer(this.baseLayer);
        if (this.hasMultiLayer()) {
            for (int i = 1; i < this.layers.size(); i++) {
                this.layers.get(i).reload();
            }
        }
    }

    /**
     * Add elements here.
     */
    protected void initBaseLayer(Layer layer) {
        layer.reload();
    }

    @Override
    public void render(DrawContext drawContext, int mouseX, int mouseY, float partialTicks) {
        super.render(drawContext, mouseX, mouseY, partialTicks);
        if (!this.layersToAdd.isEmpty()) {
            this.layers.addAll(this.layersToAdd);
            this.layersToAdd.clear();
        }
        if (!this.layersToRemove.isEmpty()) {
            this.layers.removeAll(this.layersToRemove);
            this.layersToRemove.clear();
        }

        drawContext.setTopLayer(false);
        for (int i = 0; i < this.layers.size() - 1; i++) {
            this.layers.get(i).render(drawContext, mouseX, mouseY, partialTicks);
        }
        drawContext.setTopLayer(true);
        this.reverseView.get(0).render(drawContext, mouseX, mouseY, partialTicks);
    }

    @Override
    protected void tick() {
        super.tick();
        for (Layer layer : this.layers) {
            layer.tick();
        }
    }

    @Override
    public void mouseMoved(double mouseX, double mouseY) {
        for (Layer layer : this.reverseView) {
            layer.mouseMoved(mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        for (Layer layer : this.reverseView) {
            if (layer.mouseClicked(mouseX, mouseY, button)) return true;
            if (layer.autoExit()) {
                this.removeLayer(layer);
                return true;
            }
            if (layer.blocksInteraction()) break;
        }
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        for (Layer layer : this.reverseView) {
            if (layer.mouseReleased(mouseX, mouseY, button)) return true;
            if (layer.blocksInteraction()) break;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double amount) {
        for (Layer layer : this.reverseView) {
            if (layer.mouseScrolled(mouseX, mouseY, amount)) return true;
            if (layer.blocksInteraction()) break;
        }
        return false;
    }

    @Override
    public boolean charTyped(char chr, int keyCode) {
        for (Layer layer : this.reverseView) {
            if (layer.charTyped(chr, keyCode)) return true;
            if (layer.blocksInteraction()) break;
        }
        if (keyCode == Keyboard.KEY_ESCAPE && this.hasMultiLayer()) {
            this.removeTopLayer();
            return true;
        }
        return super.charTyped(chr, keyCode);
    }

    @Override
    public List<Layer> getLayers() {
        return this.layers;
    }

    /**
     * Do not call this on constructors
     */
    @Override
    public void addLayer(Layer layer) {
        this.layersToAdd.add(layer);
        layer.reload();
    }

    @Override
    public void removeLayer(Layer layer) {
        this.layersToRemove.add(layer);
        layer.removed();
    }



}
