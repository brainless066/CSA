package net.brainless.csa.CustomItem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class CardItem extends Item {
    public CardItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            // Server-side logic (if any)
        } else {
            spawnFlameParticles(world, player);
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }

    @Environment(EnvType.CLIENT)
    private void spawnFlameParticles(World world, PlayerEntity player) {
        for (int i = 0; i < 20; i++) {
            double x = player.getX() + (world.random.nextDouble() - 0.5) * 2.0;
            double y = player.getY() + world.random.nextDouble() * 2.0;
            double z = player.getZ() + (world.random.nextDouble() - 0.5) * 2.0;

            world.addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.0, 0.0);
        }
        // Play sound
        world.playSound(player, player.getBlockPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 1.0F, 1.0F);
    }
}
