package fi.dy.masa.malilib.util;

import net.xiaoyu233.fml.FishModLoader;

import javax.annotation.Nullable;
import java.nio.file.Path;

public class Platform {
    private static final Path GAME_DIR = Path.of("");

    public static boolean isServer() {
        return FishModLoader.isServer();
    }

    public static boolean isClient() {
        return !isServer();
    }

    public static Path getConfigPath() {
        return GAME_DIR.resolve("config");
    }

    public static boolean hasMod(String id) {
        return FishModLoader.hasMod(id);
    }

    @Nullable
    public static String getModName(String id) {
        return FishModLoader.getModContainer(id).map(modContainer -> modContainer.getMetadata().getName()).orElse(null);
    }

    public static boolean isDev() {
        return FishModLoader.isDevelopmentEnvironment();
    }
}
