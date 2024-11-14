package fi.dy.masa.malilib.mixin;

import fi.dy.masa.malilib.ManyLibConfig;
import fi.dy.masa.malilib.gui.DrawContext;
import fi.dy.masa.malilib.gui.ManyLibIcons;
import fi.dy.masa.malilib.gui.screen.FakeModMenu;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.GuiButton;
import net.minecraft.GuiMainMenu;
import net.minecraft.GuiScreen;
import net.minecraft.Minecraft;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin extends GuiScreen {
    @Unique
    private static final int optionsButtonID = 28251197;

    @Unique
    private GuiButton button;

    @Inject(method = "addSingleplayerMultiplayerButtons", at = @At("HEAD"))
    private void addButton(int par1, int par2, CallbackInfo ci) {
        if (ManyLibConfig.HideConfigButton.getBooleanValue()) return;
        this.button = new GuiButton(optionsButtonID, this.width / 2 + 100 + 5, par1 + par2 * 2, 20, 20, null) {
            @Override
            public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
                if (this.drawButton) {
                    par1Minecraft.getTextureManager().bindTexture(ManyLibIcons.MenuIcon.getTexture());
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
                    ManyLibIcons.MenuIcon.renderAt(this.xPosition, this.yPosition, 0.0F, this.drawButton, this.drawButton && this.field_82253_i);
                    this.mouseDragged(par1Minecraft, par2, par3);
                }
            }
        };
        this.buttonList.add(this.button);
    }

    @Inject(method = "drawScreen", at = @At("RETURN"))
    private void tooltip(int par1, int par2, float par3, CallbackInfo ci) {
        if (ManyLibConfig.HideConfigButton.getBooleanValue()) return;
        if (this.button.func_82252_a()) {
            RenderUtils.drawHoverText(par1, par2, List.of(StringUtils.translate("manyLib.gui.button.options")), new DrawContext());
        }
    }

    @Inject(method = "actionPerformed", at = @At("TAIL"))

    private void action(GuiButton par1GuiButton, CallbackInfo ci) {
        if (ManyLibConfig.HideConfigButton.getBooleanValue()) return;
        int id = par1GuiButton.id;
        if (id == optionsButtonID) {
            this.mc.displayGuiScreen(new FakeModMenu(this));
        }
    }
}
