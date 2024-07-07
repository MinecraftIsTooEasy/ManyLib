package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.config.ConfigManager;
import net.minecraft.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "runTick", at = @At("TAIL"))
    private void hotKeyListener(CallbackInfo ci) {
        ConfigManager.getInstance().getAllHotKeys().forEach(x -> {
            if (x.isPressed()) {
                x.onPressed(Minecraft.getMinecraft());
            }
        });
    }
}
