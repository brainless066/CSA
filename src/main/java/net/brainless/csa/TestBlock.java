//HyukJoon Kwon
package net.brainless.csa;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class TestBlock implements ModInitializer {
    public static final Block CUSTOM_BLOCK = new Block(FabricBlockSettings.copyOf(Blocks.GRASS_BLOCK).sounds(BlockSoundGroup.GRASS));
    //public static final Block CUSTOM_BLOCK = new Block(FabricBlockSettings.create().strength(4.0f));
    @Override
    public void onInitialize() {
        System.out.println("Hello from TestBlock!");
        //register the block
        Registry.register(Registries.BLOCK, new Identifier("csa", "custom_block"), CUSTOM_BLOCK);
        Registry.register(Registries.ITEM, new Identifier("csa", "custom_block"), new BlockItem(CUSTOM_BLOCK, new FabricItemSettings()));
        ItemGroupEvents
                // Register a "modify" event for the Ingredients item group.
                .modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS)
                // Add the item to the group when you get access to it.
                .register((itemGroup) -> itemGroup.add(CUSTOM_BLOCK.asItem()));
    }
}
