package fi.dy.masa.malilib.client.unsafe;

import com.terraformersmc.modmenu.ModMenu;
import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.gui.ModsScreen;
import com.terraformersmc.modmenu.gui.widget.entries.ModListEntry;
import com.terraformersmc.modmenu.util.DrawingUtil;
import com.terraformersmc.modmenu.util.mod.Mod;
import com.terraformersmc.modmenu.util.mod.fabric.FabricIconHandler;
import fi.dy.masa.malilib.api.ManyLibApi;
import fi.dy.masa.malilib.client.api.ManyLibClientApi;
import fi.dy.masa.malilib.mixins.client.mod.modmenu.IModMenuMixin;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import net.minecraft.DynamicTexture;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.minecraft.ResourceLocation;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ModMenuAccess {
    private static final Map<String, ResourceLocation> ICON_MAP = new HashMap<>();
    private static final FabricIconHandler ICON_HANDLER = new FabricIconHandler();

    public static void directToMod(String id, GuiScreen parent) {
        ModsScreen screen = ModMenuApi.createModsScreen(parent);
        parent.mc.displayGuiScreen(screen);
        for (char c : id.toCharArray()) {
            screen.keyTyped(c, c);
        }
        // TODO focus on the mod
    }

    public static void registerFactories() {
        Map<String, ConfigScreenFactory<?>> map = IModMenuMixin.getConfigScreenFactories();
        ManyLibApi.streamIds()
                .filter(x -> !map.containsKey(x))
                .forEach(id -> map.put(id, parent -> ManyLibClientApi.createConfigScreen(id, parent)));
    }

    @Nullable
    public static ResourceLocation getIconTexture(Minecraft client, String id) {
        Mod mod = ModMenu.MODS.get(id);
        if (mod == null) return null;
        ResourceLocation iconLocation = ICON_MAP.get(id);

        if (iconLocation == null) {
            iconLocation = new ResourceLocation("modmenu", mod.getId() + "_icon");
            DynamicTexture icon = mod.getIcon(ICON_HANDLER, 64 * client.gameSettings.guiScale);
            if (icon != null) {
                client.getTextureManager().loadTexture(iconLocation, icon);
            } else {
                iconLocation = ModListEntry.UNKNOWN_ICON;
            }
        }

        return iconLocation;
    }

    public static void drawTexture(int x, int y, float u, float v, int width, int height, float scaleU, float scaleV) {
        DrawingUtil.drawTexture(x, y, u, v, width, height, scaleU, scaleV);
    }
}
