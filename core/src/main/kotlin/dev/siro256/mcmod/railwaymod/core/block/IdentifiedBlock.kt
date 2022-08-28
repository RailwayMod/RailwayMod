package dev.siro256.mcmod.railwaymod.core.block

import dev.siro256.mcmod.railwaymod.core.ResourceIdentifier
import net.minecraft.world.level.block.Block

abstract class IdentifiedBlock(properties: Properties): Block(properties), ResourceIdentifier
