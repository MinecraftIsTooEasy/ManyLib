package fi.dy.masa.malilib.mixin;

import net.minecraft.GuiContainerCreative;
import net.minecraft.IInventory;
import net.minecraft.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.SlotCreativeInventory.class)
public abstract class SlotCreativeInventoryMixin extends Slot {
    public SlotCreativeInventoryMixin(IInventory inventory, int slot_index, int display_x, int display_y) {
        super(inventory, slot_index, display_x, display_y);
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    private void modifySlotNumber(GuiContainerCreative slot, Slot i, int par3, CallbackInfo ci) {
        this.slotNumber = par3;
    }
}
