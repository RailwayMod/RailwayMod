package dev.siro256.mcmod.railwaymod.core.item

import dev.siro256.mcmod.railwaymod.core.CreativeTab
import dev.siro256.mcmod.railwaymod.core.Values
import dev.siro256.mcmod.railwaymod.core.block.BlockTest
import net.minecraft.Util
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.InteractionResult
import net.minecraft.world.item.context.UseOnContext

object ItemTestBlock: IdentifiedBlockItem(BlockTest, Properties().tab(CreativeTab.creativeTab.get())) {
    override val identifier = ResourceLocation(Values.MOD_ID, "test_block")

    override fun useOn(context: UseOnContext): InteractionResult {
        if (context.level.isClientSide) return InteractionResult.PASS

        context.player?.sendMessage(TextComponent("Test"), Util.NIL_UUID)
        return InteractionResult.SUCCESS
    }
}
