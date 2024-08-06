package fi.dy.masa.malilib.gui;

import fi.dy.masa.malilib.gui.interfaces.IGuiIcon;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.ResourceLocation;

public enum ManyLibIcons implements IGuiIcon {
    ResetButton(0, 0, 20, 20),
    PageDownButton(20, 0, 20, 20),
    PageUpButton(40, 0, 20, 20),
    SearchButton(20, 60, 9, 9),
    ToggleOn(0, 60, 20, 20),
    ToggleOff(0, 80, 20, 20),
    SlideIdentifier(0, 100, 16, 16),
    InputIdentifier(16, 100, 16, 16),
    AddMinusButton(32, 100, 16, 16),
    ;

    public static final ResourceLocation TEXTURE = new ResourceLocation("textures/gui/widgets_many_lib.png");

    private final int u;
    private final int v;
    private final int w;
    private final int h;
    private final int hoverOffU;
    private final int hoverOffV;

    ManyLibIcons(int u, int v, int w, int h) {
        this(u, v, w, h, 0, h);
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
