package fi.dy.masa.malilib.client.gui.interfaces;

import fi.dy.masa.malilib.client.gui.DrawContext;

public interface Drawable {
    void render(DrawContext context, int mouseX, int mouseY, float delta);
}
