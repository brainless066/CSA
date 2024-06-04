//HyukJoon
package net.brainless.csa;

import net.brainless.csa.BlackJack.Card;
import net.brainless.csa.CustomItem.CardItem;
import net.brainless.csa.CustomItem.FireCardItem;
import net.brainless.csa.CustomItem.RandomCard;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class PokemonCards {
    public static final Item Card_Pack = registerItem("card_pack", new RandomCard(new FabricItemSettings()));
    public static final Item Charizard = registerItem("charizard", new FireCardItem(new FabricItemSettings()));
    public static final Item[] Cards = new CardItem[151];
    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(Card_Pack);
        entries.add(Charizard);

        for(int i = 0; i < Cards.length; i++) {
            entries.add(Cards[i]);
        }
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier("csa", name), item);
    }

    public static void registerModItems() {
        CSA.LOGGER.info("Registering Mod Items for " + "csa");
        for(int i = 0; i < Cards.length; i++) {
            //change the integer value to three digit format
            String formatted = String.format("%03d", i+1);
            Cards[i] = registerItem("card_" + formatted, new CardItem(new FabricItemSettings()));
        }
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(PokemonCards::addItemsToIngredientItemGroup);
    }
}
