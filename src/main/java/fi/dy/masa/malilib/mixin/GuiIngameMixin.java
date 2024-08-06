package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.api.ManyLibGuiIngame;
import fi.dy.masa.malilib.event.RenderEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
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
public abstract class GuiIngameMixin extends Gui implements ManyLibGuiIngame {
    @Shadow
    @Final
    private Minecraft mc;
    @Unique
    private String info;
    @Unique
    private int renderCounter = 0;

    @Override
    public void manyLib$setInfo(String string, int duration) {
        this.info = string;
        this.renderCounter = duration;
    }

    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z", shift = At.Shift.BEFORE))
    private void renderString(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderGameOverlayPost((GuiIngame) (Object) this);
//        ((RenderEventHandler) RenderEventHandler.getInstance()).onRenderGameOverlayPost(drawContext, this.client, partialTicks);

        ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        if (this.info == null) return;
        if (this.renderCounter > 0) {
            this.drawCenteredString
                    (this.mc.fontRenderer,
                            this.info,
                            sr.getScaledWidth() / 2,
                            sr.getScaledHeight() - ManyLibConfig.hoverTextYLevel.getIntegerValue(),
                            GuiBase.COLOR_WHITE + (this.getTransparency(this.renderCounter) << 24));
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
        if (this.renderCounter > 0) {
            this.renderCounter--;
        }
    }
}
