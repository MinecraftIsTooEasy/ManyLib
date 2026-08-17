package fi.dy.masa.malilib.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import fake.org.lwjgl.glfw.GLFW;
import fi.dy.masa.malilib.client.event.InputEventHandler;
import fi.dy.masa.malilib.client.event.TickHandler;
import fi.dy.masa.malilib.client.event.WorldLoadHandler;
import fi.dy.masa.malilib.event.InitializationHandler;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import net.minecraft.WorldClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Shadow
    public WorldClient theWorld;

    @Unique
    private WorldClient worldBefore;

    @Inject(method = "startGame", at = @At("RETURN"))
    private void onStartGameComplete(CallbackInfo ci) {
        ((InitializationHandler) InitializationHandler.getInstance()).onInitialization();
    }

    @Inject(method = "runTick", at = @At("RETURN"))
    private void hotKeyListener(CallbackInfo ci) {
        TickHandler.getInstance().onClientTick((Minecraft) (Object) this);
    }

    @Inject(method = "loadWorld(Lnet/minecraft/WorldClient;Ljava/lang/String;)V", at = @At("HEAD"))
    private void onLoadWorldPre(WorldClient worldClientIn, String par2Str, CallbackInfo ci) {
        if (this.theWorld != null) {
            this.worldBefore = this.theWorld;
        }
        ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPre(this.theWorld, worldClientIn, (Minecraft) (Object) this);
    }

    @Inject(method = "loadWorld(Lnet/minecraft/WorldClient;Ljava/lang/String;)V", at = @At("RETURN"))
    private void onLoadWorldPost(WorldClient worldClientIn, String par2Str, CallbackInfo ci) {
        ((WorldLoadHandler) WorldLoadHandler.getInstance()).onWorldLoadPost(this.worldBefore, worldClientIn, (Minecraft) (Object) this);
        if (this.worldBefore != null) {
            this.worldBefore = null;
        }
    }

    @WrapOperation(method = "runTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/KeyBinding;setKeyBindState(IZ)V", ordinal = 1))
    private void onKeyInput(int i, boolean bl, Operation<Void> original) {
        boolean cancel = ((InputEventHandler) InputEventHandler.getInputManager()).onKeyInput(i, 0, 0, bl ? GLFW.GLFW_PRESS : GLFW.GLFW_RELEASE);
        if (cancel) return;
        original.call(i, bl);
    }

    @Inject(method = "closeImposedChat", at = @At("HEAD"))
    private void onChatClose(CallbackInfo ci) {
        ((InputEventHandler) InputEventHandler.getInputManager()).setTexting(false);
    }

    @Inject(method = "displayGuiScreen", at = @At("HEAD"))
    private void onGuiChange(GuiScreen par1GuiScreen, CallbackInfo ci) {
        if (par1GuiScreen == null) {
            ((InputEventHandler) InputEventHandler.getInputManager()).setTexting(false);
        }
    }
}
