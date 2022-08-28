package dev.siro256.mcmod.railwaymod.forge

import dev.siro256.mcmod.railwaymod.core.Values
import dev.siro256.mcmod.railwaymod.core.item.ItemCreativeTabIcon
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

object CreativeTab: CreativeModeTab(-1, "${Values.MOD_ID}.creative_tab") {
    override fun makeIcon(): ItemStack {
        return ItemCreativeTabIcon.defaultInstance
    }
}
