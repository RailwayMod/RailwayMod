package dev.siro256.mcmod.railwaymod.core.entity

import dev.siro256.mcmod.railwaymod.core.ResourceIdentifier
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.level.Level

abstract class IdentifiedEntity(entityType: EntityType<*>, level: Level): Entity(entityType, level), ResourceIdentifier
