package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.config.options.ConfigColor;
import fi.dy.masa.malilib.config.options.ConfigInteger;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.button.SliderButton;
import fi.dy.masa.malilib.gui.screen.util.ColorBoardSV;
import fi.dy.masa.malilib.gui.screen.util.HSV;
import fi.dy.masa.malilib.gui.screen.util.RGB;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import fi.dy.masa.malilib.util.SystemUtils;
import net.minecraft.GuiScreen;
import net.minecraft.Tessellator;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class ColorSelectScreen extends GuiBase {

    private final ConfigColor configColor;

    private ColorBoardSV colorBoard;

    private final ConfigInteger a;

    private final ConfigInteger h;

    private final ConfigInteger r;
    private final ConfigInteger g;
    private final ConfigInteger b;

    private final List<SliderButton<ConfigInteger>> sliderButtons = new ArrayList<>();


    public ColorSelectScreen(GuiScreen parentScreen, ConfigColor configColor) {
        super();
        this.setParent(parentScreen);
        this.setTitle(StringUtils.translate("manyLib.gui.title.selectColor"));
        this.configColor = configColor;

        int[] decodedARGB = decodeARGB(configColor.getDefaultColor().intValue);

        int defaultR = decodedARGB[1];
        int defaultG = decodedARGB[2];
        int defaultB = decodedARGB[3];

        int[] defaultHSV = RGB.ofIII(defaultR, defaultG, defaultB).toHSV().standardize();

        int defaultH = defaultHSV[0];

        this.a = new ConfigInteger("A", decodedARGB[0], 0, 255);

        this.h = new ConfigInteger("NewH", defaultH, 0, 359);

        this.r = new ConfigInteger("R", defaultR, 0, 255);
        this.g = new ConfigInteger("G", defaultG, 0, 255);
        this.b = new ConfigInteger("B", defaultB, 0, 255);

        this.updateRGB();
        HSV hsv = HSV.ofARGB(this.configColor.getColorInteger());
        this.h.setIntegerValue(hsv.getH());
    }

    public static int[] decodeARGB(int color) {
        return new int[]{(color & 0xFF000000) >>> 24, (color & 0x00FF0000) >>> 16, (color & 0x0000FF00) >>> 8, color & 0x000000FF};
    }

    public static int encodeARGB(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    @Override
    public void initGui() {
        super.initGui();
        int leftX = this.width / 2 - 140;
        int topY = this.height / 2 - 43;

        this.addWidget(WidgetText.of(GuiBase.TXT_AQUA + StringUtils.translate("manyLib.gui.configuring") + ": ")
                .position(leftX + 55, 40).centered());
        this.addWidget(WidgetText.of(GuiBase.TXT_AQUA + this.configColor.getConfigGuiDisplayName())
                .position(leftX + 55, 55).centered());

        this.addLine(leftX, topY, 0, "color.alpha", this.a, this.simple(x -> 0), this::setByA);
        this.addLine(leftX, topY, 1, "color.red", this.r, this.simple(x -> RGB.ofIII((int) (x * 255), this.g.getIntegerValue(), this.b.getIntegerValue()).toColor()), this::setByRGB);
        this.addLine(leftX, topY, 2, "color.green", this.g, this.simple(x -> RGB.ofIII(this.a.getIntegerValue(), (int) (x * 255), this.b.getIntegerValue()).toColor()), this::setByRGB);
        this.addLine(leftX, topY, 3, "color.blue", this.b, this.simple(x -> RGB.ofIII(this.a.getIntegerValue(), this.g.getIntegerValue(), (int) (x * 255)).toColor()), this::setByRGB);

        this.addLine(leftX, topY, 4, "color.hue", this.h, this::renderHueBar, this::setByHSV);

        this.colorBoard = new ColorBoardSV(this.h, this.a, this::setByHSV, () -> SystemUtils.copyToClipboard(this.configColor.getColorString()), this.width / 2 + 20, this.height / 2 - 75, 150, 150);
        HSV hsv = HSV.ofARGB(this.configColor.getColorInteger());
        this.colorBoard.setSV(hsv.getS(), hsv.getV());
        this.addWidget(this.colorBoard);
        this.updateHoverString();
    }

    private void setByA() {
        this.configColor.setIntegerValue(encodeARGB(this.a.getIntegerValue(), this.r.getIntegerValue(), this.g.getIntegerValue(), this.b.getIntegerValue()));
        this.updateHoverString();
    }

    private void setByRGB() {
        this.configColor.setIntegerValue(encodeARGB(this.a.getIntegerValue(), this.r.getIntegerValue(), this.g.getIntegerValue(), this.b.getIntegerValue()));
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
        int realY = topY + index * 22;
        this.addWidget(WidgetText.of(StringUtils.translate(key)).position(buttonLeftX - 35, realY + 6));

        SliderButton<ConfigInteger> slider = new SliderButton<>(buttonLeftX, realY, 120, 20, configInteger) {
            @Override
            public boolean onMouseClickedImpl(int mouseX, int mouseY, int mouseButton) {
                if (super.onMouseClickedImpl(mouseX, mouseY, mouseButton)) {
                    onModify.run();
                    return true;
                }
                return false;
            }

            @Override
            public void onMouseDraggedImpl(int mouseX, int mouseY) {
                if (this.enabled) {
                    if (this.visible) {
                        if (this.dragging) {
                            this.sliderRatio = this.getRatioFromSlider(mouseX);
                            this.config.setValueByRatio(this.sliderRatio);
                            this.updateString();
                            onModify.run();
                        }
                        renderColorBar.accept(this);
                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderUtils.drawTexturedRect(this.x + (int) (this.sliderRatio * (float) (this.width - 8)), this.y, 0, 66, 4, 20);
                        RenderUtils.drawTexturedRect(this.x + (int) (this.sliderRatio * (float) (this.width - 8)) + 4, this.y, 196, 66, 4, 20);
                    }
                }
            }
        };
        this.sliderButtons.add(slider);
        this.addButton(slider);
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
}
