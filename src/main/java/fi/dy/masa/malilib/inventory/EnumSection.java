package fi.dy.masa.malilib.inventory;

public enum EnumSection {
    Armor,
    InventoryStorage,
    InventoryHotBar,

    CraftMatrix,
    CraftResult,

    FurnaceIn,
    FurnaceOut,
    FurnaceFuel,

    MerchantIn,
    MerchantOut,

    BrewingBottles,
    BrewingIngredient,



    Other,
    ;

    public ContainerSection get() {
        return ContainerHandler.getSection(this);
    }

}
