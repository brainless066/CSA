package net.brainless.csa.items;

import net.brainless.csa.CSA;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TwistedFateCard{
    public static final Item TwistedfateCards  = registerItem("twistedfatecards", new Item(new FabricItemSettings()));
    public static final Item TwistedfateCardsItem  = registerItem("twistedfate_cards_item", new TwistedfateCardItem(new FabricItemSettings()));

    private static void addItemsToItemGroup(FabricItemGroupEntries entries){
        entries.add(TwistedfateCards);
        entries.add(TwistedfateCardsItem);
    }

    private  static Item registerItem(String name, Item item){
        return Registry.register(Registries.ITEM, new Identifier(CSA.MOD_ID, name), item);
    }
    public static void registerModItems(){
        CSA.LOGGER.info("Registering " + CSA.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(TwistedFateCard::addItemsToItemGroup);

    }

}