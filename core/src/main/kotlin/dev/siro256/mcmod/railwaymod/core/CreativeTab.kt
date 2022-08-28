package dev.siro256.mcmod.railwaymod.core

import dev.siro256.mcmod.railwaymod.core.util.MustBeInitializedOnceLater
import net.minecraft.world.item.CreativeModeTab

object CreativeTab {
    val creativeTab = MustBeInitializedOnceLater<CreativeModeTab>()
}
