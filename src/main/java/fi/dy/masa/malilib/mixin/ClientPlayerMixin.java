package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.inventory.ContainerHandler;
import net.minecraft.ClientPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayer.class)
public class ClientPlayerMixin {
    @Inject(method = "closeScreen", at = @At("RETURN"))
    private void onContainerClosed(CallbackInfo ci) {
        ContainerHandler.clear();// maybe free some memory
    }
}
