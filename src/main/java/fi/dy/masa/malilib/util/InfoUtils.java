package fi.dy.masa.malilib.util;

import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.gui.Message.MessageType;
import fi.dy.masa.malilib.render.MessageRenderer;
import fi.dy.masa.malilib.render.RenderUtils;
import net.minecraft.GuiIngame;
import net.minecraft.Minecraft;
import net.minecraft.World;

public class InfoUtils {
    private static final MessageRenderer IN_GAME_MESSAGES = new MessageRenderer(0xA0000000, 0).setBackgroundStyle(true, false).setCentered(true, false).setExpandUp(true);

    //    public static final IStringConsumer INFO_MESSAGE_CONSUMER = new InfoMessageConsumer();
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open.
//     *
//     * @param type
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiMessage(MessageType type, String translationKey, Object... args) {
//        showGuiMessage(type, 5000, translationKey, args);
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open.
//     *
//     * @param type
//     * @param lifeTime
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiMessage(MessageType type, int lifeTime, String translationKey, Object... args) {
//        if (GuiUtils.getCurrentScreen() instanceof IMessageConsumer) {
//            ((IMessageConsumer) GuiUtils.getCurrentScreen()).addMessage(type, lifeTime, translationKey, args);
//        }
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open. Otherwise prints the message to the action bar.
//     *
//     * @param type
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiOrActionBarMessage(MessageType type, String translationKey, Object... args) {
//        showGuiOrActionBarMessage(type, 5000, translationKey, args);
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open. Otherwise prints the message to the action bar.
//     *
//     * @param type
//     * @param lifeTime
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiOrActionBarMessage(MessageType type, int lifeTime, String translationKey, Object... args) {
//        if (GuiUtils.getCurrentScreen() instanceof IMessageConsumer) {
//            ((IMessageConsumer) GuiUtils.getCurrentScreen()).addMessage(type, lifeTime, translationKey, args);
//        } else {
//            String msg = type.getFormatting() + StringUtils.translate(translationKey, args) + GuiBase.TXT_RST;
//            printActionbarMessage(msg);
//        }
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open. Otherwise adds the message to the in-game message handler.
//     *
//     * @param type
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiOrInGameMessage(MessageType type, String translationKey, Object... args) {
//        showGuiOrInGameMessage(type, 5000, translationKey, args);
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open. Otherwise adds the message to the in-game message handler.
//     *
//     * @param type
//     * @param lifeTime
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiOrInGameMessage(MessageType type, int lifeTime, String translationKey, Object... args) {
//        if (GuiUtils.getCurrentScreen() instanceof IMessageConsumer) {
//            ((IMessageConsumer) GuiUtils.getCurrentScreen()).addMessage(type, lifeTime, translationKey, args);
//        } else {
//            showInGameMessage(type, lifeTime, translationKey, args);
//        }
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open.
//     * Also shows the message in the in-game message box.
//     *
//     * @param type
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiAndInGameMessage(MessageType type, String translationKey, Object... args) {
//        showGuiAndInGameMessage(type, 5000, translationKey, args);
//    }
//
//    /**
//     * Adds the message to the current GUI's message handler, if there is currently
//     * an IMessageConsumer GUI open.
//     * Also shows the message in the in-game message box.
//     *
//     * @param type
//     * @param lifeTime
//     * @param translationKey
//     * @param args
//     */
//    public static void showGuiAndInGameMessage(MessageType type, int lifeTime, String translationKey, Object... args) {
//        showGuiMessage(type, lifeTime, translationKey, args);
//        showInGameMessage(type, lifeTime, translationKey, args);
//    }
//
    public static void printActionbarMessage(String key, Object... args) {
//        sendVanillaMessage(Text.translatable(key, args));
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
        String status = StringUtils.translate("toggle." + (newValue ? "on" : "off"));
        String message = StringUtils.translate("manyLib.configToggle.toggle", GuiBase.TXT_AQUA + prettyName + GuiBase.TXT_RST, pre + status + GuiBase.TXT_RST);

        printActionbarMessage(message);
    }

    /**
     * NOT PUBLIC API - DO NOT CALL
     */
//    public static void renderInGameMessages(net.minecraft.client.gui.DrawContext drawContext) {
    public static void renderInGameMessages(GuiIngame guiIngame) {
        int x = GuiUtils.getScaledWindowWidth() / 2;
        int y = GuiUtils.getScaledWindowHeight() - 76;
//        IN_GAME_MESSAGES.drawMessages(x, y, drawContext);
        IN_GAME_MESSAGES.drawMessages(x, y, guiIngame);
    }

    public static void sendVanillaMessage(String message) {
        World world = Minecraft.getMinecraft().theWorld;

        if (world != null) {
            RenderUtils.setGuiIngameInfo(message);
//            MinecraftClient.getInstance().inGameHud.setOverlayMessage(message, false);
        }
    }
//
//    public static class InfoMessageConsumer implements IStringConsumer {
//        @Override
//        public void setString(String string) {
//            printActionbarMessage(string);
//        }
//    }
}
