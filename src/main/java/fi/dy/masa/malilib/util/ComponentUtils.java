package fi.dy.masa.malilib.util;

import fi.dy.masa.malilib.config.interfaces.IConfigBoolean;
import fi.dy.masa.malilib.config.options.ConfigBase;
import fi.dy.masa.malilib.localization.ScreenText;
import net.minecraft.EnumChatFormatting;

public class ComponentUtils {
    public static String ofBoolean(ConfigBase<?> config) {
        return ofBoolean(((IConfigBoolean) config).getBooleanValue());
    }

    public static String ofBoolean(boolean b) {
        if (b) {
            return EnumChatFormatting.GREEN + ScreenText.TRUE.translate();
        } else {
            return EnumChatFormatting.RED + ScreenText.FALSE.translate();
        }
    }
}
