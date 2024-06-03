//HyukJoon Kwon
package net.brainless.csa.CustomItem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class FireCardItem extends CardItem{
    public FireCardItem(Settings settings) {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            // Server-side logic (if any)
            burnEntities(world, player);
        } else {
            spawnFlameParticles(world, player);
            //player.sendMessage(Text.literal("This card has no ability").formatted(Formatting.BLUE), false);
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
    }


    //done by ChatGPT https://chatgpt.com/share/3457586a-72d5-4cf0-a108-2439872a5778
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

    //function that give damage to entities around the player except for that player
    //done by ChatGPT https://chatgpt.com/share/3457586a-72d5-4cf0-a108-2439872a5778
    private void burnEntities(World world, PlayerEntity player){
        //get all entities around the player
        //for each entity, if it is not the player, set it on fire
        // Define the radius around the player to check for entities
        double radius = 5.0;

        // Get the player's position
        double playerX = player.getX();
        double playerY = player.getY();
        double playerZ = player.getZ();

        // Define the bounding box around the player to search for entities
        Box searchBox = new Box(playerX - radius, playerY - radius, playerZ - radius,
                playerX + radius, playerY + radius, playerZ + radius);

        // Get all entities within the bounding box
        List<Entity> entities = world.getOtherEntities(player, searchBox, entity -> entity instanceof LivingEntity);

        // Set each entity on fire for a certain number of ticks (20 ticks = 1 second)
        int fireTicks = 100; // 5 seconds
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != player) {
                entity.setOnFireFor(fireTicks);
            }
        }
    }
}
