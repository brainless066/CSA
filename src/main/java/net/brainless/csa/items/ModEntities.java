package net.brainless.csa.items;

import net.brainless.csa.CSA;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<TwistedfateProjectileEntity> TWISTEDFATE_PROJECTILE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(CSA.MOD_ID, "twistedfate_projectile"), FabricEntityTypeBuilder.<TwistedfateProjectileEntity>create(SpawnGroup.MISC, TwistedfateProjectileEntity::new)
                    .dimensions(EntityDimensions.fixed(0.25f,0.25f)).build());
}
//Theo