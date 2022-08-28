package dev.siro256.mcmod.railwaymod.core

import dev.siro256.mcmod.railwaymod.core.util.MustBeInitializedOnceLater
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

data class CreativeTabData(
    val tab: MustBeInitializedOnceLater<CreativeModeTab>,
    val identifier: ResourceLocation,
    val item: ItemStack
)
