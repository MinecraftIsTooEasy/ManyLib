package fi.dy.masa.malilib.gui;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.ResourceLocation;

public enum ManyLibIcons implements IGuiIcon {
    ResetButton(0, 100, 20, 20, true),
    PageDownButton(20, 100, 20, 20, true),
    PageUpButton(40, 100, 20, 20, true),
    ToggleOn(0, 160, 20, 20),
    ToggleOff(0, 180, 20, 20),

    ARROW_UP(108, 0, 15, 15),
    ARROW_DOWN(108, 15, 15, 15),
    PLUS(108, 30, 15, 15),
    MINUS(108, 45, 15, 15),
    BTN_SLIDER(153, 0, 16, 16),
    BTN_TXTFIELD(153, 16, 16, 16),
    BTN_PLUSMINUS_16(153, 32, 16, 16),
    SEARCH(201, 0, 12, 12, 0, 0);;

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/manyLib/gui/gui_widgets.png");

    private final int u;
    private final int v;
    private final int w;
    private final int h;
    private final int hoverOffU;
    private final int hoverOffV;

    ManyLibIcons(int u, int v, int w, int h) {
        this(u, v, w, h, false);
    }

    ManyLibIcons(int u, int v, int w, int h, boolean offsetVertical) {
        this(u, v, w, h, offsetVertical ? 0 : w, offsetVertical ? h : 0);
    }


    ManyLibIcons(int u, int v, int w, int h, int hoverOffU, int hoverOffV) {
        this.u = u;
        this.v = v;
        this.w = w;
        this.h = h;
        this.hoverOffU = hoverOffU;
        this.hoverOffV = hoverOffV;
    }

    @Override
    public int getWidth() {
        return this.w;
    }

    @Override
    public int getHeight() {
        return this.h;
    }

    @Override
    public int getU() {
        return this.u;
    }

    @Override
    public int getV() {
        return this.v;
    }

    @Override
    public void renderAt(int x, int y, float zLevel, boolean enabled, boolean selected) {
        int u = this.u;
        int v = this.v;

        if (enabled) {
            u += this.hoverOffU;
            v += this.hoverOffV;
        }

        if (selected) {
            u += this.hoverOffU;
            v += this.hoverOffV;
        }

        RenderUtils.drawTexturedRect(x, y, u, v, this.w, this.h, zLevel);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
