package fi.dy.masa.malilib.client.gui.screen.interfaces;

import fi.dy.masa.malilib.client.gui.layer.Layer;
import fi.dy.masa.malilib.util.CollectionUtils;

import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Layered {
    List<Layer> getLayers();

    void addLayer(Layer layer);

    void removeLayer(Layer layer);

    default Layer getBaseLayer() {
        return this.getLayers().get(0);
    }

    default Layer getTopLayer() {
        return CollectionUtils.lastOf(this.getLayers());
    }

    default boolean hasMultiLayer() {
        return this.getLayers().size() > 1;
    }

    default void removeTopLayer() {
        this.removeLayer(this.getTopLayer());
    }

    default boolean isTopLayer(Layer layer) {
        return layer == this.getTopLayer();
    }

    default void toggleLayer(Predicate<Layer> predicate, Supplier<Layer> factory) {
        if (predicate.test(this.getTopLayer())) {
            this.removeTopLayer();
        } else {
            this.addLayer(factory.get());
        }
    }

}
