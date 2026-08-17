package fi.dy.masa.malilib.client.util;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.mixin.interfaces.IGuiIngame;
import net.minecraft.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.util.ArrayList;
import java.util.List;

public class RenderUtils {
    public static void setOverlayMessage(String string) {
        setOverlayMessage(string, 60);
    }

    public static void setOverlayMessage(String string, int time) {
        ((IGuiIngame) Minecraft.getMinecraft().ingameGUI).manylib$setOverlayMessage(string, time);
    }

    public static void renderTooltip(int x, int y, List<String> textLines, DrawContext drawContext) {
        renderTooltipMalilib(x, y, textLines, drawContext);
    }

    private static void renderTooltipMalilib(int x, int y, List<String> textLines, DrawContext drawContext) {
        Minecraft mc = mc();

        if (textLines.isEmpty() == false && GuiUtils.getCurrentScreen() != null) {
//            RenderSystem.enableDepthTest();

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glDisable(GL11.GL_DEPTH_TEST);// copied from ...

            FontRenderer font = mc.fontRenderer;
            int maxLineLength = 0;
            int maxWidth = GuiUtils.getCurrentScreen().width;
            List<String> linesNew = new ArrayList<>();

            for (String lineOrig : textLines) {
                String[] lines = lineOrig.split("\\n");

                for (String line : lines) {
                    int length = font.getStringWidth(line);

                    if (length > maxLineLength) {
                        maxLineLength = length;
                    }

                    linesNew.add(line);
                }
            }

            textLines = linesNew;

            final int lineHeight = font.FONT_HEIGHT + 1;
            int textHeight = textLines.size() * lineHeight - 2;
            int textStartX = x + 4;
            int textStartY = Math.max(8, y - textHeight - 6);

            if (textStartX + maxLineLength + 6 > maxWidth) {
                textStartX = Math.max(2, maxWidth - maxLineLength - 8);
            }

//            MatrixStack matrixStack = drawContext.getMatrices();
//            matrixStack.push();
//            matrixStack.translate(0, 0, 300);
//            RenderSystem.applyModelViewMatrix();

            double zLevel = 300;
            int borderColor = 0xF0100010;
            drawGradientRect(textStartX - 3, textStartY - 4, textStartX + maxLineLength + 3, textStartY - 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 3, textStartY + textHeight + 3, textStartX + maxLineLength + 3, textStartY + textHeight + 4, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 3, textStartY - 3, textStartX + maxLineLength + 3, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX - 4, textStartY - 3, textStartX - 3, textStartY + textHeight + 3, zLevel, borderColor, borderColor);
            drawGradientRect(textStartX + maxLineLength + 3, textStartY - 3, textStartX + maxLineLength + 4, textStartY + textHeight + 3, zLevel, borderColor, borderColor);

            int fillColor1 = 0x505000FF;
            int fillColor2 = 0x5028007F;
            drawGradientRect(textStartX - 3, textStartY - 3 + 1, textStartX - 3 + 1, textStartY + textHeight + 3 - 1, zLevel, fillColor1, fillColor2);
            drawGradientRect(textStartX + maxLineLength + 2, textStartY - 3 + 1, textStartX + maxLineLength + 3, textStartY + textHeight + 3 - 1, zLevel, fillColor1, fillColor2);
            drawGradientRect(textStartX - 3, textStartY - 3, textStartX + maxLineLength + 3, textStartY - 3 + 1, zLevel, fillColor1, fillColor1);
            drawGradientRect(textStartX - 3, textStartY + textHeight + 2, textStartX + maxLineLength + 3, textStartY + textHeight + 3, zLevel, fillColor2, fillColor2);

            for (int i = 0; i < textLines.size(); ++i) {
                String str = textLines.get(i);

                drawContext.drawText(font, str, textStartX, textStartY, 0xFFFFFFFF, false);
                textStartY += lineHeight;
            }

//            matrixStack.pop();
//            RenderSystem.applyModelViewMatrix();

            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_DEPTH_TEST);// copied from ...

            //RenderSystem.enableDepthTest();
            //enableDiffuseLightingGui3D();
        }
    }

    private static void renderTooltipModMenu(List<String> text, int x, int y, GuiScreen screen) {
        if (!text.isEmpty()) {
            int n2 = 0;

            for (String string : text) {
                int n = screen.fontRenderer.getStringWidth(string);
                if (n > n2) {
                    n2 = n;
                }
            }

            int n3 = x + 12;
            int n4 = y - 12;
            int n5 = 8;
            if (text.size() > 1) {
                n5 += 2 + (text.size() - 1) * 10;
            }

            if (n3 + n2 > screen.width) {
                n3 -= 28 + n2;
            }

            if (n4 + n5 + 6 > screen.height) {
                n4 = screen.height - n5 - 6;
            }

//            screen.zLevel = 300.0F;
//            itemRenderer.zLevel = 300.0F;
            int n6 = -267386864;
            screen.drawGradientRect(n3 - 3, n4 - 4, n3 + n2 + 3, n4 - 3, n6, n6);
            screen.drawGradientRect(n3 - 3, n4 + n5 + 3, n3 + n2 + 3, n4 + n5 + 4, n6, n6);
            screen.drawGradientRect(n3 - 3, n4 - 3, n3 + n2 + 3, n4 + n5 + 3, n6, n6);
            screen.drawGradientRect(n3 - 4, n4 - 3, n3 - 3, n4 + n5 + 3, n6, n6);
            screen.drawGradientRect(n3 + n2 + 3, n4 - 3, n3 + n2 + 4, n4 + n5 + 3, n6, n6);
            int n7 = 1347420415;
            int n8 = (n7 & 16711422) >> 1 | n7 & -16777216;
            screen.drawGradientRect(n3 - 3, n4 - 3 + 1, n3 - 3 + 1, n4 + n5 + 3 - 1, n7, n8);
            screen.drawGradientRect(n3 + n2 + 2, n4 - 3 + 1, n3 + n2 + 3, n4 + n5 + 3 - 1, n7, n8);
            screen.drawGradientRect(n3 - 3, n4 - 3, n3 + n2 + 3, n4 - 3 + 1, n7, n7);
            screen.drawGradientRect(n3 - 3, n4 + n5 + 2, n3 + n2 + 3, n4 + n5 + 3, n8, n8);

            for (int i = 0; i < text.size(); ++i) {
                String string = text.get(i);
                screen.fontRenderer.drawStringWithShadow(string, n3, n4, -1);
                if (i == 0) {
                    n4 += 2;
                }

                n4 += 10;
            }

//            screen.zLevel = 0.0F;
//            itemRenderer.zLevel = 0.0F;
        }
    }

    private static void renderTooltipVanilla(List<String> stringList, int x, int y, boolean has_title, DrawContext drawContext) {
        if (stringList.isEmpty()) return;
        FontRenderer fontRenderer = fontRenderer();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
//        RenderHelper.disableStandardItemLighting();// bad
//        GL11.glDisable(GL11.GL_LIGHTING);// bad
        GL11.glDisable(GL11.GL_DEPTH_TEST);// good
        int boxWidth = 0;
        int stringWidth;
        for (String textPart : stringList) {
            stringWidth = fontRenderer.getStringWidth(textPart);
            if (stringWidth > boxWidth) {
                boxWidth = stringWidth;
            }
        }
        int stringXPos = x + 12;
        int stringYPos = y - 12;
        int boxHeight = 8;
        if (stringList.size() > 1) {
            boxHeight += 2 + (stringList.size() - 1) * 10;
        }
        if (!has_title) {
            boxHeight -= 2;
        }
        if (stringXPos + boxWidth > GuiUtils.getScaledWindowWidth()) {
            stringXPos -= 28 + boxWidth;
        }
        int height = GuiUtils.getScaledWindowHeight();
        if (stringYPos + boxHeight + 6 > height) {
            stringYPos = height - boxHeight - 6;
        }
//        screen.zLevel = 300.0F;
        int color_1 = -267386864;
        color_1 = color_1 & 16777215 | -369098752;
        drawContext.drawGradientRect(stringXPos - 3, stringYPos - 4, stringXPos + boxWidth + 3, stringYPos - 3, color_1, color_1);
        drawContext.drawGradientRect(stringXPos - 3, stringYPos + boxHeight + 3, stringXPos + boxWidth + 3, stringYPos + boxHeight + 4, color_1, color_1);
        drawContext.drawGradientRect(stringXPos - 3, stringYPos - 3, stringXPos + boxWidth + 3, stringYPos + boxHeight + 3, color_1, color_1);
        drawContext.drawGradientRect(stringXPos - 4, stringYPos - 3, stringXPos - 3, stringYPos + boxHeight + 3, color_1, color_1);
        drawContext.drawGradientRect(stringXPos + boxWidth + 3, stringYPos - 3, stringXPos + boxWidth + 4, stringYPos + boxHeight + 3, color_1, color_1);
        int color_2 = 1347420415;
        int color_3 = (color_2 & 16711422) >> 1 | color_2 & -16777216;
        drawContext.drawGradientRect(stringXPos - 3, stringYPos - 3 + 1, stringXPos - 3 + 1, stringYPos + boxHeight + 3 - 1, color_2, color_3);
        drawContext.drawGradientRect(stringXPos + boxWidth + 2, stringYPos - 3 + 1, stringXPos + boxWidth + 3, stringYPos + boxHeight + 3 - 1, color_2, color_3);
        drawContext.drawGradientRect(stringXPos - 3, stringYPos - 3, stringXPos + boxWidth + 3, stringYPos - 3 + 1, color_2, color_2);
        drawContext.drawGradientRect(stringXPos - 3, stringYPos + boxHeight + 2, stringXPos + boxWidth + 3, stringYPos + boxHeight + 3, color_3, color_3);

        for (int stringIndex = 0; stringIndex < stringList.size(); ++stringIndex) {
            String string = stringList.get(stringIndex);
            fontRenderer.drawStringWithShadow(string, stringXPos, stringYPos, -1);
            if (stringIndex == 0 && has_title) {
                stringYPos += 2;
            }
            stringYPos += 10;
        }
//        screen.zLevel = 0.0F;
//        GL11.glEnable(GL11.GL_LIGHTING);// bad
        GL11.glEnable(GL11.GL_DEPTH_TEST);// good
//        RenderHelper.enableStandardItemLighting();// bad
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
    }

    public static void bindTexture(ResourceLocation texture) {
        mc().getTextureManager().bindTexture(texture);
    }

    public static void color(float r, float g, float b, float a) {
        GL11.glColor4f(r, g, b, a);
    }

    public static void drawOutlinedBox(int x, int y, int width, int height, int colorBg, int colorBorder) {
        drawOutlinedBox(x, y, width, height, colorBg, colorBorder, 0f);
    }

    public static void drawOutlinedBox(int x, int y, int width, int height, int colorBg, int colorBorder, float zLevel) {
        // Draw the background
        drawRect(x, y, width, height, colorBg, zLevel);

        // Draw the border
        drawOutline(x - 1, y - 1, width + 2, height + 2, colorBorder, zLevel);
    }

    public static void drawOutline(int x, int y, int width, int height, int colorBorder) {
        drawOutline(x, y, width, height, colorBorder, 0f);
    }

    public static void drawOutline(int x, int y, int width, int height, int colorBorder, float zLevel) {
        drawRect(x, y, 1, height, colorBorder, zLevel); // left edge
        drawRect(x + width - 1, y, 1, height, colorBorder, zLevel); // right edge
        drawRect(x + 1, y, width - 2, 1, colorBorder, zLevel); // top edge
        drawRect(x + 1, y + height - 1, width - 2, 1, colorBorder, zLevel); // bottom edge
    }


    public static void drawOutline(int x, int y, int width, int height, int borderWidth, int colorBorder) {
        drawOutline(x, y, width, height, borderWidth, colorBorder, 0f);
    }

    public static void drawOutline(int x, int y, int width, int height, int borderWidth, int colorBorder, float zLevel) {
        drawRect(x, y, borderWidth, height, colorBorder, zLevel); // left edge
        drawRect(x + width - borderWidth, y, borderWidth, height, colorBorder, zLevel); // right edge
        drawRect(x + borderWidth, y, width - 2 * borderWidth, borderWidth, colorBorder, zLevel); // top edge
        drawRect(x + borderWidth, y + height - borderWidth, width - 2 * borderWidth, borderWidth, colorBorder, zLevel); // bottom edge
    }

    public static void drawTexturedRect(int x, int y, int u, int v, int width, int height) {
        drawTexturedRect(x, y, u, v, width, height, 0);
    }


    public static void drawRect(int x, int y, int width, int height, int color) {
        drawRect(x, y, width, height, color, 0f);
    }

    public static void drawRect(int x, int y, int width, int height, int color, float zLevel) {
        float a = (float) (color >> 24 & 0xFF) / 255.0f;
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, a);
        tessellator.startDrawingQuads();
        tessellator.addVertex(x, y, zLevel);
        tessellator.addVertex(x, y + height, zLevel);
        tessellator.addVertex(x + width, y + height, zLevel);
        tessellator.addVertex(x + width, y, zLevel);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawCircle(int x, int y, int radius, int color) {
        drawCircle(x, y, radius, color, 0F);
    }

    public static void drawCircle(int x, int y, int radius, int color, float zLevel) {
        float a = (float) (color >> 24 & 0xFF) / 255.0f;
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, a);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_LINE_LOOP);
        int drawNum = 360;
        for (int i = 0; i < drawNum; i++) {
            float angle = (float) (2 * Math.PI * i / drawNum);
            tessellator.addVertex(x + radius * MathHelper.cos(angle), y - radius * MathHelper.sin(angle), zLevel);
        }
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawDisk(int x, int y, float radius, int color) {
        drawDisk(x, y, radius, color, 0F);
    }

    public static void drawDisk(int x, int y, float radius, int color, float zLevel) {
        float a = (float) (color >> 24 & 0xFF) / 255.0f;
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(r, g, b, a);
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawing(GL11.GL_TRIANGLE_FAN);
        tessellator.addVertex(x, y, zLevel);
        int drawNum = 361;
        for (int i = 0; i <= drawNum; i++) {
            float angle = (float) (2 * Math.PI * i / drawNum);
            tessellator.addVertex(x + radius * MathHelper.cos(angle), y - radius * MathHelper.sin(angle), zLevel);
        }
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawTexturedRect(int x, int y, int u, int v, int width, int height, float zLevel) {
        float pixelWidth = 0.00390625F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, zLevel, u * pixelWidth, (v + height) * pixelWidth);
        tessellator.addVertexWithUV(x + width, y + height, zLevel, (u + width) * pixelWidth, (v + height) * pixelWidth);
        tessellator.addVertexWithUV(x + width, y, zLevel, (u + width) * pixelWidth, v * pixelWidth);
        tessellator.addVertexWithUV(x, y, zLevel, u * pixelWidth, v * pixelWidth);
        tessellator.draw();
    }

    public static void preDrawRect() {
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void postDrawRect() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawGradientRect(int left, int top, int right, int bottom, double zLevel, int startColor, int endColor) {
        preRenderGradient();
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        bufferGradientVertical(left, top, right, bottom, zLevel, startColor, endColor, tessellator);
        tessellator.draw();
        postRenderGradient();
    }

    public static void preRenderGradient() {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glShadeModel(GL11.GL_SMOOTH);
    }

    public static void postRenderGradient() {
        GL11.glShadeModel(GL11.GL_FLAT);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public static void bufferGradientHorizontal(int left, int top, int right, int bottom, double zLevel, int startColor, int endColor, Tessellator tessellator) {
        float startA = (float) (startColor >> 24 & 0xFF) / 255.0f;
        float startR = (float) (startColor >> 16 & 0xFF) / 255.0f;
        float startG = (float) (startColor >> 8 & 0xFF) / 255.0f;
        float startB = (float) (startColor & 0xFF) / 255.0f;
        float endA = (float) (endColor >> 24 & 0xFF) / 255.0f;
        float endR = (float) (endColor >> 16 & 0xFF) / 255.0f;
        float endG = (float) (endColor >> 8 & 0xFF) / 255.0f;
        float endB = (float) (endColor & 0xFF) / 255.0f;
        tessellator.setColorRGBA_F(startR, startG, startB, startA);
        tessellator.addVertex(left, top, zLevel);
        tessellator.addVertex(left, bottom, zLevel);
        tessellator.setColorRGBA_F(endR, endG, endB, endA);
        tessellator.addVertex(right, bottom, zLevel);
        tessellator.addVertex(right, top, zLevel);
    }

    public static void bufferGradientVertical(int left, int top, int right, int bottom, double zLevel, int startColor, int endColor, Tessellator tessellator) {
        float startA = (float) (startColor >> 24 & 0xFF) / 255.0f;
        float startR = (float) (startColor >> 16 & 0xFF) / 255.0f;
        float startG = (float) (startColor >> 8 & 0xFF) / 255.0f;
        float startB = (float) (startColor & 0xFF) / 255.0f;
        float endA = (float) (endColor >> 24 & 0xFF) / 255.0f;
        float endR = (float) (endColor >> 16 & 0xFF) / 255.0f;
        float endG = (float) (endColor >> 8 & 0xFF) / 255.0f;
        float endB = (float) (endColor & 0xFF) / 255.0f;
        tessellator.setColorRGBA_F(startR, startG, startB, startA);
        tessellator.addVertex(right, top, zLevel);
        tessellator.addVertex(left, top, zLevel);
        tessellator.setColorRGBA_F(endR, endG, endB, endA);
        tessellator.addVertex(left, bottom, zLevel);
        tessellator.addVertex(right, bottom, zLevel);
    }

    public static void drawCenteredString(int x, int y, int color, String text, DrawContext drawContext) {
        FontRenderer textRenderer = mc().fontRenderer;
        drawContext.drawCenteredTextWithShadow(textRenderer, text, x, y, color);
    }

    public static void drawHorizontalLine(int x, int y, int width, int color) {
        drawRect(x, y, width, 1, color);
    }

    public static void drawVerticalLine(int x, int y, int height, int color) {
        drawRect(x, y, 1, height, color);
    }

    public static void startScissor(int x, int y, int width, int height) {
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int scaledWindowHeight = GuiUtils.getScaledWindowHeight();
        y = scaledWindowHeight - y - height;// the gl count from left bottom
        int scaleFactor = GuiUtils.getScaleFactor();
        GL11.glScissor(x * scaleFactor, y * scaleFactor, width * scaleFactor, height * scaleFactor);
    }

    public static void endScissor() {
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
    }

    private static Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static FontRenderer fontRenderer() {
        return mc().fontRenderer;
    }

    public static void drawString(int x, int y, int color, String text, DrawContext drawContext) {
        drawContext.drawText(Minecraft.getMinecraft().fontRenderer, text, x, y, color, false);
    }
}
