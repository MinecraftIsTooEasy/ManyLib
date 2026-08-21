package fi.dy.masa.malilib.client.gui.interfaces;

import fi.dy.masa.malilib.client.gui.DrawContext;

public interface Renderable {
    void render(DrawContext context, int mouseX, int mouseY, float delta);
}
