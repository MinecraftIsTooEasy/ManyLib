package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.gui.screen.FakeModMenu;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiButton;
import net.minecraft.GuiIngameMenu;
import net.minecraft.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiIngameMenu.class)
public abstract class GuiIngameMenuMixin extends GuiScreen {
    @Unique
    private static final int optionsButtonID = 28251197;

    @Inject(method = "initGui", at = @At("TAIL"))
    private void addButton(CallbackInfo ci) {
        if (ManyLibConfig.HideConfigButton.getBooleanValue()) return;
        GuiButton manyLibOptions = new GuiButton(optionsButtonID, this.width / 2 - 100, this.height / 4 + 72 - 16, StringUtils.translate("manyLib.gui.button.options"));
        this.buttonList.add(manyLibOptions);
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))
    private void action(GuiButton par1GuiButton, CallbackInfo ci) {
        if (ManyLibConfig.HideConfigButton.getBooleanValue()) return;
        if (par1GuiButton.id == optionsButtonID) {
            this.mc.displayGuiScreen(new FakeModMenu(this));
        }
    }
}
