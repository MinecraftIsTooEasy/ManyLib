package fi.dy.masa.malilib.util;

import fi.dy.masa.malilib.api.ManyLibGuiIngame;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class RenderUtils {
    public static void setGuiIngameInfo(String string) {
        ((ManyLibGuiIngame) Minecraft.getMinecraft().ingameGUI).manyLib$setInfo(string);
    }

    public static void drawCreativeTabHoveringText(GuiScreen screen, String string, int x, int y) {
        string = string.replace('（', '(').replace('）', ')');
        List<String> list = Arrays.stream(string.split("\\\\n")).map(String::trim).toList();
        if (list.size() == 1 && screen.fontRenderer.getStringWidth(list.get(0)) > 300) {
            list = Arrays.stream(string.split("[,.，。]")).map(String::trim).toList();
        }
        drawTextList(screen, list, x, y);
    }

    public static void drawTextList(GuiScreen screen, List<String> stringList, int x, int y) {
        drawTextList(screen, stringList, x, y, true);
    }

    public static void drawTextList(GuiScreen screen, List<String> stringList, int x, int y, boolean has_title) {
        if (stringList.isEmpty()) return;
        GL11.glDisable(32826);
//        RenderHelper.disableStandardItemLighting();// bad
//        GL11.glDisable(2896);// bad
        GL11.glDisable(2929);// good
        int boxWidth = 0;
        int stringWidth;
        for (String textPart : stringList) {
            stringWidth = screen.fontRenderer.getStringWidth(textPart);
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
        if (stringXPos + boxWidth > screen.width) {
            stringXPos -= 28 + boxWidth;
        }
        if (stringYPos + boxHeight + 6 > screen.height) {
            stringYPos = screen.height - boxHeight - 6;
        }
        screen.zLevel = 300.0F;
        int color_1 = -267386864;
        color_1 = color_1 & 16777215 | -369098752;
        screen.drawGradientRect(stringXPos - 3, stringYPos - 4, stringXPos + boxWidth + 3, stringYPos - 3, color_1, color_1);
        screen.drawGradientRect(stringXPos - 3, stringYPos + boxHeight + 3, stringXPos + boxWidth + 3, stringYPos + boxHeight + 4, color_1, color_1);
        screen.drawGradientRect(stringXPos - 3, stringYPos - 3, stringXPos + boxWidth + 3, stringYPos + boxHeight + 3, color_1, color_1);
        screen.drawGradientRect(stringXPos - 4, stringYPos - 3, stringXPos - 3, stringYPos + boxHeight + 3, color_1, color_1);
        screen.drawGradientRect(stringXPos + boxWidth + 3, stringYPos - 3, stringXPos + boxWidth + 4, stringYPos + boxHeight + 3, color_1, color_1);
        int color_2 = 1347420415;
        int color_3 = (color_2 & 16711422) >> 1 | color_2 & -16777216;
        screen.drawGradientRect(stringXPos - 3, stringYPos - 3 + 1, stringXPos - 3 + 1, stringYPos + boxHeight + 3 - 1, color_2, color_3);
        screen.drawGradientRect(stringXPos + boxWidth + 2, stringYPos - 3 + 1, stringXPos + boxWidth + 3, stringYPos + boxHeight + 3 - 1, color_2, color_3);
        screen.drawGradientRect(stringXPos - 3, stringYPos - 3, stringXPos + boxWidth + 3, stringYPos - 3 + 1, color_2, color_2);
        screen.drawGradientRect(stringXPos - 3, stringYPos + boxHeight + 2, stringXPos + boxWidth + 3, stringYPos + boxHeight + 3, color_3, color_3);

        for (int stringIndex = 0; stringIndex < stringList.size(); ++stringIndex) {
            String string = stringList.get(stringIndex);
            screen.fontRenderer.drawStringWithShadow(string, stringXPos, stringYPos, -1);
            if (stringIndex == 0 && has_title) {
                stringYPos += 2;
            }
            stringYPos += 10;
        }
        screen.zLevel = 0.0F;
//        GL11.glEnable(2896);// bad
        GL11.glEnable(2929);// good
//        RenderHelper.enableStandardItemLighting();// bad
        GL11.glEnable(32826);
    }
}
