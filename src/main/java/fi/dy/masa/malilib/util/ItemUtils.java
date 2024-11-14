package fi.dy.masa.malilib.util;

import net.minecraft.ItemStack;

public class ItemUtils {
    public static boolean compareID(ItemStack itemStack, ItemStack other) {
        return itemStack.itemID == other.itemID;
    }

    public static boolean compareMeta(ItemStack itemStack, ItemStack other) {
        return itemStack.getItemSubtype() == other.getItemSubtype();
    }

    public static boolean compareIDMeta(ItemStack itemStack, ItemStack other) {
        return compareID(itemStack, other) && compareMeta(itemStack, other);
    }

    public static boolean canMerge(ItemStack to, ItemStack from) {
        if (to.stackSize >= to.getMaxStackSize()) return false;// full slot can not merge
        return compareIDMeta(to, from);
    }
}
