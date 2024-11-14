package fi.dy.masa.malilib.inventory;

import fi.dy.masa.malilib.util.ItemUtils;
import net.minecraft.*;

import java.util.List;
import java.util.Optional;

public class InventoryUtils {
    public static void drop(Slot slot, boolean ctrl) {
        getController().windowClick(getWindowID(), slot.slotNumber, ctrl ? 1 : 0, 4, getClientPlayer());
    }

    public static void swapHotBar(Slot slot, int hotBar) {// hotBar: 0-8
        getController().windowClick(getWindowID(), slot.slotNumber, hotBar, 2, getClientPlayer());
    }

    public static void gatherItems(Slot slot) {
        if (isHoldingItem()) {
            getController().windowClick(getWindowID(), slot.slotNumber, slot.slotNumber, 6, getClientPlayer());
        }
    }

    public static void moveToEmpty(Slot slot, Slot empty) {
        leftClick(slot);
        leftClick(empty);
    }

    public static void moveOneItem(Slot to, Slot from) {
        leftClick(from);
        rightClick(to);
        leftClick(from);
    }

    public static boolean canMergeSlot(Slot to, Slot from) {
        if (to.getHasStack() && from.getHasStack()) {
            return ItemUtils.canMerge(to.getStack(), from.getStack());
        }
        return true;// empty slots always merge
    }

    public static ItemStack getHeldItem() {
        return getPlayerInventory().getItemStack();
    }

    public static boolean isHoldingItem() {
        return getHeldItem() != null;
    }

    public static void putHeldItemDown(ContainerSection section) {
        if (isHoldingItem()) {
            section.getEmptySlot().ifPresent(InventoryUtils::leftClick);
        }
    }

    public static void dropHeldItem() {
        if (isHoldingItem()) {
            getController().windowClick(getWindowID(), -999, 0, 4, getClientPlayer());
        }
    }

    public static ItemStack leftClick(Slot slot) {
        return click(slot.slotNumber, false, false);
    }

    public static ItemStack leftClick(int index) {
        return click(index, false, false);
    }

    public static ItemStack rightClick(Slot slot) {
        return click(slot.slotNumber, true, false);
    }

    public static ItemStack rightClick(int index) {
        return click(index, true, false);
    }

    public static ItemStack click(Slot slot, boolean rightClick, boolean shift) {
        return click(slot.slotNumber, rightClick, shift);
    }

    public static ItemStack click(int index, boolean rightClick, boolean shift) {
        return getController().windowClick(getWindowID(), index, rightClick ? 1 : 0, shift ? 1 : 0, getClientPlayer());
    }

    public static Optional<Slot> getSlotMouseOver() {
        return Optional.ofNullable(getGuiContainer().theSlot);
    }

    public static List<Slot> getSlots() {
        return getSlots(getCurrentContainer());
    }

    @SuppressWarnings("unchecked")
    public static List<Slot> getSlots(Container container) {
        return container.inventorySlots;
    }

    public static GuiContainer getGuiContainer() {
        return (GuiContainer) getClient().currentScreen;
    }

    public static int getWindowID() {
        return getCurrentContainer().windowId;
    }

    public static PlayerControllerMP getController() {
        return getClient().playerController;
    }

    public static Container getContainer(GuiContainer guiContainer) {
        return guiContainer.inventorySlots;
    }

    public static Container getInventoryContainer() {
        return getClientPlayer().inventoryContainer;
    }

    public static Container getCurrentContainer() {
        return getClientPlayer().openContainer;
    }

    public static ClientPlayer getClientPlayer() {
        return getClient().thePlayer;
    }

    public static Minecraft getClient() {
        return Minecraft.getMinecraft();
    }

    public static InventoryPlayer getPlayerInventory() {
        return getClientPlayer().inventory;
    }

    public static boolean isPlayerInventory(IInventory inventory) {
        return inventory == getPlayerInventory();
    }

}
