//HyukJoon Kwon
package net.brainless.csa;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class TestItem implements ModInitializer {
    public static final Item CUSTOM_ITEM = new Item(new FabricItemSettings());
    @Override
    public void onInitialize() {
        System.out.println("Hello from TestItem!");
        //register the item
        Registry.register(Registries.ITEM, new Identifier("csa", "custom_item"), CUSTOM_ITEM);
        ItemGroupEvents
                // Register a "modify" event for the Ingredients item group.
                .modifyEntriesEvent(ItemGroups.INGREDIENTS)
                // Add the item to the group when you get access to it.
                .register((itemGroup) -> itemGroup.add(CUSTOM_ITEM));
    }
}
