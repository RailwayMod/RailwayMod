package dev.siro256.mcmod.railwaymod.core.block

import dev.siro256.mcmod.railwaymod.core.Values
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.DyeColor
import net.minecraft.world.level.material.Material

object BlockTest2: IdentifiedBlock(Properties.of(Material.STONE, DyeColor.YELLOW)) {
    override val identifier = ResourceLocation(Values.MOD_ID, "test_block2")
}
