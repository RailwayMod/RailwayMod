package dev.siro256.mcmod.railwaymod.core.item

import dev.siro256.mcmod.railwaymod.core.ResourceIdentifier
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block

abstract class IdentifiedBlockItem(block: Block, properties: Properties)
    : BlockItem(block, properties), ResourceIdentifier
