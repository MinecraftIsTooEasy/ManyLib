package fi.dy.masa.malilib.client.util;

import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.gui.Message.MessageType;
import fi.dy.masa.malilib.client.render.MessageRenderer;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.Minecraft;
import net.minecraft.World;

public class InfoUtils {
    private static final MessageRenderer IN_GAME_MESSAGES = new MessageRenderer(0xA0000000, 0)
            .setBackgroundStyle(true, false)
            .setCentered(true, false)
            .setExpandUp(true);

    public static void printActionbarMessage(String key, Object... args) {
        sendVanillaMessage(StringUtils.translate(key, args));
    }

    /**
     * Adds the message to the in-game message handler
     *
     * @param type
     * @param translationKey
     * @param args
     */
    public static void showInGameMessage(MessageType type, String translationKey, Object... args) {
        showInGameMessage(type, 5000, translationKey, args);
    }

    /**
     * Adds the message to the in-game message handler
     *
     * @param type
     * @param lifeTime
     * @param translationKey
     * @param args
     */
    public static void showInGameMessage(MessageType type, int lifeTime, String translationKey, Object... args) {
        IN_GAME_MESSAGES.addMessage(type, lifeTime, translationKey, args);
    }

    public static void printBooleanConfigToggleMessage(String prettyName, boolean newValue) {
        String pre = newValue ? GuiBase.TXT_GREEN : GuiBase.TXT_RED;
        String status = newValue ? ScreenText.ON.translate() : ScreenText.OFF.translate();
        String message = ScreenText.TOGGLE.translate(GuiBase.TXT_AQUA + prettyName + GuiBase.TXT_RST, pre + status + GuiBase.TXT_RST);

        printActionbarMessage(message);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
    public static void renderInGameMessages(DrawContext drawContext) {
        int x = GuiUtils.getScaledWindowWidth() / 2;
        int y = GuiUtils.getScaledWindowHeight() - 76;

        IN_GAME_MESSAGES.drawMessages(x, y, drawContext);
    }

    public static void sendVanillaMessage(String message) {
        World world = Minecraft.getMinecraft().theWorld;

        if (world != null) {
            RenderUtils.setOverlayMessage(message);
        }
    }
}
