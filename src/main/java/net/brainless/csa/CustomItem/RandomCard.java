package net.brainless.csa.CustomItem;

import net.brainless.csa.CSA;
import net.brainless.csa.PokemonCards;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.logging.Logger;

public class RandomCard extends Item {

    public RandomCard(Settings settings) {
        super(settings);
    }
    private boolean isInventoryFull(ServerPlayerEntity player) {
        return player.getInventory().main.stream().allMatch(stack -> !stack.isEmpty());
    }

    private void sendFullInventoryMessage(ServerPlayerEntity player) {
        player.sendMessage(Text.literal("Your inventory is full!, you might lose your card packs").formatted(Formatting.RED), false);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //if all the inventory space is full, do nothing and print message to user


        if (user instanceof ServerPlayerEntity) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) user;
            if (isInventoryFull(serverPlayer)) {
                sendFullInventoryMessage(serverPlayer);
                return new TypedActionResult<>(ActionResult.PASS, user.getStackInHand(hand));
            }
        }
        //user randomly gets the item from the Cards array
        int randomIndex = (int) (Math.random() * (PokemonCards.Cards.length+1));
        if(randomIndex < PokemonCards.Cards.length) {
            user.giveItemStack(PokemonCards.Cards[randomIndex].getDefaultStack());
        }
        else if (randomIndex == 151){
            user.giveItemStack(PokemonCards.Charizard.getDefaultStack());
        }
        else{
            //Log error
            CSA.LOGGER.info("Random index out of bounds");
        }
        //remove one card pack from the user's inventory
        ItemStack stack = user.getStackInHand(hand);
        stack.decrement(1);

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));

    }
}
