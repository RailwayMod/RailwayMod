package dev.siro256.mcmod.railwaymod.core.util

import dev.siro256.mcmod.railwaymod.core.ResourceIdentifier
import dev.siro256.mcmod.railwaymod.core.block.IdentifiedBlock
import dev.siro256.mcmod.railwaymod.core.entity.EntityTest
import dev.siro256.mcmod.railwaymod.core.item.IdentifiedBlockItem
import dev.siro256.mcmod.railwaymod.core.renderer.RendererTestEntity
import net.minecraft.client.renderer.entity.EntityRenderer
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import kotlin.reflect.KFunction1

fun <T, U> blockAndItemList(vararg elements: Pair<T, U>): List<Pair<T, U>>
        where T : Block,
              T : ResourceIdentifier,
              U : BlockItem,
              U : ResourceIdentifier = if (elements.isNotEmpty()) elements.toList() else emptyList()

fun blockList(vararg elements: IdentifiedBlock): List<IdentifiedBlock> =
    if (elements.isNotEmpty()) elements.toList() else emptyList()

fun itemList(vararg elements: IdentifiedBlockItem): List<IdentifiedBlockItem> =
    if (elements.isNotEmpty()) elements.toList() else emptyList()
