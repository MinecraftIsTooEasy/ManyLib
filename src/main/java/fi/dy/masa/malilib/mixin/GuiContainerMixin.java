package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.inventory.ContainerHandler;
import net.minecraft.Container;
import net.minecraft.GuiContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public class GuiContainerMixin {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(Container par1Container, CallbackInfo ci) {
        ContainerHandler.updateContainer(par1Container);
    }
}
