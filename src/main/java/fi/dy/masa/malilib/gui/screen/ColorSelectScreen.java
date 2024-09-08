package fi.dy.masa.malilib.gui.screen;

import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.screen.interfaces.ScreenParented;
import fi.dy.masa.malilib.gui.widgets.WidgetBase;
import fi.dy.masa.malilib.gui.widgets.WidgetContainer;
import fi.dy.masa.malilib.gui.widgets.WidgetText;
import net.minecraft.GuiScreen;

public class ColorSelectScreen extends ScreenParented {
    public ColorSelectScreen(GuiScreen parentScreen) {
        super(parentScreen);
        this.container = new ColorSelectContainer(this);
    }

    static class ColorSelectContainer extends WidgetContainer {
        ColorSelectScreen screen;

        public ColorSelectContainer(ColorSelectScreen screen) {
            super(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
            this.screen = screen;
        }

        @Override
        public void initWidgets() {
            this.subWidgets.clear();
            this.addWidget(WidgetText.of("114514").position(this.width / 2, 40).centered(true));
            this.addWidget(new ColorBoardBeta(this.width / 2 - 100, this.height / 2 - 100, 200, 200));
        }
    }

    static class ColorBoardBeta extends WidgetBase {

        public ColorBoardBeta(int x, int y, int width, int height) {
            super(x, y, width, height);
        }

        @Override
        public void render(int mouseX, int mouseY, boolean selected, DrawContext drawContext) {
            super.render(mouseX, mouseY, selected, drawContext);

//            GL11.glPushMatrix();
//
//            int xPos, yPos;
//            for (int i = 0; i < this.width; i++) {
//                for (int j = 0; j < this.height; j++) {
//                    xPos = i + this.x;
//                    yPos = j + this.y;
//                    int hash = i * this.width * this.height + j;
//                    double mapped = (double) hash / ((this.width - 1) * this.width * this.height + this.height - 1);
//                    int color = (int) (mapped * 256 * 256 * 256);
//                    GL11.glDrawPixels(1,1,1,1,1);
//                }
//            }
//
//            RenderUtils.drawRect(1, 1, 1, 1, 1);
        }
    }
}
