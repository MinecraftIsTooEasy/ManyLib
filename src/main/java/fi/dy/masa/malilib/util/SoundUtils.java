package fi.dy.masa.malilib.util;

import net.minecraft.Minecraft;

public class SoundUtils {
    public static void click(Minecraft client) {
        client.sndManager.playSoundFX("random.click", 1.0f, 1.0f);
    }
}
