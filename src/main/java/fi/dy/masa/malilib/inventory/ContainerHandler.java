package fi.dy.masa.malilib.inventory;

import net.minecraft.*;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContainerHandler {
    private static final List<ContainerSection> unIdentifiedSections = new ArrayList<>();

    private static final Map<EnumSection, ContainerSection> sectionMap = new HashMap<>();

    public static void updateContainer(Container container) {
        List<Slot> slots = InventoryUtils.getSlots(container);
        Map<IInventory, List<Slot>> map = slots.stream().collect(Collectors.groupingBy(x -> x.inventory));
        for (IInventory iInventory : map.keySet()) {
            identifyGroup(iInventory, map.get(iInventory));
        }
    }

    private static void identifyGroup(IInventory iInventory, List<Slot> slotList) {

        ContainerSection theWholeSection = new ContainerSection(iInventory, slotList);


        if (InventoryUtils.isPlayerInventory(iInventory)) {
            int size = slotList.size();
            ContainerSection hotBar, playerStorage;
            switch (size) {
                case 36 -> {// only hotBar and storage
                    Slot sample = slotList.get(0);
                    if (sample.isSlotInInventory(iInventory, 0)) {// this means hotBar then storage
                        hotBar = new ContainerSection(iInventory, slotList.subList(0, 9));
                        playerStorage = new ContainerSection(iInventory, slotList.subList(9, 36));
                    } else {// this means storage then hotBar
                        playerStorage = new ContainerSection(iInventory, slotList.subList(0, 27));
                        hotBar = new ContainerSection(iInventory, slotList.subList(27, 36));
                    }
                    sectionMap.put(EnumSection.InventoryHotBar, hotBar);
                    sectionMap.put(EnumSection.InventoryStorage, playerStorage);
                }
                case 40 -> {// armor, storage, hotBar
                    ContainerSection armor = new ContainerSection(iInventory, slotList.subList(0, 4));
                    playerStorage = new ContainerSection(iInventory, slotList.subList(4, 31));
                    hotBar = new ContainerSection(iInventory, slotList.subList(31, 40));
                    sectionMap.put(EnumSection.InventoryHotBar, hotBar);
                    sectionMap.put(EnumSection.InventoryStorage, playerStorage);
                    sectionMap.put(EnumSection.Armor, armor);
                }
            }
            return;
        }

        if (iInventory instanceof TileEntityFurnace) {
            sectionMap.put(EnumSection.FurnaceIn, new ContainerSection(iInventory, slotList.subList(0, 1)));
            sectionMap.put(EnumSection.FurnaceFuel, new ContainerSection(iInventory, slotList.subList(1, 2)));
            sectionMap.put(EnumSection.FurnaceOut, new ContainerSection(iInventory, slotList.subList(2, 3)));
            return;
        }

        if (iInventory instanceof InventoryMerchant) {
            sectionMap.put(EnumSection.MerchantIn, new ContainerSection(iInventory, slotList.subList(0, 2)));
            sectionMap.put(EnumSection.MerchantOut, new ContainerSection(iInventory, slotList.subList(2, 3)));
        }

        if (iInventory instanceof TileEntityBrewingStand) {
            sectionMap.put(EnumSection.BrewingBottles, new ContainerSection(iInventory, slotList.subList(0, 3)));
            sectionMap.put(EnumSection.BrewingIngredient, new ContainerSection(iInventory, slotList.subList(3, 4)));
            return;
        }

        if (iInventory instanceof InventoryCrafting) {
            sectionMap.put(EnumSection.CraftMatrix, theWholeSection);
            return;
        }

        if (iInventory instanceof InventoryCraftResult) {
            sectionMap.put(EnumSection.CraftResult, theWholeSection);
            return;
        }


        sectionMap.putIfAbsent(EnumSection.Other, theWholeSection);
        unIdentifiedSections.add(theWholeSection);
    }

    public static void clear() {
        sectionMap.clear();
        unIdentifiedSections.clear();
    }

    public static ContainerSection getSection(EnumSection section) {
        return sectionMap.get(section);
    }

    public static List<ContainerSection> getUnIdentifiedSections() {
        return unIdentifiedSections;
    }

    public static List<ContainerSection> getAllSections() {
        return Stream.concat(unIdentifiedSections.stream(), sectionMap.values().stream()).toList();
    }

    public static Optional<ContainerSection> getSectionMouseOver() {
        return InventoryUtils.getSlotMouseOver().map(ContainerHandler::getSection);
    }

    public static ContainerSection getSection(Slot slot) {
        return getAllSections().stream().filter(x -> x.hasSlot(slot)).findFirst().orElseThrow();
    }

    public static ContainerSection getSection(int globalIndex) {
        return getAllSections().stream().filter(x -> x.slots().stream().anyMatch(y -> y.slotNumber == globalIndex)).findFirst().orElseThrow();
    }
}
