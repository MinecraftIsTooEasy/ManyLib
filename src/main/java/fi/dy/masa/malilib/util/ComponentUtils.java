package fi.dy.masa.malilib.util;

import fi.dy.masa.malilib.config.interfaces.IConfigBase;
import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.EnumChatFormatting;

public class ComponentUtils {
    public static String ofBoolean(IConfigBase config) {
        boolean value;
        if (config instanceof IConfigBoolean configBoolean) {
            value = configBoolean.getBooleanValue();
        } else {
            value = false;
        }
        return ofBoolean(value);
    }

    public static String ofBoolean(boolean b) {
        if (b) {
            return EnumChatFormatting.GREEN + ScreenText.TRUE.translate();
        } else {
            return EnumChatFormatting.RED + ScreenText.FALSE.translate();
        }
    }
}
