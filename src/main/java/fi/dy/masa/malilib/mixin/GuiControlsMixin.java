package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.gui.button.interfaces.GuiButtonCommented;
import fi.dy.masa.malilib.gui.screen.HotKeyMenu;
import net.minecraft.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Mixin(GuiControls.class)
public abstract class GuiControlsMixin extends GuiScreen {
    @Shadow
    private GameSettings options;

    @Unique
    private Set<Integer> keyCodesCache = new HashSet<>();

    @Inject(method = "initGui", at = @At("TAIL"))
    private void cacheAndAddButton(CallbackInfo ci) {
        this.keyCodesCache = ConfigManager.getInstance().getAllHotKeys().map(ConfigHotkey::getKeyCode).collect(Collectors.toSet());
    }

    @Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/GuiControls;drawString(Lnet/minecraft/FontRenderer;Ljava/lang/String;III)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void markRed(int par1, int par2, float par3, CallbackInfo ci, int var4, int var5, boolean var6, int var7) {
        if (this.keyCodesCache.contains(this.options.keyBindings[var5].keyCode)) {
            ((GuiButton) this.buttonList.get(var5)).displayString = EnumChatFormatting.RED + this.options.getOptionDisplayString(var5);
        }
    }
}
