package dev.siro256.mcmod.railwaymod.core.item

import dev.siro256.mcmod.railwaymod.core.ResourceIdentifier
import net.minecraft.world.item.Item

abstract class IdentifiedItem(properties: Properties): Item(properties), ResourceIdentifier
