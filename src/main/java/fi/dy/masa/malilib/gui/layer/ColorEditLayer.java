package fi.dy.masa.malilib.gui.layer;

import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.SliderButton;
import fi.dy.masa.malilib.gui.screen.util.ColorBoardSV;
import fi.dy.masa.malilib.gui.screen.util.HSV;
import fi.dy.masa.malilib.gui.screen.util.RGB;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.ColorUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.SystemUtils;
import net.minecraft.GuiScreen;
import net.minecraft.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorEditLayer extends Layer {
    private final static int X_OFFSET = 130;
    private final static int Y_OFFSET = 60;

    private final ConfigColor configColor;
    private final Runnable finishAction;

    private ColorBoardSV colorBoard;

    private final ConfigInteger a;

    private final ConfigInteger h;

    private final ConfigInteger r;
    private final ConfigInteger g;
    private final ConfigInteger b;

    private final List<SliderButton<ConfigInteger>> sliderButtons = new ArrayList<>();

    public ColorEditLayer(ConfigColor config, GuiScreen screen, Runnable finishAction) {
        super(screen);
        this.configColor = config;
        this.finishAction = finishAction;

        int[] decodedARGB = ColorUtils.decodeARGB(config.getColorInteger());

        int r = decodedARGB[1];
        int g = decodedARGB[2];
        int b = decodedARGB[3];

        int[] hsv = RGB.ofIII(r, g, b).toHSV().standardize();

        this.a = new ConfigInteger("A", decodedARGB[0], 0, 255);

        this.r = new ConfigInteger("R", r, 0, 255);
        this.g = new ConfigInteger("G", g, 0, 255);
        this.b = new ConfigInteger("B", b, 0, 255);

        this.h = new ConfigInteger("H", hsv[0], 0, 359);
    }

    @Override
    public void initGui() {
        super.initGui();
        int leftX = this.screen.width / 2 - X_OFFSET;
        int topY = this.screen.height / 2 - Y_OFFSET;

        int sliderX = leftX + 35;
        int topSliderY = topY + 30;

        String description = GuiBase.TXT_AQUA + StringUtils.translate("manyLib.gui.configuring") + ": " + this.configColor.getConfigGuiDisplayName();
        this.addWidget(WidgetText.of(description).position(leftX + 5, topY + 10));

        this.addLine(sliderX, topSliderY, 0, "color.alpha", this.a, this.simple(x -> 0), this::setByA);
        this.addLine(sliderX, topSliderY, 1, "color.red", this.r, this.simple(x -> RGB.ofIII((int) (x * 255), this.g.getIntegerValue(), this.b.getIntegerValue()).toColor()), this::setByRGB);
        this.addLine(sliderX, topSliderY, 2, "color.green", this.g, this.simple(x -> RGB.ofIII(this.a.getIntegerValue(), (int) (x * 255), this.b.getIntegerValue()).toColor()), this::setByRGB);
        this.addLine(sliderX, topSliderY, 3, "color.blue", this.b, this.simple(x -> RGB.ofIII(this.a.getIntegerValue(), this.g.getIntegerValue(), (int) (x * 255)).toColor()), this::setByRGB);

        this.addLine(sliderX, topSliderY, 4, "color.hue", this.h, this::renderHueBar, this::setByHSV);

        this.colorBoard = new ColorBoardSV(this.h,
                this.a,
                this::setByHSV,
                () -> SystemUtils.copyToClipboard(this.configColor.getColorString()),
                this.screen.width / 2 + 25,
                this.screen.height / 2 - 50,
                100,
                100
        );
        HSV hsv = HSV.ofARGB(this.configColor.getColorInteger());
        this.colorBoard.setSV(hsv.getS(), hsv.getV());
        this.addWidget(this.colorBoard);
        this.updateHoverString();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.drawOverlay();
        this.drawOutlinedBox(X_OFFSET, Y_OFFSET);
        super.render(context, mouseX, mouseY, delta);
    }

    private void setByA() {
        this.configColor.setIntegerValue(ColorUtils.encodeARGB(this.a.getIntegerValue(), this.r.getIntegerValue(), this.g.getIntegerValue(), this.b.getIntegerValue()));
        this.updateHoverString();
    }

    private void setByRGB() {
        this.configColor.setIntegerValue(ColorUtils.encodeARGB(this.a.getIntegerValue(), this.r.getIntegerValue(), this.g.getIntegerValue(), this.b.getIntegerValue()));
        this.updateHSV();
        this.updateSlider();
        this.updateHoverString();
    }

    private void setByHSV() {
        HSV hsv = HSV.ofIFF(this.h.getIntegerValue(), this.colorBoard.s, this.colorBoard.v);
        RGB rgb = hsv.toRGB();
        int color = rgb.toColor(this.a.getIntegerValue());
        this.configColor.setIntegerValue(color);
        this.updateRGB();
        this.updateSlider();
        this.updateHoverString();
    }

    private void updateHSV() {
        HSV hsv = HSV.ofARGB(this.configColor.getColorInteger());
        this.h.setIntegerValue(hsv.getH());
        this.colorBoard.setSV(hsv.getS(), hsv.getV());
    }

    private void updateRGB() {
        int[] standardize = RGB.ofARGB(this.configColor.getColorInteger()).standardize();
        this.r.setIntegerValue(standardize[0]);
        this.g.setIntegerValue(standardize[1]);
        this.b.setIntegerValue(standardize[2]);
    }

    private void updateSlider() {
        this.sliderButtons.forEach(x -> {
            x.updateSliderRatioByConfig();
            x.updateString();
        });
    }

    private void updateHoverString() {
        this.colorBoard.setHoverStrings(StringUtils.translate("manyLib.gui.rightClickToCopy"),
                this.configColor.getColorString(),
                String.format("s=%.2f, v=%.2f", this.colorBoard.s, this.colorBoard.v)
        );
    }

    // onClicked: can be slider clicked, dragged or reset button clicked
    private void addLine(int buttonLeftX, int topY, int index, String key, ConfigInteger configInteger, Consumer<SliderButton<?>> renderColorBar, Runnable onModify) {
        int realY = topY + index * 16;
        this.addWidget(WidgetText.of(StringUtils.translate(key)).position(buttonLeftX - 30, realY + 4));

        SliderButton<ConfigInteger> slider = new ColorSlider(buttonLeftX, realY, configInteger, onModify, renderColorBar);
        this.sliderButtons.add(slider);
        this.addWidget(slider);
    }

    private Consumer<SliderButton<?>> simple(Function<Float, Integer> colorSupplier) {
        return sliderButton -> {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            RenderUtils.preRenderGradient();

            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            int x = sliderButton.getX();
            int y = sliderButton.getY();
            RenderUtils.bufferGradientHorizontal(x, y, x + sliderButton.getWidth(), y + sliderButton.getHeight(), 0.0F, colorSupplier.apply(0.0F), colorSupplier.apply(1.0F), tessellator);
            tessellator.draw();

            RenderUtils.postRenderGradient();
        };
    }

    private void renderHueBar(SliderButton<?> sliderButton) {
        int x = sliderButton.getX();
        int y = sliderButton.getY();
        int width = sliderButton.getWidth();
        int height = sliderButton.getHeight();
        double z = 0.0D;
        int segment = width / 6;
        RenderUtils.preRenderGradient();

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();


        this.bufferHueBarSegment(x, y, segment, height, z, 0, 59, tessellator);
        this.bufferHueBarSegment(x + segment, y, segment, height, z, 60, 119, tessellator);
        this.bufferHueBarSegment(x + 2 * segment, y, segment, height, z, 120, 179, tessellator);
        this.bufferHueBarSegment(x + 3 * segment, y, segment, height, z, 180, 239, tessellator);
        this.bufferHueBarSegment(x + 4 * segment, y, segment, height, z, 240, 299, tessellator);
        this.bufferHueBarSegment(x + 5 * segment, y, segment, height, z, 300, 359, tessellator);

        tessellator.draw();

        RenderUtils.postRenderGradient();
    }

    private void bufferHueBarSegment(int x, int y, int width, int height, double z, int startHue, int endHue, Tessellator tessellator) {
        int startColor = HSV.ofIFF(startHue, 1.0F, 1.0F).toColor();
        int endColor = HSV.ofIFF(endHue, 1.0F, 1.0F).toColor();
        RenderUtils.bufferGradientHorizontal(x, y, x + width, y + height, z, startColor, endColor, tessellator);
    }

    @Override
    public boolean autoExit() {
        return true;
    }

    @Override
    public boolean blocksInteraction() {
        return true;
    }

    @Override
    public void removed() {
        super.removed();
        this.finishAction.run();
    }

    private static class ColorSlider extends SliderButton<ConfigInteger> {
        private final Runnable onModify;
        private final Consumer<SliderButton<?>> renderColorBar;

        public ColorSlider(int buttonLeftX, int realY, ConfigInteger configInteger, Runnable onModify, Consumer<SliderButton<?>> renderColorBar) {
            super(buttonLeftX, realY, 108, 14, configInteger);
            this.onModify = onModify;
            this.renderColorBar = renderColorBar;
        }

        @Override
        public boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
            if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
                onModify.run();
                return true;
            }
            return false;
        }

        @Override
        public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
            this.renderButtonGeneric(mouseX, mouseY, selected, drawContext);
            if (this.enabled) {
                if (this.visible) {
                    if (this.dragging) {
                        this.sliderRatio = this.getRatioFromSlider(mouseX);
                        this.config.setValueByRatio(this.sliderRatio);
                        this.updateString();
                        this.onModify.run();
                    }
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    this.renderColorBar.accept(this);
                    this.bindTexture(BUTTON_TEXTURES);
                    int upper = this.height / 2;
                    int lower = this.height - upper;
                    int xPos = this.x + (int) (this.sliderRatio * (float) (this.width - 8));
                    int yPos = this.y;
                    RenderUtils.drawTexturedRect(xPos,
                            yPos,
                            0,
                            66,
                            4,
                            upper);//left up
                    RenderUtils.drawTexturedRect(xPos + 4,
                            yPos,
                            196,
                            66,
                            4,
                            upper);//right up
                    RenderUtils.drawTexturedRect(xPos,
                            yPos + upper,
                            0,
                            66 + 20 - lower,
                            4,
                            lower);//left down
                    RenderUtils.drawTexturedRect(xPos + 4,
                            yPos + upper,
                            196,
                            66 + 20 - lower,
                            4,
                            lower);//right down
                    this.renderDisplayString(drawContext);
                }
            }
        }
    }
}
