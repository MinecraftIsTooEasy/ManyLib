package fi.dy.masa.malilib.mixins.client;

import fi.dy.masa.malilib.client.event.RenderEventHandler;
import fi.dy.masa.malilib.client.gui.DrawContext;
import fi.dy.masa.malilib.client.gui.GuiBase;
import fi.dy.masa.malilib.client.internal.ManyLibClientConfig;
import fi.dy.masa.malilib.mixin.interfaces.IGuiIngame;
import net.minecraft.Gui;
import net.minecraft.GuiIngame;
import net.minecraft.Minecraft;
import net.minecraft.ScaledResolution;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin extends Gui implements IGuiIngame {
    @Shadow
    @Final
    private Minecraft mc;
    @Unique
    private String overlayMessage;
    @Unique
    private int overlayMessageTime = 0;

    @Override
    public void manylib$setOverlayMessage(String string, int time) {
        this.overlayMessage = string;
        this.overlayMessageTime = time;
    }

    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z", shift = At.Shift.BEFORE))
    private void renderString(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderGameOverlayPost(new DrawContext(), this.mc, par1);

        ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        if (this.overlayMessage == null) return;
        if (this.overlayMessageTime > 0) {
            this.drawCenteredString
                    (this.mc.fontRenderer,
                            this.overlayMessage,
                            sr.getScaledWidth() / 2,
                            sr.getScaledHeight() - ManyLibClientConfig.ActionBarShift.getIntegerValue(),
                            GuiBase.COLOR_WHITE + (this.getTransparency(this.overlayMessageTime) << 24));
        }
    }

    @Unique
    private int getTransparency(int renderCounter) {// 0 to 255
        if (renderCounter > 20) {
            return 255;
        } else {
            return (int) (renderCounter * 12.75F);
        }
    }

    @Inject(method = "updateTick", at = @At("TAIL"))
    private void updateCounter(CallbackInfo ci) {
        if (this.overlayMessageTime > 0) {
            this.overlayMessageTime--;
        }
    }
}
