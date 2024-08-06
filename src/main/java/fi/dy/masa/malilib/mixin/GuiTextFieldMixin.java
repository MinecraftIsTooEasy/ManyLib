package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.event.InputEventHandler;
import net.minecraft.GuiTextField;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiTextField.class)
public class GuiTextFieldMixin {
    @Inject(method = "setFocused", at = @At("RETURN"))
    private void stopKeyListening(boolean par1, CallbackInfo ci) {
        ((InputEventHandler) InputEventHandler.getInputManager()).setTexting(par1);
    }
}
