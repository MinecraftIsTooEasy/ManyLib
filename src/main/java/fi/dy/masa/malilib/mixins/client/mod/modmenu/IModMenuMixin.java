package fi.dy.masa.malilib.mixins.client.mod.modmenu;

import com.terraformersmc.modmenu.ModMenu;
import io.github.prospector.modmenu.api.ConfigScreenFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ModMenu.class)
public interface IModMenuMixin {
    @Accessor
    static Map<String, ConfigScreenFactory<?>> getConfigScreenFactories() {
        throw new AssertionError();
    }
}
