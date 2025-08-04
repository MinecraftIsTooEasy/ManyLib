package fi.dy.masa.malilib.gui.button;

import fi.dy.masa.malilib.config.interfaces.ConfigType;
import fi.dy.masa.malilib.config.interfaces.IConfigDisplay;
import fi.dy.masa.malilib.config.interfaces.IConfigSlideable;
import fi.dy.masa.malilib.config.interfaces.IStringRepresentable;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.config.options.ConfigDouble;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.button.interfaces.ISliderButton;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.MathHelper;
import org.lwjgl.opengl.GL11;

public class SliderButton<T extends ConfigBase<T> & IConfigSlideable & IConfigDisplay & IStringRepresentable> extends ButtonGeneric implements ISliderButton {
    protected float sliderRatio;
    protected boolean dragging;
    protected final T config;
    private final boolean isDouble;

    public SliderButton(int x, int y, int width, int height, T config) {
        super(x, y, width, height, config.getDisplayText(), button -> {
        });
        this.config = config;
        this.updateSliderRatioByConfig();
        this.isDouble = config.getType() == ConfigType.DOUBLE;
        if (this.isDouble) this.setHoverStrings(this.castDoubleString());
    }

    @Override
    protected int getTextureOffset(boolean isMouseOver) {
        return 0;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
        if (this.enabled) {
            if (this.visible) {
                if (this.dragging) {
                    this.sliderRatio = this.getRatioFromSlider(mouseX);
                    this.config.setValueByRatio(this.sliderRatio);
                    this.updateString();
                }
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                this.bindTexture(BUTTON_TEXTURES);
                RenderUtils.drawTexturedRect(this.x + (int) (this.sliderRatio * (float) (this.width - 8)), this.y, 0, 66, 4, 20);
                RenderUtils.drawTexturedRect(this.x + (int) (this.sliderRatio * (float) (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
            }
        }
        if (this.visible) {
            this.renderDisplayString(drawContext);
        }
    }

    /**
     * Only directs to the super.
     */
    protected final void renderButtonGeneric(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.render(mouseX, mouseY, selected, drawContext);
    }

    @Override
    public boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
        if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
            this.sliderRatio = this.getRatioFromSlider(mouseX);
            this.config.setValueByRatio(this.sliderRatio);
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void postRenderHovered(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
        super.postRenderHovered(mouseX, mouseY, selected, drawContext);
        if (this.isMouseOver(mouseX, mouseY)) {
            RenderUtils.drawHoverText(mouseX, mouseY, this.getHoverStrings(), drawContext);
        }
    }

    @Override
    public void onMouseReleasedImpl(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }

    @Override
    public void updateString() {
        this.displayString = this.config.getDisplayText();
        if (this.isDouble) this.setHoverStrings(this.castDoubleString());
    }

    @Override
    public void updateSliderRatioByConfig() {
        this.sliderRatio = (float) this.config.getRatio();
    }

    @Override
    public float getRatioFromSlider(int mouseX) {
        return MathHelper.clamp_float((float) (mouseX - (this.x + 4)) / (float) (this.width - 8), 0.0F, 1.0F);
    }

    private String castDoubleString() {
        String text = this.config.getStringValue();
        if (text.length() > 11) {
            double doubleValue = ((ConfigDouble) this.config).getDoubleValue();
            text = String.format("%.8f", doubleValue);
        }
        return text;
    }
}
