package net.brainless.csa.items;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class TwistedfateProjectileEntity extends ThrownItemEntity {

    public TwistedfateProjectileEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }
    public TwistedfateProjectileEntity(LivingEntity livingEntity, World world) {
        super(ModEntities.TWISTEDFATE_PROJECTILE, livingEntity, world);
    }

    @Override
    protected Item getDefaultItem() {
        return TwistedFateCard.TwistedfateCardsItem;
    }


    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        int damage = 10;
        entity.damage(this.getDamageSources().thrown(this, this.getOwner()), damage);
        //https://chatgpt.com/share/cbad70da-d87d-428a-9e1f-7374eae192ac
        entity.setOnFireFor(5);
        burnEntities(this.getWorld(),entityHitResult);
    }
    private void burnEntities(World world, EntityHitResult entityHitResult){
        //get all entities around the player
        //for each entity, if it is not the player, set it on fire
        // Define the radius around the player to check for entities
        Entity entityEne = entityHitResult.getEntity();
        double radius = 5.0;

        // Get the player's position
        double playerX = entityEne.getX();
        double playerY = entityEne.getY();
        double playerZ = entityEne.getZ();

        // Define the bounding box around the player to search for entities
        Box searchBox = new Box(playerX - radius, playerY - radius, playerZ - radius,
                playerX + radius, playerY + radius, playerZ + radius);

        // Get all entities within the bounding box
        List<Entity> entities = world.getOtherEntities(entityEne, searchBox, entity -> entity instanceof LivingEntity);

        // Set each entity on fire for a certain number of ticks (20 ticks = 1 second)
        int fireTicks = 100; // 5 seconds
        for (Entity entity : entities) {
            if (entity instanceof LivingEntity && entity != entityEne) {
                entity.setOnFireFor(fireTicks);
            }
        }
    }


    @Override
    protected void onBlockCollision(BlockState state) { // called on collision with a block
        super.onBlockCollision(state);
        if (!this.getWorld().isClient) { // checks if the world is client
            this.getWorld().sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }

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

//Theo
}
