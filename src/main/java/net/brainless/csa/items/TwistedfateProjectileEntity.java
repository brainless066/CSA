package net.brainless.csa.items;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
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
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

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
    }


    @Override
    protected void onBlockCollision(BlockState state) { // called on collision with a block
        super.onBlockCollision(state);
        if (!this.getWorld().isClient) { // checks if the world is client
            this.getWorld().sendEntityStatus(this, (byte)3); // particle?
            this.kill(); // kills the projectile
        }

    }


}
