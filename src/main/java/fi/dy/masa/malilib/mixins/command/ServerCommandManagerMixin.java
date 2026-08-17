package fi.dy.masa.malilib.mixins.command;

import fi.dy.masa.malilib.command.CommandMain;
import net.minecraft.CommandHandler;
import net.minecraft.IAdminCommand;
import net.minecraft.ServerCommandManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerCommandManager.class)
public abstract class ServerCommandManagerMixin extends CommandHandler implements IAdminCommand {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void registerCommands(CallbackInfo ci) {
        this.registerCommand(new CommandMain());
    }
}
