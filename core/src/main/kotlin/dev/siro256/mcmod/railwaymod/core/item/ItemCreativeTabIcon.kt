package dev.siro256.mcmod.railwaymod.core.item

import dev.siro256.mcmod.railwaymod.core.Values
import net.minecraft.resources.ResourceLocation

object ItemCreativeTabIcon: IdentifiedItem(Properties()) {
    override val identifier = ResourceLocation(Values.MOD_ID, "creative_tab_icon")
}
