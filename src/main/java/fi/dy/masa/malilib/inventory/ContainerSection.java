package fi.dy.masa.malilib.inventory;

import fi.dy.masa.malilib.util.ItemUtils;
import net.minecraft.IInventory;
import net.minecraft.ItemStack;
import net.minecraft.Slot;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public record ContainerSection(IInventory inventory, List<Slot> slots) {
    public boolean isInventoryHotBar() {
        return InventoryUtils.isPlayerInventory(this.inventory) && this.slots.size() == 9;
    }

    public boolean isInventoryStorage() {
        return InventoryUtils.isPlayerInventory(this.inventory) && this.slots.size() == 27;
    }

    public boolean hasSlot(Slot slot) {
        return this.slots.contains(slot);
    }

    public int getLocalIndex(Slot slot) {
        for (int i = 0; i < this.slots.size(); i++) {
            if (this.slots.get(i) == slot) return i;
        }
        return 0;
    }

    public void leftClick(int localIndex) {
        InventoryUtils.leftClick(this.toGlobalIndex(localIndex));
    }

    public void leftClick(Slot slot) {
        InventoryUtils.leftClick(slot.slotNumber);
    }

    public int toGlobalIndex(int localIndex) {
        return this.slots.get(localIndex).slotNumber;
    }

    public int toLocalIndex(int globalIndex) {
        for (int i = 0; i < this.slots.size(); i++) {
            if (this.slots.get(i).slotNumber == globalIndex) return i;
        }
        return 0;
    }

    public Optional<Slot> getEmptySlot() {
        return this.slots.stream().filter(x -> !x.getHasStack()).findFirst();
    }

    public boolean moveToEmpty(Slot slot) {
        Optional<Slot> emptySlot = this.getEmptySlot();
        if (emptySlot.isPresent()) {
            InventoryUtils.moveToEmpty(slot, emptySlot.get());
            return true;
        } else {
            return false;
        }
    }

    public void moveToSection(int localIndex, ContainerSection other) {
        other.getEmptySlot().ifPresent(x -> {
            this.leftClick(localIndex);
            other.leftClick(x);
        });
    }

    public void moveToSection(Slot slot, ContainerSection other) {
        other.getEmptySlot().ifPresent(x -> {
            this.leftClick(slot);
            other.leftClick(x);
        });
    }

    public Optional<Slot> hasItem(ItemStack itemStack) {
        return this.slots.stream().filter(x -> x.getHasStack() && ItemUtils.compareIDMeta(x.getStack(), itemStack))
                .max(Comparator.comparingInt(slot -> slot.getStack().stackSize));
    }

    public Optional<Slot> providesOneScroll(ItemStack itemStack) {
        return this.slots.stream().filter(x -> x.getHasStack() && ItemUtils.canMerge(itemStack, x.getStack())).findFirst();
    }

    public Optional<Slot> absorbsOneScroll(ItemStack itemStack) {
        return this.slots.stream().filter(x -> x.getHasStack() && ItemUtils.canMerge(x.getStack(), itemStack)).findFirst();
    }

    public void mergeSlots() {
        for (int i = this.slots.size() - 1; i >= 0; i--) {// inverse order reduce operations
            Slot slot = slots.get(i);
            if (i == 0) continue;// just skip the first slot
            if (!slot.getHasStack()) continue;// skip those empty
            mergeSlot(i, slot);
        }
    }

    private void mergeSlot(int currentIndex, Slot currentSlot) {
        for (int i = 0; i < currentIndex; i++) {
            Slot slot = this.slots.get(i);
            if (slot.getHasStack() && InventoryUtils.canMergeSlot(slot, currentSlot)) {
                InventoryUtils.leftClick(currentSlot);
                InventoryUtils.leftClick(slot);
                InventoryUtils.putHeldItemDown(this);
//                ManyLib.logger.info("merging {} to {}", currentIndex, i);
                return;
            }
        }
    }

    public void fillBlanks() {
        for (int i = this.slots.size() - 1; i >= 0; i--) {// inverse order reduce operations
            Slot slot = slots.get(i);
            if (i == 0) continue;// just skip the first slot
            if (!slot.getHasStack()) continue;// skip those empty
            this.moveToPreviousEmpty(i, slot);
        }
    }

    private void moveToPreviousEmpty(int currentIndex, Slot currentSlot) {
        for (int i = 0; i < currentIndex; i++) {
            Slot slot = this.slots.get(i);
            if (!slot.getHasStack()) {
                InventoryUtils.moveToEmpty(currentSlot, slot);
//                ManyLib.logger.info("moving {} to empty {}", currentIndex, i);
                return;
            }
        }
    }

    public void runForSimilar(ItemStack itemStack, BiPredicate<ItemStack, ItemStack> similarity, Consumer<Slot> runnable) {
        for (Slot slot : this.slots) {
            if (slot.getHasStack() && similarity.test(itemStack, slot.getStack())) {
                runnable.accept(slot);
            }
        }
    }
}
