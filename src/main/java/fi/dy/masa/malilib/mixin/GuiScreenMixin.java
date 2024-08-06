package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.event.InputEventHandler;
import net.minecraft.GuiScreen;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public class GuiScreenMixin {
    @Inject(method = "handleKeyboardInput", at = @At("HEAD"))
    private void onKeyInput(CallbackInfo ci) {
        ((InputEventHandler) InputEventHandler.getInputManager()).onKeyInput(Keyboard.getEventKey(), 0, 0, Keyboard.getEventKeyState() ? GLFW.GLFW_PRESS : GLFW.GLFW_RELEASE);
    }
}
