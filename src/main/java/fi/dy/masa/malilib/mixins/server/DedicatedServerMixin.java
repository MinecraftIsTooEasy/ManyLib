package fi.dy.masa.malilib.mixins.server;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.minecraft.DedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DedicatedServer.class)
public class DedicatedServerMixin {
    @Inject(method = "startServer", at = @At(value = "RETURN", ordinal = 1))
    private void onServerStarted(CallbackInfoReturnable<Boolean> cir) {
        ((InitializationHandler) InitializationHandler.getInstance()).onInitialization();
    }
}
