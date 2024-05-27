package net.brainless.csa.CustomItem;

import net.brainless.csa.PokemonCards;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class RandomCard extends Item {

    public RandomCard(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        //********Done by copilot********

        //user randomly gets the item from the Cards array
        int randomIndex = (int) (Math.random() * PokemonCards.Cards.length);
        user.giveItemStack(PokemonCards.Cards[randomIndex].getDefaultStack());

        //remove one card pack from the user's inventory
        ItemStack stack = user.getStackInHand(hand);
        stack.decrement(1);

        return new TypedActionResult<>(ActionResult.SUCCESS, user.getStackInHand(hand));

    }
}
