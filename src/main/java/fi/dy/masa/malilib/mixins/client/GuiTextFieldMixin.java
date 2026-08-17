package fi.dy.masa.malilib.mixins.client;

import fi.dy.masa.malilib.client.event.InputEventHandler;
import fi.dy.masa.malilib.mixin.interfaces.IPositionMutable;
import net.minecraft.GuiTextField;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiTextField.class)
public class GuiTextFieldMixin implements IPositionMutable {
    @Mutable
    @Shadow
    @Final
    public int xPos;

    @Mutable
    @Shadow
    @Final
    public int yPos;

    @Inject(method = "setFocused", at = @At("RETURN"))
    private void stopKeyListening(boolean par1, CallbackInfo ci) {
        ((InputEventHandler) InputEventHandler.getInputManager()).setTexting(par1);
    }


    @Override
    public void manylib$setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }
}
