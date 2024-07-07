package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.api.ManyLibGuiIngame;
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
    private int rgb;
    @Unique
    private int renderCounter = 0;

    @Override
    public void manyLib$setInfo(String string, int rgb, int duration) {
        this.info = string;
        this.rgb = rgb;
        this.renderCounter = duration;
    }

    @Inject(method = "renderGameOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/Minecraft;inDevMode()Z", shift = At.Shift.BEFORE))
    private void renderString(float par1, boolean par2, int par3, int par4, CallbackInfo ci) {
        ScaledResolution sr = new ScaledResolution(this.mc.gameSettings, this.mc.displayWidth, this.mc.displayHeight);
        if (this.info == null) return;

        if (this.renderCounter > 0) {
            this.drawCenteredString
                    (this.mc.fontRenderer,
                            this.info,
                            sr.getScaledWidth() / 2,
                            sr.getScaledHeight() - ManyLibConfig.hoverTextYLevel.getIntegerValue(),
                            this.rgb + (this.getTransparency(this.renderCounter) << 24));
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
