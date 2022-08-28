package dev.siro256.mcmod.railwaymod.core.entity

import dev.siro256.mcmod.railwaymod.core.util.MustBeInitializedOnceLater
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType

object RailwayModEntityType {
    val TEST = IdentifiedEntityType(EntityTest.identifier, MustBeInitializedOnceLater<EntityType<EntityTest>>())

    data class IdentifiedEntityType<T: Entity>(val resourceLocation: ResourceLocation, val entityType: MustBeInitializedOnceLater<EntityType<T>>)
}
